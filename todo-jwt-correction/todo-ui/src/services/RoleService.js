// Importe Axios pour faire des appels HTTP et une fonction depuis AuthService pour récupérer le token JWT.
import axios from "axios";
import { getToken } from "./AuthService";

// Définit l'URL de base pour l'API des rôles.
const BASE_REST_API_URL = 'http://localhost:8080/api/roles';

// Ajoute un intercepteur de requête à Axios.
axios.interceptors.request.use(function (config) {
    // Cet intercepteur s'exécute avant chaque requête HTTP envoyée. 
    // Il modifie la configuration de la requête en ajoutant un en-tête d'autorisation avec le token JWT.
    // La fonction getToken() récupère le token stocké (si l'utilisateur est connecté).
    config.headers['Authorization'] = getToken();

    // Renvoie la configuration modifiée à Axios pour que la requête soit envoyée avec l'en-tête d'autorisation.
    return config;
  }, function (error) {
    // En cas d'erreur dans la configuration de la requête, rejette la promesse avec l'erreur.
    return Promise.reject(error);
  });
  
// Fonction pour récupérer tous les rôles disponibles à partir de l'API. 
// Utilise une requête GET vers l'URL spécifique de l'API des rôles.
export const getAllRoles = () => axios.get(BASE_REST_API_URL + "/allRoles")
