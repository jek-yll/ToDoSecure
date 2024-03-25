import React, { useEffect, useState } from 'react'
import { completeTodo, deleteTodo, getAllTodos, inCompleteTodo, getTodos } from '../services/TodoService'
import { useNavigate } from 'react-router-dom'
import { getUserDetails } from '../helpers/user-details'

const ListTodoComponent = () => {

    // Déclare l'état pour stocker les todos et le setter pour le modifier.
    const [todos, setTodos] = useState([])
    // Déclare l'état pour vérifier si l'utilisateur est de type 'user' et le setter correspondant.
    const [isUser, setIsUser] = useState();

    // Initialise le hook useNavigate.
    const navigate = useNavigate()

    // Utilise useEffect pour exécuter le code au montage du composant.
    useEffect(() => {
        const userDetails = getUserDetails(); // Obtient les détails de l'utilisateur connecté.
        console.log(userDetails)
        if (userDetails && userDetails.role === 'ROLE_USER') { // Vérifie si l'utilisateur est de type 'user'.
            setIsUser(true)
            listTodosRoleUSer() // Appelle la fonction pour lister les todos pour les utilisateurs.
            console.log("user " + userDetails)
        } else {
            console.log("admin " + userDetails)
            listTodos(); // Appelle la fonction pour lister tous les todos.
        }
    }, []) // Le tableau vide indique que cet effet ne dépend d'aucune variable d'état et ne s'exécutera qu'une fois.

    // Fonction pour obtenir tous les todos depuis le backend et les stocker dans l'état.
    function listTodos() {
        getAllTodos().then((response) => {
            console.log(response)
            setTodos(response.data); // Met à jour l'état des todos avec les données reçues.
        }).catch(error => {
            console.error(error);
        })
    }

    // Fonction similaire à listTodos mais spécifique aux rôles d'utilisateur.
    function listTodosRoleUSer() {
        getTodos().then((response) => {
            console.log(response)
            setTodos(response.data) // Met à jour l'état des todos.
        }).catch(error => {
            console.log(error)
        })
    }

    // Fonction pour naviguer vers le formulaire d'ajout d'un nouveau todo.
    function addNewTodo() {
        navigate('/add-todo')
    }

    // Fonction pour naviguer vers le formulaire de mise à jour d'un todo.
    function updateTodo(id) {
        console.log(id)
        navigate(`/update-todo/${id}`)
    }

    // Fonction pour supprimer un todo.
    function removeTodo(id) {
        deleteTodo(id).then(() => {
            listTodos(); // Rafraîchit la liste des todos après suppression.
        }).catch(error => {
            console.error(error)
        })
    }

    // Fonction pour marquer un todo comme complet.
    function markCompleteTodo(id) {
        completeTodo(id).then(() => {
            listTodos() // Rafraîchit la liste des todos.
        }).catch(error => {
            console.error(error)
        })
    }

    // Fonction pour marquer un todo comme incomplet.
    function markInCompleteTodo(id) {
        inCompleteTodo(id).then(() => {
            listTodos(); // Rafraîchit la liste des todos.
        }).catch(error => {
            console.error(error)
        })
    }


    return (
        <div className='container'>
            <h2 className='text-center'>List of Todos</h2>
            <button className='btn btn-primary mb-2' onClick={addNewTodo}>Add Todo</button>
            <div>
                <table className='table table-bordered table-striped'>
                    <thead>
                        <tr>
                            <th>Todo Title</th>
                            <th>Todo Description</th>
                            <th>Todo Completed</th>
                            {!isUser && (<th>Actions</th>)}
                        </tr>
                    </thead>
                    <tbody>
                        {
                            todos.map(todo =>
                                <tr key={todo.id}>
                                    <td>{todo.title}</td>
                                    <td>{todo.description}</td>
                                    <td>{todo.completed ? 'YES' : 'NO'}</td>
                                    {!isUser && (<td>
                                        <button className='btn btn-info' onClick={() => updateTodo(todo.id)}>Update</button>
                                        <button className='btn btn-danger' onClick={() => removeTodo(todo.id)} style={{ marginLeft: "10px" }} >Delete</button>
                                        <button className='btn btn-success' onClick={() => markCompleteTodo(todo.id)} style={{ marginLeft: "10px" }} >Complete</button>
                                        <button className='btn btn-info' onClick={() => markInCompleteTodo(todo.id)} style={{ marginLeft: "10px" }} >In Complete</button>
                                    </td>)
                                    }
                                </tr>
                            )
                        }


                    </tbody>
                </table>
            </div>

        </div>
    )
}

export default ListTodoComponent