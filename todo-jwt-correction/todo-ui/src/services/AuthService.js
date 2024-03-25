// Importe Axios pour effectuer des requêtes HTTP.
import axios from "axios";

// Définit l'URL de base de l'API d'authentification.
const AUTH_REST_API_BASE_URL = "http://localhost:8080/api/auth"

// Fonction pour enregistrer un nouvel utilisateur. Prend en paramètre un objet de données d'inscription et effectue une requête POST.
export const registerAPICall = (registerObj) => axios.post(AUTH_REST_API_BASE_URL + '/register', registerObj);

// Fonction pour se connecter. Prend en paramètres le nom d'utilisateur ou l'email et le mot de passe, et effectue une requête POST.
export const loginAPICall = (usernameOrEmail, password) => axios.post(AUTH_REST_API_BASE_URL + '/login', { usernameOrEmail, password});

// Fonction pour stocker le token JWT dans le stockage local du navigateur. Utile pour maintenir l'état de connexion entre les sessions.
export const storeToken = (token) => localStorage.setItem("token", token);

// Fonction pour récupérer le token JWT du stockage local. Nécessaire pour les requêtes authentifiées après la connexion.
export const getToken = () => localStorage.getItem("token");

// Fonction pour sauvegarder le nom d'utilisateur de l'utilisateur connecté dans le stockage de session. Cela permet de savoir qui est connecté pendant la session en cours.
export const saveLoggedInUser = (username) => sessionStorage.setItem("authenticatedUser", username);

// Fonction pour vérifier si un utilisateur est connecté en cherchant si un nom d'utilisateur est stocké dans la session.
export const isUserLoggedIn = () => {
    const username = sessionStorage.getItem("authenticatedUser");
    // Retourne vrai si un nom d'utilisateur est trouvé, indiquant qu'un utilisateur est connecté.
    return username != null;
}

// Fonction pour obtenir le nom d'utilisateur de l'utilisateur connecté à partir du stockage de session.
export const getLoggedInUser = () => {
    const username = sessionStorage.getItem("authenticatedUser");
    return username;
}

// Fonction pour déconnecter l'utilisateur en effaçant le stockage local et de session. Cela supprime toutes les informations de connexion stockées.
export const logout = () => {
    localStorage.clear();
    sessionStorage.clear();
}
