package christel.mvele.nutrisportBackend.controller;


import christel.mvele.nutrisportBackend.model.Utilisateur;
import christel.mvele.nutrisportBackend.service.ApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final ApiService apiService;

    @GetMapping("/users")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> users = apiService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
