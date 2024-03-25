package net.m2i.todo.security;

import lombok.AllArgsConstructor; // Génère un constructeur avec un argument pour chaque champ final de la classe.
import net.m2i.todo.entity.User; // Importe l'entité User.
import net.m2i.todo.repository.UserRepository; // Importe le repository pour accéder aux données utilisateur.
import org.springframework.security.core.GrantedAuthority; // Représente une autorité accordée à l'utilisateur authentifié.
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Implémentation concrète de GrantedAuthority.
import org.springframework.security.core.userdetails.UserDetails; // Fournit les informations nécessaires pour construire un objet Authentication.
import org.springframework.security.core.userdetails.UserDetailsService; // Interface pour le service de détails utilisateur.
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exception lancée quand l'utilisateur n'est pas trouvé.
import org.springframework.stereotype.Service; // Marque la classe comme un bean de service.

import java.util.Set;
import java.util.stream.Collectors; // Importe Collectors pour les opérations de stream.

@Service // Déclare la classe comme un service Spring.
@AllArgsConstructor // Lombok génère automatiquement un constructeur avec tous les attributs de la classe comme arguments.
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository; // Injecte UserRepository pour accéder aux utilisateurs dans la base de données.

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Méthode pour charger un utilisateur par son nom d'utilisateur ou son email.
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                // Tente de récupérer l'utilisateur par son nom d'utilisateur ou son email.
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));
        // Si l'utilisateur n'est pas trouvé, lance une UsernameNotFoundException.

        // Convertit les rôles de l'utilisateur en GrantedAuthority, nécessaire pour Spring Security.
        Set<GrantedAuthority> authorities = user.getUserRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())) // Map chaque rôle de l'utilisateur en SimpleGrantedAuthority.
                .collect(Collectors.toSet()); // Collecte le résultat dans un Set.

        // Retourne une instance de UserDetails contenant les informations de l'utilisateur.
        // Cela inclut le nom d'utilisateur ou l'email, le mot de passe, et les autorités.
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities
        );
    }
}
