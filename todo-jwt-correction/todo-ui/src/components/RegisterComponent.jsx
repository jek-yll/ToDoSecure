import React, { useState, useEffect } from 'react'
import { registerAPICall } from '../services/AuthService'
import { getAllRoles } from '../services/RoleService'
import { useNavigate } from 'react-router-dom'




const RegisterComponent = () => {

   // Déclare des états pour chaque champ du formulaire d'inscription.
   const [name, setName] = useState('')
   const [username, setUsername] = useState('')
   const [email, setEmail] = useState('')
   const [password, setPassword] = useState('')
   const [roles, setRoles] = useState([]); // Contiendra les rôles récupérés de l'API.
   const [selectedRole, setSelectedRole] = useState(''); // Stocke le rôle sélectionné.

   // Initialise useNavigate pour la redirection après inscription.
   const navigator = useNavigate();

   // Un objet pour convertir les identifiants de rôle en labels lisibles.
   const roleLabels = {
       'ROLE_ADMIN': 'Admin',
       'ROLE_USER': 'User'
   };

   // useEffect pour récupérer les rôles dès le chargement du composant.
   useEffect(() => {
       getAllRoles().then(response => {
           setRoles(response.data); // Met à jour l'état des rôles avec ceux récupérés de l'API.
           if (response.data.length > 0) {
               setSelectedRole(response.data[0].id); // Pré-sélectionne le premier rôle par défaut.
           }
       }).catch(error => {
           console.error('There was an error fetching the roles', error);
       });
   }, []); // Le tableau vide indique que cet effet ne s'exécute qu'au montage du composant.

   // Gère la soumission du formulaire d'inscription.
   function handleRegistrationForm(e) {
       e.preventDefault(); // Empêche le comportement par défaut du formulaire.

       // Prépare l'objet d'inscription avec les données du formulaire et le rôle sélectionné.
       const registerData = { name, username, email, password, idRole: selectedRole }; 

       console.log("register", registerData);

       // Appelle l'API d'inscription avec les données collectées.
       registerAPICall(registerData).then((response) => {
           console.log(response.data);
           navigator("/login") // Redirige vers la page de connexion après une inscription réussie.
       }).catch(error => {
           console.error(error); // Gère les erreurs éventuelles.
       });
   }
   
    return (
        <div className='container'>
            <br /> <br />
            <div className='row'>
                <div className='col-md-6 offset-md-3'>
                    <div className='card'>
                        <div className='card-header'>
                            <h2 className='text-center'> User Registration Form </h2>
                        </div>

                        <div className='card-body'>
                            <form>
                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'> Name </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='text'
                                            name='name'
                                            className='form-control'
                                            placeholder='Enter name'
                                            value={name}
                                            onChange={(e) => setName(e.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'> Username </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='text'
                                            name='username'
                                            className='form-control'
                                            placeholder='Enter username'
                                            value={username}
                                            onChange={(e) => setUsername(e.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>


                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'> Email </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='text'
                                            name='email'
                                            className='form-control'
                                            placeholder='Enter email address'
                                            value={email}
                                            onChange={(e) => setEmail(e.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'> Password </label>
                                    <div className='col-md-9'>
                                        <input
                                            type='password'
                                            name='password'
                                            className='form-control'
                                            placeholder='Enter password'
                                            value={password}
                                            onChange={(e) => setPassword(e.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="roles" className="form-label">Rôle</label>
                                    <select
                                        className="form-control"
                                        id="roles"
                                        value={selectedRole}
                                        onChange={(e) => setSelectedRole(e.target.value)}
                                        required
                                    >
                                        {roles.map((role) => (
                                             <option key={role.id} value={role.id}>{roleLabels[role.name] || 'Non défini'}</option>
                                        ))}
                                    </select>
                                </div>

                                <div className='form-group mb-3'>
                                    <button className='btn btn-primary' onClick={(e) => handleRegistrationForm(e)}>Submit</button>

                                </div>
                            </form>

                        </div>

                    </div>
                </div>
            </div>


        </div>
    )
}

export default RegisterComponent