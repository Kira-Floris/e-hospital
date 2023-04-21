package com.servlet_tomcat;

public class Patient extends User {
    public String username;
    public String password;
    static String temp_role = "patient";

    public Patient(){
        
    }

    public Patient(String username, String password, String name, Integer age, String gender){
        super(name, age, gender, temp_role);
        this.username = username;
        this.password = password;       
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        if (passwordLength>3 && passwordLength<7){
            return "200__Ok";
        }
        else{
            return "400__password must be between 4 and 6 characters";
        }
    }

    @Override
    public String login(String db_username, String db_password, String username, String password){
        if (db_username.equals(username) && db_password.equals(password)){
            return "200__Ok"; 
        }
        return "400__invalid credentials";
    }
}
