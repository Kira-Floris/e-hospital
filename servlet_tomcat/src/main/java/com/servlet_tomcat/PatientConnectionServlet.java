package com.servlet_tomcat;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
// import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientConnectionServlet extends HttpServlet {
    private final Gson gson = new Gson();

    // This function gets a list of 
    //  1. physicians
    //  2. pharmacist
    // so that a user can choose who to connect to
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();

        // check headers for key
        String token = request.getHeader("Authorization");
        if (db != null){
            if (token!=null){
                if (db.checkPatientExist(token)){
                    String role = request.getPathInfo().substring(1);
                    if (role.equals("physicians")){
                        Map<String, Physician> physicians = db.getPhysicianMap();
        
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = gson.toJson(new Util().physiciansSortByName(new Util().physiciansToList(physicians)));
        
                        out.print(json);
                        out.flush();
                    }
                    else if (role.equals("pharmacists")){
                        Map<String, Pharmacist> pharmacists = db.getPharmacistMap();
        
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = gson.toJson(new Util().pharmacistsSortByAge(new Util().pharmacistsToList(pharmacists)));
        
                        out.print(json);
                        out.flush();
                    }
                    else{
                        response.setStatus(400);
                        out.print(gson.toJson(new Util().message("Group you are trying to access is not available, choose between physicians or pharmacists")));
                    }
                }
                else{
                    response.setStatus(401);
                    out.print(gson.toJson(new Util().message("UnAuthorized")));
                }
            }
            else{
                response.setStatus(401);
                out.print(gson.toJson(new Util().message("UnAuthorized")));
            }
        }
        else{
            response.setStatus(500);
            out.print(gson.toJson(new Util().message("No DB found")));
        }
    }


    // this function grants access to a pharmacist or physician
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();
        
        // check headers for key
        String token = request.getHeader("Authorization");
        if (db != null){
            if (token!=null){
                if (db.checkPatientExist(token)){
                    String role = request.getPathInfo().substring(1);

                    if (role.equals("physicians")){
                        String physicianKey = gson.fromJson(reader, Identifier.class).key;
                        Physician physician = db.getPhysician(physicianKey);
        
                        if (physician != null && physicianKey != null){
                            String[] res = db.addPatientPhysician(token, physicianKey);
                            response.setStatus(Integer.parseInt(res[0]));
                            out.print(gson.toJson(new Util().message(res[1])));
                        }
                        else{
                            response.setStatus(404);
                            out.print(gson.toJson(new Util().message("Physician not found")));
                        }
                    }
                    else if (role.equals("pharmacists")){
                        String pharmacistKey = gson.fromJson(reader, Identifier.class).key;
                        Pharmacist pharmacist = db.getPharmacist(pharmacistKey);
        
                        if (pharmacist != null && pharmacistKey != null){
                            String[] res = db.addPatientPharmacist(token, pharmacistKey);
                            response.setStatus(Integer.parseInt(res[0]));
                            out.print(gson.toJson(new Util().message(res[1])));
                        }
                        else{
                            response.setStatus(404);
                            out.print(gson.toJson(new Util().message("Pharmacist not found")));
                        }
                    }
                    else{
                        response.setStatus(401);
                        out.print(gson.toJson(new Util().message("Group you are trying to access is not available, choose between physicians or pharmacists")));
                    }  
                }
                else{
                    response.setStatus(401);
                    out.print(gson.toJson(new Util().message("UnAuthorized")));
                }
            }
            else{
                response.setStatus(401);
                out.print(gson.toJson(new Util().message("UnAuthorized")));
            }
        }
        else{
            response.setStatus(500);
            out.print(gson.toJson(new Util().message("No DB found")));
        }
    }
}
