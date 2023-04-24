package com.servlet_tomcat;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
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
                String key = new Util().createKey(patient.role, patient.username);
                patient.userKey = key;
                
                String[] res = userDataMap.addPatient(patient);
                if (res[0].equals("200")){
                    Patient userTemp = userDataMap.getPatient(key);
                    response.setStatus(200);
                    out.print(gson.toJson(userTemp));
                }
                else{
                    response.setStatus(Integer.parseInt(res[0]));
                    out.print(gson.toJson(new Util().message(res[1])));
                }
            }
            else if (role.equals("physician")){
                Physician physician = gson.fromJson(reader, Physician.class);
                String key = new Util().createKey(physician.role, physician.email);
                physician.userKey = key;
                
                String[] res = userDataMap.addPhysician(physician);
                if (res[0].equals("200")){
                    Physician userTemp = userDataMap.getPhysician(key);
                    out.print(gson.toJson(userTemp));
                }
                else{
                    response.setStatus(Integer.parseInt(res[0]));
                    out.print(gson.toJson(new Util().message(res[1])));
                }

            }
            else if (role.equals("pharmacist")){
                Pharmacist pharmacist = gson.fromJson(reader, Pharmacist.class);
                String key = new Util().createKey(pharmacist.role, pharmacist.phone);
                pharmacist.userKey = key;
                
                String[] res = userDataMap.addPharmacist(pharmacist);
                if (res[0].equals("200")){
                    Pharmacist userTemp = userDataMap.getPharmacist(key);
                    response.setStatus(200);
                    out.print(gson.toJson(userTemp));
                }
                else{
                    response.setStatus(Integer.parseInt(res[0]));
                    out.print(gson.toJson(new Util().message(res[1])));
                }
            }
            else{
                response.setStatus(400);
                out.print(gson.toJson(new Util().message("User role must be patient, physician or pharmacist")));
            }
        }
        else{
            response.setStatus(500);
            out.print(gson.toJson(new Util().message("DB not Found")));
        }
    }
}