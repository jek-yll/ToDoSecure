// Importe useState de React pour gérer les états locaux du composant.
import React, { useState } from 'react'
// Importe les fonctions depuis AuthService pour gérer l'authentification.
import { loginAPICall, saveLoggedInUser, storeToken } from '../services/AuthService';
// Importe useNavigate pour naviguer programmablement entre les routes.
import { useNavigate } from 'react-router-dom';
// Importe une fonction d'aide pour obtenir les détails de l'utilisateur connecté.
import { getUserDetails } from '../helpers/user-details';

// Définit le composant LoginComponent.
const LoginComponent = () => {

    // Déclare des états pour le nom d'utilisateur et le mot de passe avec des setters.
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    // Initialise le hook useNavigate.
    const navigator = useNavigate();

    // Définit une fonction asynchrone pour gérer la soumission du formulaire de connexion.
    async function handleLoginForm(e){

        e.preventDefault(); // Empêche le comportement par défaut du formulaire (rechargement de la page).

        // Appelle l'API de connexion et attend sa réponse.
        await loginAPICall(username, password).then((response) => {
            console.log(response.data); // Affiche les données de réponse dans la console.

            // Crée un token d'accès en préfixant le token reçu de l'API avec 'Bearer '.
            const token = 'Bearer ' + response.data.accessToken;
            console.log(token)
            console.log(getUserDetails()); // Affiche les détails de l'utilisateur dans la console.
            storeToken(token); // Stocke le token dans le stockage local ou une autre méthode de stockage.

            saveLoggedInUser(username); // Enregistre le nom d'utilisateur comme utilisateur connecté.
            navigator("/todos") // Navigue vers la route '/todos'.

            window.location.reload(false); // Recharge la page pour refléter l'état connecté (potentiellement déconseillé dans les SPAs modernes).
        }).catch(error => {
            console.error(error); // Affiche l'erreur en cas d'échec de la connexion.
        })

    }

  // Rendu du formulaire de connexion.
  return (
    <div className='container'>
        <br /> <br />
        <div className='row'>
            <div className='col-md-6 offset-md-3'>
                <div className='card'>
                    <div className='card-header'>
                        <h2 className='text-center'> Login Form </h2>
                    </div>

                    <div className='card-body'>
                        <form>
                            {/* Champ pour le nom d'utilisateur avec gestion de l'état et de la mise à jour. */}
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'> Username or Email</label>
                                <div className='col-md-9'>
                                    <input
                                        type='text'
                                        name='username'
                                        className='form-control'
                                        placeholder='Enter username'
                                        value={username}
                                        onChange={ (e) => setUsername(e.target.value)}
                                    >
                                    </input>
                                </div>
                            </div>

                            {/* Champ pour le mot de passe avec gestion de l'état et de la mise à jour. */}
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'> Password </label>
                                <div className='col-md-9'>
                                    <input
                                        type='password'
                                        name='password'
                                        className='form-control'
                                        placeholder='Enter password'
                                        value={password}
                                        onChange={ (e) => setPassword(e.target.value)}
                                    >
                                    </input>
                                </div>
                            </div>

                            {/* Bouton de soumission du formulaire qui déclenche handleLoginForm. */}
                            <div className='form-group mb-3'>
                                <button className='btn btn-primary' onClick={ (e) => handleLoginForm(e)}>Submit</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}

// Exporte le composant pour être utilisé dans d'autres parties de l'application.
export default LoginComponent
