package net.m2i.todo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marque cette classe comme un composant Spring, permettant son injection automatique là où c'est nécessaire.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Hérite de OncePerRequestFilter pour s'assurer que le filtre est appelé une seule fois par requête.

    private JwtTokenProvider jwtTokenProvider; // Injecte le fournisseur de token JWT pour valider les tokens.
    private UserDetailsService userDetailsService; // Injecte un service pour charger les données des utilisateurs.

    // Constructeur pour l'injection de dépendances.
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extrait le JWT token de la requête HTTP.
        String token = getTokenFromRequest(request);

        // Valide le token et extrait le nom d'utilisateur si le token est valide.
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsername(token);

            // Charge les données de l'utilisateur basées sur le nom d'utilisateur.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Crée un objet d'authentification basé sur les données de l'utilisateur.
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Définit les détails de l'authentification dans le contexte de sécurité.
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Passe à l'exécution du filtre suivant dans la chaîne de filtres.
        filterChain.doFilter(request, response);
    }

    // Méthode pour extraire le JWT token de l'en-tête "Authorization" de la requête HTTP.
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        // Vérifie si l'en-tête contient un token portant le préfixe "Bearer ".
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7); // Extrait le token en enlevant le préfixe.
        }

        return null; // Retourne null si le token n'est pas trouvé ou ne commence pas par "Bearer ".
    }
}
