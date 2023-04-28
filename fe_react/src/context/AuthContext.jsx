import { createContext, useState, useEffect} from 'react';
import {useHistory} from 'react-router-dom';
import {toast} from "react-toastify";

import jwt_decode from 'jwt-decode';

const AuthContext = createContext();

export default AuthContext;


export const AuthProvider = ({children}) =>{
    
    let authExists = localStorage.getItem("authTokens")?JSON.parse(localStorage.getItem("authTokens")) : null;
    let userExists = localStorage.getItem("userData")?JSON.parse(localStorage.getItem("userData")) : null;

    let [authTokens, setAuthTokens] = useState(() => authExists);
    let [user, setUser] = useState(() => userExists);
    let [loading, setLoading] = useState(true);

    const history = useHistory();

    let loginUser = async(e) => {
        e.preventDefault();
        const url = `http://localhost:5000/api/auth/login/${e.target.role.value}`;
        let body;
        if (e.target.role.value==="pharmacist"){
            body = {
                "phone": e.target.phone.value.toString(),
                "password": e.target.password.value.toString()
            };
        }else if (e.target.role.value==="physician"){
            body = {
                "email": e.target.email.value.toString(),
                "password": e.target.password.value.toString()
            };
        }else {
            body = {
                "username": e.target.username.value.toString(),
                "password": e.target.password.value.toString()
            };
        }

        try{
            const response = await fetch(url, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(body)
            });
            console.log(response)
            let data = await response.json();
            if (response.status === 200){
                setAuthTokens(data.data.userKey);
                setUser(data.data);
                localStorage.setItem("authTokens", JSON.stringify(data.data.userKey));
                localStorage.setItem("userData", JSON.stringify(data.data));
                toast.success("Login successful");
                const temp = "/"+e.target.role.value;
                history.push(temp);
            }else{
                toast.error("Login Failed. Check your credentials");
            }
        } catch(err){
            console.log(err);
            setAuthTokens(null);
            setUser(null);
            localStorage.removeItem("authTokens");
            localStorage.removeItem("userData");
            toast.error("Login Failed");
            history.push("/login");
        }

    };

    let registerUser = async(e) => {
        e.preventDefault();
        const url = `http://localhost:5000/api/auth/register/${e.target.role.value}`;
        let body;
        if (e.target.role.value==="pharmacist"){
            body = {
                "phone": e.target.phone.value.toString(),
                "password": e.target.password.value.toString(),
                "name": e.target.names.value.toString(),
                "age": e.target.age.value,
                "gender": e.target.gender.value.toString(),
                "role": e.target.role.value
            };
        }else if (e.target.role.value==="physician"){
            body = {
                "email": e.target.email.value.toString(),
                "password": e.target.password.value.toString(),
                "name": e.target.names.value.toString(),
                "age": e.target.age.value,
                "gender": e.target.gender.value.toString(),
                "role": e.target.role.value
            };
        }else {
            body = {
                "username": e.target.username.value.toString(),
                "password": e.target.password.value.toString(),
                "name": e.target.names.value.toString(),
                "age": e.target.age.value,
                "gender": e.target.gender.value.toString(),
                "role": e.target.role.value
            };
        }

        try{
            const response = await fetch(url, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(body)
            });
            let data = await response.json();
            if (response.status === 200){
                setAuthTokens(data.data.userKey);
                setUser(data.data);
                localStorage.setItem("authTokens", JSON.stringify(data.data.userKey));
                localStorage.setItem("userData", JSON.stringify(data.data));
                toast.success("Registration Successful");
                const temp = "/"+e.target.role.value;
                history.push(temp);
            }
            else{
                toast.error("Registration Failure. Check your input");
            }
        } catch(err){
            setAuthTokens(null);
            setUser(null);
            localStorage.removeItem("authTokens");
            localStorage.removeItem("userData");
            toast.error("Registration Failed");
            history.push("/register");
        }
    };

    let logOut = () => {
        setAuthTokens(null);
        setUser(null);
        localStorage.removeItem("authTokens");
        localStorage.removeItem("userData");
    };

    let checkTokenValidation = async() => {
        const tok = localStorage.getItem("authTokens");
        console.log(tok);
        if(tok === undefined || tok === null){
            logOut();
        }
        if(loading){
            setLoading(false);
        }
    };


    useEffect(()=>{
        if(loading){
            checkTokenValidation();
        }
    },[authTokens, loading]);

    let contextData = {
        user,
        authTokens,
        loginUser:loginUser,
        registerUser:registerUser,
        logOut: logOut
    };

    return (
        <AuthContext.Provider value={contextData}>
            {children}
        </AuthContext.Provider>
    )
}