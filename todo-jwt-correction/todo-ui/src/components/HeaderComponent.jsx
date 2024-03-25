// Importe React pour utiliser JSX et les fonctionnalités de React.
import React from 'react'
// Importe NavLink de react-router-dom pour créer des liens de navigation dans l'application.
import { NavLink } from 'react-router-dom'
// Importe des fonctions depuis AuthService pour vérifier l'état de connexion et déconnecter l'utilisateur.
import { isUserLoggedIn, logout } from '../services/AuthService'
// Importe useNavigate pour naviguer programmablement entre les routes.
import { useNavigate } from 'react-router-dom'

// Définit le composant HeaderComponent comme un composant fonctionnel.
const HeaderComponent = () => {

    // Utilise isUserLoggedIn pour vérifier si l'utilisateur est connecté et stocke le résultat dans isAuth.
    const isAuth = isUserLoggedIn();

    // Initialise le hook useNavigate pour pouvoir rediriger l'utilisateur.
    const navigator = useNavigate();

    // Définit une fonction pour gérer la déconnexion de l'utilisateur.
    function handleLogout(){
        logout(); // Appelle la fonction logout pour déconnecter l'utilisateur.
        navigator('/login') // Redirige l'utilisateur vers la page de connexion après la déconnexion.
    }

  // Rendu du composant, retourne le JSX pour l'en-tête.
  return (
    <div>
        <header>
            <nav className='navbar navbar-expand-md navbar-dark bg-dark'>
                <div>
                    <a href='http://localhost:3000' className='navbar-brand'>
                        Todo App React JWT // Nom de l'application affiché dans l'en-tête.
                    </a>
                </div>
                <div className='collapse navbar-collapse'>
                    <ul className='navbar-nav'>

                        {
                            isAuth && // Affiche le lien Todos uniquement si l'utilisateur est connecté.
                            <li className='nav-item'>
                            <NavLink to="/todos" className="nav-link">Todos</NavLink>
                        </li>
                        }

                    </ul>

                </div>
                <ul className='navbar-nav'>
                    {
                        !isAuth && // Affiche les liens Register et Login seulement si l'utilisateur n'est pas connecté.                         
                        <li className='nav-item'>
                        <NavLink to="/register" className="nav-link">Register</NavLink>
                    </li>
                    }

                    {
                        !isAuth &&    
                        <li className='nav-item'>
                        <NavLink to="/login" className="nav-link">Login</NavLink>
                    </li>
                    }

                    {
                        isAuth && // Affiche le lien Logout seulement si l'utilisateur est connecté.   
                        <li className='nav-item'>
                        <NavLink to="/login" className="nav-link" onClick={handleLogout}>Logout</NavLink>
                    </li>
                    }

                    </ul>
            </nav>
        </header>

    </div>
  )
}

// Exporte le composant pour être utilisé dans d'autres parties de l'application.
export default HeaderComponent
