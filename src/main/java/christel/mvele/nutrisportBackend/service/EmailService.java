package christel.mvele.nutrisportBackend.service;

import christel.mvele.nutrisportBackend.controller.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendMail(
            String to,
            String username,
            String confirmationUrl,
            String activationCode,
            String subject,
            EmailTemplateName emailTemplate
    ) throws MessagingException {
        // Définir le nom du modèle d'email
        String templateName = (emailTemplate == null) ? "confirm-email" : emailTemplate.getName();

        // Créer le message MIME
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );

        // Ajouter les propriétés au contexte de Thymeleaf
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        // Configurer les détails de l'email
        helper.setFrom("mvelengoupechristel81@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Générer le contenu de l'email à partir du modèle
        String template = templateEngine.process(templateName, context);
        helper.setText(template, true);

        // Envoyer l'email
        mailSender.send(mimeMessage);
    }
}
