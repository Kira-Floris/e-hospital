import React, {useContext, useState, useEffect} from 'react';
import {Link} from 'react-router-dom';

import AuthContext from '../context/AuthContext';


export default function Navbar() {
    const {authTokens, loggedUser, user, logOut} = useContext(AuthContext);
    const tokenExist = (localStorage.getItem('authTokens')!==null)?true:false;
    const [errorMessage, setErrorMessage] = useState("");
    const [userEmail, setUserEmail] = useState(null);
    const [userRole, setUserRole] = useState(null);
    let fetchUser = async () =>{
        let response = await fetch('/auth/me',{
            method:"GET",
            headers:{"Authorization":"Bearer "+String(authTokens)},
        });
        let data = await response.json();
        if (response.status===200){
            setUserEmail(data.email);
            setUserRole(data.role);
        }else{
            console.log('Something went wrong fetching email');
            setUserEmail(null);
            setUserRole(null);
        }
    }
    useEffect(()=>{
        fetchUser();
    },[]);
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light shadow">
        <div className="container">
            <Link className="navbar-brand" to="/" style={{color:"#70E7B5", fontSize:"2rem", fontWeight:"bold"}}>E-HOSP</Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
          <div className="collapse navbar-collapse" id="navbarText">
            <span className="me-auto"></span>
            <ul className="navbar-nav mb-2 mb-lg-0">
                <li className="nav-item h6 mx-3">
                    <Link className="nav-link link-secondary" to="/register">Register</Link>
                </li>
                <li className="nav-item h6">
                    <Link className="nav-link link-secondary mx-3" to="/login">Login</Link>
                </li>
                <li className="nav-item h6">
                    <Link className="nav-link link-secondary mx-3" to="/login" onClick={()=>logOut()}>Logout</Link>
                </li>
            </ul>
          </div>
        </div>
      </nav>
    )
  }
  