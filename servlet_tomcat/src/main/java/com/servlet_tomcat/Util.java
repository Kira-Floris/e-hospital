package com.servlet_tomcat;

public class Util {
    public String createKey(String role, String unique_identifier){
        return role + "__" + unique_identifier;
    }

    public String[] response(String status){
        return status.split("__");
    }

    public Message message(String text){
        return new Message(text);
    } 
    class Message{
        public String message;
        Message(String message){
            this.message = message;
        }
    }

    public Token token(String key){
        return new Token(key);
    }

    class Token{
        public String token;
        Token(String key){
            this.token = key;
        }
    }
    
}