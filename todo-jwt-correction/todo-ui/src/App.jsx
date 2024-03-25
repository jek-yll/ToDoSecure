// Importe le hook useState de React et les styles CSS de l'application.
import { useState } from 'react'
import './App.css'

// Importe les composants utilisés dans l'application.
import ListTodoComponent from './components/ListTodoComponent'
import HeaderComponent from './components/HeaderComponent'
import FooterComponent from './components/FooterComponent'
import TodoComponent from './components/TodoComponent'
import RegisterComponent from './components/RegisterComponent'
import LoginComponent from './components/LoginComponent'

// Importe les éléments nécessaires de react-router-dom pour le routage, et le service d'authentification.
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { isUserLoggedIn } from './services/AuthService'

// Définit le composant principal App.
function App() {

  // Définit un composant AuthenticatedRoute pour encapsuler les routes nécessitant une authentification.
  function AuthenticatedRoute({children}){
    // Vérifie si l'utilisateur est connecté.
    const isAuth = isUserLoggedIn();

    // Si l'utilisateur est authentifié, retourne les enfants (le contenu protégé), sinon redirige vers la page de connexion.
    if(isAuth) {
      return children;
    }

    return <Navigate to="/" />
  }

  // Rendu du composant App avec BrowserRouter pour gérer le routage dans l'application.
  return (
    <>
      <BrowserRouter>
          <HeaderComponent /> {/* Affiche l'en-tête sur toutes les pages */}
            <Routes>
              {/* Route pour la page de connexion */}
              <Route path='/' element={<LoginComponent />} />
              
              {/* Routes protégées nécessitant une authentification */}
              <Route path='/todos' element={
                <AuthenticatedRoute>
                  <ListTodoComponent />
                </AuthenticatedRoute>
              } />
              
              <Route path='/add-todo' element={
                <AuthenticatedRoute>
                  <TodoComponent />
                </AuthenticatedRoute>
              } />
              
              <Route path='/update-todo/:id' element={
                <AuthenticatedRoute>
                  <TodoComponent />
                </AuthenticatedRoute>
              } />
              
              {/* Route pour la page d'inscription */}
              <Route path='/register' element={<RegisterComponent />} />
              
              {/* Une autre route pour la page de connexion accessible directement via '/login' */}
              <Route path='/login' element={<LoginComponent />} />
            </Routes>
          <FooterComponent /> {/* Affiche le pied de page sur toutes les pages */}
      </BrowserRouter>
    </>
  )
}

// Exporte le composant App pour être utilisé dans d'autres parties de l'application.
export default App
