package net.m2i.todo.security;

import io.jsonwebtoken.*; // Importe la bibliothèque JJWT pour manipuler les JWT.
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.m2i.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Permet d'injecter des valeurs depuis application.properties.
import org.springframework.security.core.Authentication; // Représente l'authentification dans le contexte de sécurité.
import org.springframework.stereotype.Component;

import java.security.Key; // Représente une clé cryptographique.
import java.util.Date; // Utilisé pour la manipulation des dates.
import java.util.stream.Collectors; // Utilisé pour manipuler les collections de façon fonctionnelle.

@Component // Marque cette classe comme un bean Spring, la rendant éligible à l'injection de dépendances.
public class JwtTokenProvider {

    @Value("${app.jwt-secret}") // Injecte le secret JWT depuis application.properties.
    private String jwtSecret;

    @Autowired
    UserRepository authService; // Injecte le service pour interagir avec la base de données utilisateur.

    @Value("${app.jwt-expiration-milliseconds}") // Injecte la durée d'expiration du JWT depuis application.properties.
    private long jwtExpirationDate;

    // Génère un token JWT pour un utilisateur authentifié.
    public String generateToken(Authentication authentication){
        String username = authentication.getName(); // Obtient le nom d'utilisateur de l'authentification.

        // Obtient les rôles de l'utilisateur et les joint par une virgule.
        String roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));

        Long idUser = authService.findIdByUsername(username); // Obtient l'ID de l'utilisateur depuis la base de données.

        Date currentDate = new Date(); // Crée une nouvelle instance de Date pour le timestamp actuel.
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate); // Calcule la date d'expiration du token.

        // Construit le token JWT.
        String token = Jwts.builder()
                .setSubject(username) // Définit le sujet du token (nom d'utilisateur).
                .claim("id",idUser) // Ajoute un claim personnalisé avec l'ID de l'utilisateur.
                .claim("role", roles) // Ajoute un claim personnalisé avec les rôles de l'utilisateur.
                .setIssuedAt(new Date()) // Timestamp de création du token.
                .setExpiration(expireDate) // Définit la date d'expiration du token.
                .signWith(key()) // Signe le token avec la clé spécifiée.
                .compact(); // Construit le token JWT.

        return token;
    }

    // Génère la clé utilisée pour signer le token à partir du secret JWT.
    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    // Extrait le nom d'utilisateur depuis le token JWT.
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody(); // Obtient le corps du token.

        String username = claims.getSubject(); // Extrait le sujet (nom d'utilisateur) des claims.

        return username;
    }

    // Valide le token JWT.
    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(token); // Tente de parser le token avec la clé spécifiée.
        return true; // Renvoie true si le token est valide.
    }
}
