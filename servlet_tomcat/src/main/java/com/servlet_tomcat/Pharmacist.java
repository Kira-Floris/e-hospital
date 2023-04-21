package com.servlet_tomcat;

public class Pharmacist extends User {
    public String phone;
    public String password;
    static String temp_role = "pharmacist";

    public Pharmacist(String phone, String password, String name, Integer age, String gender){
        super(name, age, gender, temp_role);
        this.phone = phone;
        this.password = password;
    }

    @Override
    public String register(){
        int passwordLength = this.password.length();
        if (passwordLength>8 && passwordLength<11){
            return "200__Ok";
        }
        else{
            return "400__password must be between 9 and 10 characters";
        }
    }

    @Override
    public String login(){
        return "";
    }

}
