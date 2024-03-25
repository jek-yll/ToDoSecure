// Définit le package et importe les classes nécessaires.
package net.m2i.todo.config;

import lombok.AllArgsConstructor;
import net.m2i.todo.security.JwtAuthenticationEntryPoint;
import net.m2i.todo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Marque cette classe comme source de définitions de bean pour le contexte d'application.
@EnableMethodSecurity // Active la sécurité au niveau des méthodes avec les annotations pré-autorisées.
@AllArgsConstructor // Lombok génère automatiquement un constructeur avec un argument pour chaque champ final.
public class SpringSecurityConfig {

    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    // Bean pour encoder les mots de passe en utilisant BCrypt.
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configure la chaîne de filtres de sécurité.
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable() // Désactive la protection CSRF car JWT est utilisé.
                .authorizeHttpRequests((authorize) -> { // Autorise et restreint l'accès basé sur les URL et les rôles.
                    authorize.requestMatchers("/api/auth/**", "/api/roles/**").permitAll(); // Permet l'accès non authentifié aux endpoints d'authentification et de rôles.
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Permet les requêtes OPTIONS pré-vol CORS.
                    authorize.anyRequest().authenticated(); // Nécessite une authentification pour toutes les autres requêtes.
                }).httpBasic(Customizer.withDefaults()); // Active l'authentification HTTP Basic.

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)); // Gère les exceptions d'authentification.

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ajoute le filtre JWT avant le processus d'authentification par nom d'utilisateur et mot de passe.

        return http.build(); // Construit la configuration de sécurité.
    }

    // Bean pour obtenir l'AuthenticationManager, qui gère l'authentification dans Spring Security.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
