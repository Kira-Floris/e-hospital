package com.servlet_tomcat;

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

    
    private static final Pattern PHONE_REGEXT = Pattern.compile("^\\d{10}$");

    public static boolean isValidPhone(String phone){
        if (phone==null){
            return false;
        }
        return PHONE_REGEXT.matcher(phone).matches();
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        if (isValidPhone(this.phone) && passwordLength>8 && passwordLength<11){
            return "200__Ok";
        }
        else{
            return "400__Invalid Inputs; check your phone and password should be between 9 and 10 characters";
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
