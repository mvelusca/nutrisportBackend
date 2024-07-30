package christel.mvele.nutrisportBackend.controller;


import christel.mvele.nutrisportBackend.model.Utilisateur;
import christel.mvele.nutrisportBackend.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    /**
     * Updates an existing user with the specified ID.
     *
     * @param id the unique identifier of the user to be updated
     * @param updatedUser the updated details of the user
     * @return a ResponseEntity containing the updated user in the body and a status code of 200 (OK) upon successful update
     */
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody Utilisateur updatedUser) {
        Utilisateur user = apiService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    /**
     * Deletes a user with the specified ID.
     *
     * @param id the unique identifier of the user to be deleted
     * @return a ResponseEntity with status code 204 (No Content) upon successful deletion
     * @throws IllegalArgumentException if the provided ID is null or negative
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        apiService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds a new user.
     *
     * @param newUser the details of the new user to be added
     * @return a ResponseEntity containing the added user in the body and a status code of 201 (Created) upon successful addition
     */
    @PostMapping("/add")
    public ResponseEntity<Utilisateur> addUser(@RequestBody Utilisateur newUser) {
        Utilisateur user = apiService.addUser(newUser);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/users/paginate")
    public ResponseEntity<Page<Utilisateur>> getUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Utilisateur> usersPage = apiService.getUsersPage(page, size);
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Utilisateur>> searchUsers(@RequestParam String query) {
        List<Utilisateur> users = apiService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Integer id) {
        Optional<Utilisateur> userOptional = apiService.getUserById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<Utilisateur> getUserByMail(@PathVariable String mail) {
        Optional<Utilisateur> userOptional = apiService.getUserByMail(mail);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
