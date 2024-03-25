// Importe Axios pour faire des appels HTTP et des fonctions depuis AuthService et user-details pour l'authentification et les infos utilisateur.
import axios from "axios";
import { getToken } from "./AuthService";
import { getUserDetails } from "../helpers/user-details";

// Base URL de l'API.
const BASE_REST_API_URL = 'http://localhost:8080/api/';

// Obtient les détails de l'utilisateur connecté, y compris son identifiant.
const userDetails = getUserDetails();
const idUser = userDetails?.id; // Utilise l'opérateur optionnel de chaînage pour gérer le cas où userDetails serait null/undefined.

// Ajoute un intercepteur de requête à Axios pour inclure le token JWT dans chaque requête.
axios.interceptors.request.use(function (config) {
    config.headers['Authorization'] = getToken(); // Ajoute le token JWT à l'en-tête d'autorisation.
    return config; // Retourne la configuration modifiée pour l'exécution de la requête.
  }, function (error) {
    // Gère l'erreur de configuration de la requête et la rejette.
    return Promise.reject(error);
  });
  
// Fonction pour obtenir toutes les tâches associées à l'utilisateur connecté.
export const getAllTodos = () => axios.get(BASE_REST_API_URL + 'todos/' + idUser + '/allTodos')

// Fonction pour obtenir les tâches spécifiques de l'utilisateur connecté.
export const getTodos = () => axios.get(BASE_REST_API_URL + 'todos/' + idUser + '/allTodosUser')

// Fonction pour sauvegarder une nouvelle tâche.
export const saveTodo = (todo) => axios.post(BASE_REST_API_URL + 'todos/' + idUser, todo)

// Fonction pour obtenir une tâche spécifique par son ID.
export const getTodo = (idTodo) => axios.get(BASE_REST_API_URL + 'todos/' + idUser + '/todo/' + idTodo)

// Fonction pour mettre à jour une tâche spécifique.
export const updateTodo = (idTodo, todo) => axios.put(BASE_REST_API_URL + 'todos/' + idUser + '/update/' + idTodo, todo)

// Fonction pour supprimer une tâche spécifique.
export const deleteTodo = (idTodo) => axios.delete(BASE_REST_API_URL + 'todos/' + idUser + '/delete/' + idTodo)

// Fonction pour marquer une tâche spécifique comme complétée.
export const completeTodo = (idTodo) => axios.patch(BASE_REST_API_URL + 'todos/' + idUser + '/complete/' + idTodo)

// Fonction pour marquer une tâche spécifique comme incomplète.
export const inCompleteTodo = (idTodo) => axios.patch(BASE_REST_API_URL + 'todos/' + idUser + '/in-complete/' + idTodo)
