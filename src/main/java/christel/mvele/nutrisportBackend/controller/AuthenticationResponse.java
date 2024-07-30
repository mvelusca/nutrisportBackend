package christel.mvele.nutrisportBackend.controller;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {

    private String token;
    private Integer userid;
    private String mail;
}
