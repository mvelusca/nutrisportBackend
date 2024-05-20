package christel.mvele.nutrisportBackend.service;

import christel.mvele.nutrisportBackend.controller.AuthenticationRequest;
import christel.mvele.nutrisportBackend.controller.AuthenticationResponse;
import christel.mvele.nutrisportBackend.controller.EmailTemplateName;
import christel.mvele.nutrisportBackend.controller.RegistrationRequest;
import christel.mvele.nutrisportBackend.model.Token;
import christel.mvele.nutrisportBackend.model.Utilisateur;
import christel.mvele.nutrisportBackend.repository.RoleRepository;
import christel.mvele.nutrisportBackend.repository.TokenRepository;
import christel.mvele.nutrisportBackend.repository.UtilisateurRepository;
import christel.mvele.nutrisportBackend.security.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtService jwtService;

    //@Value("${spring.application.mailing.frontend.activation-url}")
    private final String activationUrl = "http://localhost:4200/activate-account";
    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role User not initialized"));
        var user = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .mdp(passwordEncoder.encode(request.getMdp()))
                .mail(request.getMail())
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .createdDate(LocalDate.now())
                .build();
        utilisateurRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(Utilisateur user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendMail(
                user.getMail(),
                user.getNom(),
                activationUrl,
                newToken,
                "Activation de votre compte",
                EmailTemplateName.ACTIVATE_ACCOUNT
        );
    }

    private String generateAndSaveActivationToken(Utilisateur user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .utilisateur(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }
    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            codeBuilder.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getMail(),
                        request.getMdp()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((Utilisateur)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                //todo exception
                .orElseThrow(() -> new RuntimeException("Token invalide"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUtilisateur());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
        }
        var user = utilisateurRepository.findById(savedToken.getUtilisateur().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        utilisateurRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
