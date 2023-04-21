package com.servlet_tomcat;

import java.util.regex.*;

public class Physician extends User{
    public String email;
    public String password;
    static String temp_role = "physician";
    Pattern emailPattern;
    Matcher matcher;

    public Physician() {
        
    }

    public Physician(String email, String password, String name, Integer age, String gender) {
        super(name, age, gender, temp_role);
        this.email = email;
        this.password = password;
    }

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean isValidEmail(String email) {
        if (email==null){
            return false;
        }
        return EMAIL_REGEX.matcher(email).matches();
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        if (isValidEmail(this.email) && passwordLength>6 && passwordLength<9){
            return "200__Ok";
        }
        else{
            return "400__Invalid Inputs; Check you email and password should be between 7 and 8 characters";
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
