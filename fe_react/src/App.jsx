import React from 'react';
import { useContext, useState } from 'react';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from 'react-toastify';


import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Register from './components/Register';
import Login from './components/Login';
import Patient from './components/Patient';
import Physician from './components/Physician';
import Pharmacist from './components/Pharmacist';
import AuthContext, {AuthProvider} from './context/AuthContext';
import PrivateRoute from './utils/PrivateRoute';
import PatientRoute from './utils/PatientRoute';
import PharmacistRoute from './utils/PharmacistRoute';
import PhysicianRoute from './utils/PhysicianRoute';


function App() {
  const [message, setMessage] = useState("");
  return (
    <Router>
      <ToastContainer/>
      <AuthProvider>
          <Navbar/>
          <Route path="/register" exact><Register/></Route>
          <Route path="/login" exact><Login/></Route>
          <PrivateRoute>
            <PatientRoute>
              <Route path="/patient" exact><Patient/></Route>
            </PatientRoute>
            <PhysicianRoute>
              <Route path="/physician" exact><Physician/></Route>
            </PhysicianRoute>
            <PharmacistRoute>
              <Route path="/pharmacist" exact><Pharmacist/></Route>
            </PharmacistRoute>
          </PrivateRoute>
          <Footer/>
      </AuthProvider>
    </Router>
  );
}

export default App;
