package com.servlet_tomcat;

import java.util.regex.*;

public class Physician extends User{
    public String email;
    public String password;
    static String temp_role = "physician";

    public Physician() {
        
    }

    public Physician(String email, String password, String name, Integer age, String gender) {
        super(name, age, gender, temp_role);
        this.email = email;
        this.password = password;
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        Pattern emailPattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        matcher = emailPattern.matcher(this.email);
        if (matcher.matches() && passwordLength>6 && passwordLength<9){
            return "200__Ok";
        }
        else if (matcher.matches()!=true && passwordLength>6 && passwordLength<9){
            return "400__Email should be valid";
        }
        else{
            return "400__password must be between 4 and 6 characters";
        }
    }

    @Override
    public String login(String db_email, String db_password, String email, String password){
        if (db_email.equals(email) && db_password.equals(password)){
            return "200__Ok"; 
        }
        return "400__invalid credentials";
    }
    
}
