import React, { useContext, useState } from "react";
import {Link} from "react-router-dom";

import AuthContext from "../context/AuthContext";

const Register = () => {
  let {registerUser} = useContext(AuthContext);
  const [errorMessage, setErrorMessage] = useState("");
  const [selectedButton, setSelectedButton] = useState("patient");

  const handleButtonClick = (event) => {
    setSelectedButton(event.target.value);
  };

  return (
    <div>
      <h1 className="text-center mt-5 mb-4">Register Page</h1>
      <div className="d-flex justify-content-center mb-3 mx-5 p-2">
        <button className="btn btn-primary rounded-0 mx-2" value="patient" onClick={handleButtonClick}>
          Patient Register
        </button>
        <button className="btn btn-primary rounded-0 mx-2" value="physician" onClick={handleButtonClick}>
          Physician Register
        </button>
        <button className="btn btn-primary rounded-0 mx-2" value="pharmacist" onClick={handleButtonClick}>
          Pharmacist Register
        </button>
      </div>


      {selectedButton === "patient" && (
        <div className="d-flex justify-content-center">
          <form className="container col-md-12 border rounded py-5 m-2 p-4 forms" onSubmit={registerUser} method='post'>
            <h2 className="text-center">Patient Register</h2>
            <div className="form-group my-2">
                <label>Username</label>
                <input type="text" className="form-control" name="username" placeholder="Enter your Username" required/>
            </div>
            <div className="form-group my-2">
                <label>Password</label>
                <input type="password" className="form-control" id="exampleInputPassword2" name="password" placeholder="Password" required/>
            </div>
            <div className="form-group my-2">
                <label>Names</label>
                <input type="text" className="form-control" name="names" placeholder="Enter your Names" required/>
            </div>
            <div className="form-group my-2">
                <label>Age</label>
                <input type="number" className="form-control" name="age" placeholder="Enter your Age" required/>
            </div>
            <div className="form-group my-2">
                <label>Gender</label><br/>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="M" id="flexRadioDefault1"/>
                  <label class="form-check-label" for="flexRadioDefault1">
                    Male
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="F" id="flexRadioDefault2"/>
                  <label class="form-check-label" for="flexRadioDefault2">
                    Female
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="Other" id="flexRadioDefault3"/>
                  <label class="form-check-label" for="flexRadioDefault2">
                    Other
                  </label>
                </div>
            </div>
            <input type="hidden" name="role" value="patient"/>
            <button type="submit" className="my-2 btn btn-primary">Submit</button>
            <div>
            <span><Link to="/login">Have an Account? Login</Link></span>
          </div>
          </form>
        </div>
      )}


      {selectedButton === "physician" && (
        <div className="d-flex justify-content-center">
        <form className="container col-md-12 border rounded py-5 m-2 p-4 forms" onSubmit={registerUser} method='post'>
          <h2 className="text-center">Physician Register</h2>
          <div className="form-group my-2">
              <label>Email</label>
              <input type="email" className="form-control" name="email" placeholder="Enter your Email" required/>
          </div>
          <div className="form-group my-2">
              <label>Password</label>
              <input type="password" className="form-control" id="exampleInputPassword2" name="password" placeholder="Password" required/>
          </div>
          <div className="form-group my-2">
                <label>Names</label>
                <input type="text" className="form-control" name="names" placeholder="Enter your Names" required/>
            </div>
            <div className="form-group my-2">
                <label>Age</label>
                <input type="number" className="form-control" name="age" placeholder="Enter your Age" required/>
            </div>
            <div className="form-group my-2">
                <label>Gender</label><br/>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="M" id="flexRadioDefault1"/>
                  <label class="form-check-label" for="flexRadioDefault1">
                    Male
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="F" id="flexRadioDefault2"/>
                  <label class="form-check-label" for="flexRadioDefault2">
                    Female
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="Other" id="flexRadioDefault3"/>
                  <label class="form-check-label" for="flexRadioDefault2">
                    Other
                  </label>
                </div>
            </div>
          <input type="hidden" name="role" value="physician"/>
          <button type="submit" className="my-2 btn btn-primary">Submit</button>
          <div>
            <span><Link to="/login">Have an Account? Login</Link></span>
          </div>
        </form>
      </div>
      )}


      {selectedButton === "pharmacist" && (
        <div className="d-flex justify-content-center">
        <form className="container col-md-12 border rounded py-5 m-2 p-4 forms" onSubmit={registerUser} method='post'>
          <h2 className="text-center">Pharmacist Register</h2>
          <div className="form-group my-2">
              <label>Phone</label>
              <input type="text" className="form-control" name="phone" placeholder="Enter your Phone" required/>
          </div>
          <div className="form-group my-2">
              <label>Password</label>
              <input type="password" className="form-control" id="exampleInputPassword2" name="password" placeholder="Password" required/>
          </div>
          <div className="form-group my-2">
                <label>Names</label>
                <input type="text" className="form-control" name="names" placeholder="Enter your Names" required/>
            </div>
            <div className="form-group my-2">
                <label>Age</label>
                <input type="number" className="form-control" name="age" placeholder="Enter your Age" required/>
            </div>
            <div className="form-group my-2">
                <label>Gender</label><br/>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="M" id="flexRadioDefault1"/>
                  <label class="form-check-label" for="flexRadioDefault1">
                    Male
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="F" id="flexRadioDefault2"/>
                  <label class="form-check-label" for="flexRadioDefault2">
                    Female
                  </label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="gender" value="Other" id="flexRadioDefault3"/>
                  <label class="form-check-label" for="flexRadioDefault2">
                    Other
                  </label>
                </div>
            </div>
          <input type="hidden" name="role" value="pharmacist"/>
          <button type="submit" className="my-2 btn btn-primary">Submit</button>
          <div>
            <span><Link to="/login">Have an Account? Login</Link></span>
          </div>
        </form>
      </div>
      )}
    </div>
  );
};

export default Register;