import React, { useState, useContext } from "react";
import {useHistory} from 'react-router-dom';

import AuthContext from "../context/AuthContext";

const Login = () => {
  let {loginUser} = useContext(AuthContext);
  let {logoutUser, user, authTokens} = useContext(AuthContext);
  const [errorMessage, setErrorMessage] = useState("");
  const [selectedButton, setSelectedButton] = useState("patient");

  const handleButtonClick = (event) => {
    setSelectedButton(event.target.value);
  };

  return (
    <div>
      <h1 className="text-center mt-5 mb-4">Login Page</h1>
      <div className="d-flex justify-content-center mb-3 mx-5 p-2">
        <button className="btn btn-primary rounded-0 mx-2" value="patient" onClick={handleButtonClick}>
          Patient Login
        </button>
        <button className="btn btn-primary rounded-0 mx-2" value="physician" onClick={handleButtonClick}>
          Physician Login
        </button>
        <button className="btn btn-primary rounded-0 mx-2" value="pharmacist" onClick={handleButtonClick}>
          Pharmacist Login
        </button>
      </div>


      {selectedButton === "patient" && (
        <div className="d-flex justify-content-center">
          <form className="container col-md-12 border rounded py-5 m-2 p-4 forms" onSubmit={loginUser} method='post'>
            <h2 className="text-center">Patient Login</h2>
            <div className="form-group my-2">
                <label>Username</label>
                <input type="text" className="form-control" name="username" placeholder="Enter your Username" required/>
            </div>
            <div className="form-group my-2">
                <label>Password</label>
                <input type="password" className="form-control" id="exampleInputPassword2" name="password" placeholder="Password" required/>
            </div>
            <input type="hidden" name="role" value="patient"/>
            <button type="submit" className="my-2 btn btn-primary">Submit</button>
          </form>
        </div>
      )}


      {selectedButton === "physician" && (
        <div className="d-flex justify-content-center">
        <form className="container col-md-12 border rounded py-5 m-2 p-4 forms" onSubmit={loginUser} method='post'>
          <h2 className="text-center">Physician Login</h2>
          <div className="form-group my-2">
              <label>Email</label>
              <input type="email" className="form-control" name="email" placeholder="Enter your Email" required/>
          </div>
          <div className="form-group my-2">
              <label>Password</label>
              <input type="password" className="form-control" id="exampleInputPassword2" name="password" placeholder="Password" required/>
          </div>
          <input type="hidden" name="role" value="physician"/>
          <button type="submit" className="my-2 btn btn-primary">Submit</button>
        </form>
      </div>
      )}


      {selectedButton === "pharmacist" && (
        <div className="d-flex justify-content-center">
        <form className="container col-md-12 border rounded py-5 m-2 p-4 forms" onSubmit={loginUser} method='post'>
          <h2 className="text-center">Pharmacist Login</h2>
          <div className="form-group my-2">
              <label>Phone</label>
              <input type="text" className="form-control" name="phone" placeholder="Enter your Phone" required/>
          </div>
          <div className="form-group my-2">
              <label>Password</label>
              <input type="password" className="form-control" id="exampleInputPassword2" name="password" placeholder="Password" required/>
          </div>
          <input type="hidden" name="role" value="pharmacist"/>
          <button type="submit" className="my-2 btn btn-primary">Submit</button>
        </form>
      </div>
      )}
    </div>
  );
};

export default Login;