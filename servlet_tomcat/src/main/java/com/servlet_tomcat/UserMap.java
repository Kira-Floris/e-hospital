package com.servlet_tomcat;

import java.util.LinkedHashMap;

public class UserMap {
    public LinkedHashMap<String, User> userData = new LinkedHashMap<>();

    public boolean checkUserExist(String userKey){
        return this.userData.containsKey(userKey);
    }

    private String createKey(User user, String unique_identifier){
        return user.role + '_' + unique_identifier; 
    }

    private String[] response(String status){
        return status.split("__");
    }

    public String[] addUser(User user, String unique_identifier){
        String[] checkValidation = response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = this.createKey(user, unique_identifier);
            if (this.checkUserExist(userKey)){
                return response("400__unique identifier already exists");
            }
            else{
                this.userData.put(this.createKey(user, unique_identifier), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }
}
