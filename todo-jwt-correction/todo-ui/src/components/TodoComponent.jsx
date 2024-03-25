// Importe React et ses hooks pour gérer les états et les effets de bord du composant.
import React, { useEffect, useState } from 'react'
// Importe les fonctions pour interagir avec l'API des tâches et les hooks pour la navigation et l'accès aux paramètres d'URL.
import { getTodo, saveTodo, updateTodo } from '../services/TodoService'
import { useNavigate, useParams } from 'react-router-dom'

// Définit le composant fonctionnel TodoComponent.
const TodoComponent = () => {
    // Déclare les états locaux pour gérer le titre, la description, et l'état de complétion de la tâche.
    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('')
    const [completed, setCompleted] = useState(false)

    // Utilise le hook useNavigate pour la redirection programmée.
    const navigate = useNavigate()

    // Récupère l'identifiant de la tâche depuis l'URL, si présent.
    const { id } = useParams()

    // Gère la soumission du formulaire pour créer ou mettre à jour une tâche.
    function saveOrUpdateTodo(e){
        e.preventDefault() // Empêche le comportement par défaut du formulaire (rechargement de la page).

        // Prépare l'objet todo avec les données saisies.
        const todo = {title, description, completed}
        console.log(todo);

        // Vérifie si un identifiant est présent pour déterminer s'il faut créer ou mettre à jour la tâche.
        if(id){
            // Met à jour la tâche existante.
            updateTodo(id, todo).then(() => {
                navigate('/todos') // Redirige vers la liste des tâches après la mise à jour.
            }).catch(error => {
                console.error(error); // Affiche l'erreur en cas de problème.
            })
        }else{
            // Crée une nouvelle tâche.
            saveTodo(todo).then((response) => {
                console.log(response.data) // Affiche les données de réponse pour débogage.
                navigate('/todos') // Redirige vers la liste des tâches après la création.
            }).catch(error => {
                console.error(error); // Affiche l'erreur en cas de problème.
            })
        }
    }

    // Détermine le titre de la page en fonction de la présence d'un identifiant dans l'URL.
    function pageTitle(){
        if(id) {
            return <h2 className='text-center'>Update Todo</h2>
        }else {
            return <h2 className='text-center'>Add Todo</h2>
        }
    }

    // Utilise useEffect pour charger les données de la tâche à modifier lors du montage du composant.
    useEffect(() => {
        if(id){
            getTodo(id).then((response) => {
                // Initialise les états locaux avec les données de la tâche à modifier.
                setTitle(response.data.title)
                setDescription(response.data.description)
                setCompleted(response.data.completed)
            }).catch(error => {
                console.error(error); // Affiche l'erreur en cas de problème.
            })
        }
    }, [id]) // L'effet dépend de l'identifiant pour se réexécuter si celui-ci change.

    // Rendu du composant, incluant le formulaire pour saisir ou modifier les données d'une tâche.
    return (
        <div className='container'>
            {/* Structure du formulaire et mise en page */}
            {pageTitle()} {/* Affiche le titre de la page */}
            <div className='card-body'>
                <form>
                    {/* Champs du formulaire pour saisir le titre, la description, et l'état de complétion de la tâche */}
                    <button className='btn btn-success' onClick={saveOrUpdateTodo}>Submit</button>
                </form>
            </div>
        </div>
    )
}

// Exporte le composant pour être utilisé ailleurs dans l'application.
export default TodoComponent
