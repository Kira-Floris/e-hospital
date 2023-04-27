import React from 'react';
import { useContext, useState } from 'react';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";

import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Register from './components/Register';
import Login from './components/Login';
import Home from './components/Home';
import AuthContext, {AuthProvider} from './context/AuthContext';
import PrivateRoute from './utils/PrivateRoute';


function App() {
  const [message, setMessage] = useState("");
  return (
    <Router>
      <AuthProvider>
          <Navbar/>
          <Route path="/" exact><Home/></Route>
          <Route path="/register" exact><Register/></Route>
          <Route path="/login" exact><Login/></Route>
          <Footer/>
      </AuthProvider>
    </Router>
  );
}

export default App;
