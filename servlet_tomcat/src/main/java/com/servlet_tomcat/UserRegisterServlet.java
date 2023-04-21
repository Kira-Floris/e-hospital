package com.servlet_tomcat;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet("/user")
public class UserRegisterServlet extends HttpServlet {
    // public UserMap userMap = new UserMap();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        DatabaseSingletonMap userDataMap = DatabaseSingletonMap.getInstance();

        if (userDataMap != null){
            String role = request.getPathInfo().substring(1);

            if (role.equals("patient")){
                Patient patient = gson.fromJson(reader, Patient.class);
                
                String[] res = userDataMap.addPatient(patient);
                if (res[0].equals("200")){
                    Patient userTemp = userDataMap.getPatient(userDataMap.createKey(patient, patient.username)); 
                    response.setStatus(200);
                    out.print(gson.toJson(userTemp));
                }
                else{
                    response.setStatus(Integer.parseInt(res[0]));
                    out.print(gson.toJson(new ErrorMessage(res[1])));
                }
            }
            else if (role.equals("physician")){
                Physician physician = gson.fromJson(reader, Physician.class);
                
                String[] res = userDataMap.addPhysician(physician);
                if (res[0].equals("200")){
                    Physician userTemp = userDataMap.getPhysician(userDataMap.createKey(physician, physician.email)); 
                    out.print(gson.toJson(userTemp));
                }
                else{
                    response.setStatus(Integer.parseInt(res[0]));
                    out.print(gson.toJson(new ErrorMessage(res[1])));
                }

            }
            else if (role.equals("pharmacist")){
                Pharmacist pharmacist = gson.fromJson(reader, Pharmacist.class);
                
                String[] res = userDataMap.addPharmacist(pharmacist);
                if (res[0].equals("200")){
                    Pharmacist userTemp = userDataMap.getPharmacist(userDataMap.createKey(pharmacist, pharmacist.phone)); 
                    response.setStatus(200);
                    out.print(gson.toJson(userTemp));
                }
                else{
                    response.setStatus(Integer.parseInt(res[0]));
                    out.print(gson.toJson(new ErrorMessage(res[1])));
                }
            }
            else{
                response.setStatus(400);
                out.print(gson.toJson(new ErrorMessage("User role must be patient, physician or pharmacist")));
            }
        }
        else{
            response.setStatus(404);
            out.print(gson.toJson(new ErrorMessage("No Data Found")));
        }



        // Todo todo = gson.fromJson(reader, Todo.class);
        
        // out.print(role);

    }
}

class ErrorMessage{
    public String message;
    ErrorMessage(String message){
        this.message = message;
    }
}