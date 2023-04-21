package com.servlet_tomcat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pharmacist extends User {
    public String phone;
    public String password;
    static String temp_role = "pharmacist";

    public Pharmacist(){
        
    }

    public Pharmacist(String phone, String password, String name, Integer age, String gender){
        super(name, age, gender, temp_role);
        this.phone = phone;
        this.password = password;
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        Pattern phonePattern;
        Matcher matcher;
        String PHONE_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        phonePattern = Pattern.compile(PHONE_PATTERN);
        matcher = phonePattern.matcher(this.phone);
        if (matcher.matches() && passwordLength>8 && passwordLength<11){
            return "200__Ok";
        }
        else if (matcher.matches() && passwordLength>8 && passwordLength<11){
            return "400__phone number should be valid";
        }
        else{
            return "400__password must be between 9 and 10 characters";
        }
    }

    @Override
    public String login(String db_phone, String db_password, String phone, String password){
        if (db_phone.equals(phone) && db_password.equals(password)){
            return "200__Ok"; 
        }
        return "400__invalid credentials";
    }

}
