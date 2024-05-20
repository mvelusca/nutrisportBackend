package christel.mvele.nutrisportBackend.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "nom est obligatoire")
    @NotBlank(message = "nom est obligatoire")
    private String nom;
    @NotEmpty(message = "prenom est obligatoire")
    @NotBlank(message = "prenom est obligatoire")
    private String prenom;
    @Size(min = 8, message = "mot de passe doit avoir 8 caractères minimum")
    @NotEmpty(message = "mot de passe est obligatoire")
    @NotBlank(message = "mot de passe est obligatoire")
    private String mdp;
    @Email(message = "mail non valide")
    @NotEmpty(message = "mail est obligatoire")
    @NotBlank(message = "mail est obligatoire")
    private String mail;


}
