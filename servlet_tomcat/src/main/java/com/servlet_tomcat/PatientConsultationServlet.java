package com.servlet_tomcat;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientConsultationServlet  extends HttpServlet{
    private final Gson gson = new Gson();

    // @Override
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
                    Map<String, PhysicianConsultationSchema> myConsultations = db.getPhysicianConsultationMap();
                    List<PhysicianConsultationSchema> consultations = new ArrayList<>();
                    for (Map.Entry<String, PhysicianConsultationSchema> entry: myConsultations.entrySet()){
                        String key = entry.getKey();
                        if (token.equals(new Util().splitCombinationKey(key)[1])){
                            consultations.add(entry.getValue());
                        }
                    }
                    String json = gson.toJson(consultations);
                    response.setStatus(200);
                    out.print(json);
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
