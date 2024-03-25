package net.m2i.todo.security;

// Importations nécessaires, y compris les classes pour les requêtes et réponses HTTP, ainsi que les exceptions d'authentification.
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // Marque cette classe comme un composant de Spring, le rendant éligible pour la détection automatique lors du balayage de chemin de classe.
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // Implémente l'interface AuthenticationEntryPoint, fournissant une méthode pour commencer l'authentification.

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Cette méthode est appelée chaque fois qu'une exception est lancée en raison d'une tentative d'accès non authentifié à une ressource sécurisée.

        // Envoie une réponse d'erreur avec le code d'état HTTP 401 (Non autorisé) pour indiquer que la demande nécessite une authentification HTTP.
        // Le message de l'exception authException est également envoyé dans la réponse pour fournir plus de détails sur l'erreur.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
