package christel.mvele.nutrisportBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    private String prenom;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //pour cacher l'élément mdp, qui est dans le tableau Json
    private String mdp;

    @Column(unique = true)
    private String mail;

    private String photo;

    private String bio;

    private LocalDate naissance;

    private String localisation;

    private boolean accountLocked;

    private boolean enabled;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate updatedDate;

    //un utilisateur a un role
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;


    @JsonSerialize(using = GrantedAuthoritySerializer.class)
    @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * This method retrieves the authorities associated with the user.
     * If authorities have not been set, it populates them by mapping the user's roles to {@link SimpleGrantedAuthority} instances.
     *
     * @return a collection of {@link GrantedAuthority} objects representing the user's authorities
     */
    //gestion des rôles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = this.roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return this.mdp;
    }

    @Override
    public String getUsername() {
        return this.getMail();
    }

    //compte de l'utilisateur a-t-il expiré ?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //son compte est blocké ?
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    //ses identifiants ont expiré ?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //son compte est actif ?
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return mail;
    }

    public String fullName(){
        return this.nom + " " + this.prenom;
    }

}


