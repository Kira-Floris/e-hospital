package com.servlet_tomcat;

public abstract class User{
    public String name;
    public Integer age;
    public String gender;
    public String role;
    public String userKey;

    public User(){

    }

    public User(String name, Integer age, String gender, String role){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.role = role;
    }

    public abstract String register();
    public abstract String login(String db_unique_identifier, String db_password, String unique_identifier, String password);
    // public abstract void logout();
}