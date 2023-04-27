package com.servlet_tomcat;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();

        if (db != null){
            String role = request.getPathInfo().substring(1);

            if (role.equals("patient")){
                Patient temp = gson.fromJson(reader, Patient.class);
                Patient patient = db.getPatient(new Util().createKey("patient", temp.username));
                if (patient!=null){
                    String[] log_res = new Util().response(new Patient().login(patient.username, patient.password, temp.username, temp.password));
                    if (log_res[0].equals("200")){
                        response.setStatus(200);
                        out.print(gson.toJson(patient));
                    }
                    else{
                        response.setStatus(Integer.parseInt(log_res[0]));
                        out.print(gson.toJson(new Util().message(log_res[1])));
                    }
                }
                else{
                    response.setStatus(404);
                    out.print(gson.toJson(new Util().message("User not found")));
                }
            }
            else if(role.equals("physician")){
                Physician temp = gson.fromJson(reader, Physician.class);
                Physician physician = db.getPhysician(new Util().createKey("physician", temp.email));
                if (physician!=null){
                    String[] log_res = new Util().response(new Physician().login(physician.email, physician.password, temp.email, temp.password));
                    if (log_res[0].equals("200")){
                        response.setStatus(200);
                        out.print(gson.toJson(physician));
                    }
                    else{
                        response.setStatus(Integer.parseInt(log_res[0]));
                        out.print(gson.toJson(new Util().message(log_res[1])));
                    }
                }
                else{
                    response.setStatus(404);
                    out.print(gson.toJson(new Util().message("User not found")));
                }
            }
            else if(role.equals("pharmacist")){
                Pharmacist temp = gson.fromJson(reader, Pharmacist.class);
                Pharmacist pharmacist = db.getPharmacist(new Util().createKey("pharmacist", temp.phone));
                if (pharmacist!=null){
                    String[] log_res = new Util().response(new Pharmacist().login(pharmacist.phone, pharmacist.password, temp.phone, temp.password));
                    if (log_res[0].equals("200")){
                        response.setStatus(200);
                        out.print(gson.toJson(pharmacist));
                    }
                    else{
                        response.setStatus(Integer.parseInt(log_res[0]));
                        out.print(gson.toJson(new Util().message(log_res[1])));
                    }
                }
                else{
                    response.setStatus(404);
                    out.print(gson.toJson(new Util().message("User not found")));
                }
            }
            else{
                response.setStatus(400);
                out.print(gson.toJson(new Util().message("Role should be patient, physician or pharmacist")));
            }
        }
        else{
            response.setStatus(500);
            out.print(gson.toJson(new Util().message("DB not found")));
        }
    }


}
