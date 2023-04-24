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

public class PharmacistProvisionServlet extends HttpServlet{

    private final Gson gson = new Gson();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();

        String token = request.getHeader("Authorization");
        if (db!=null){
            if(token!=null){
                if(db.checkPharmacistExist(token)){
                    Map<String, PatientPharmacistSchema> APD = db.getPatientPharmacistMap();
                    Map<String, Patient> myPatients = new LinkedHashMap<>();
                    for (Map.Entry<String, PatientPharmacistSchema> entry: APD.entrySet()){
                        String key = entry.getKey();
                        if (key.equals(new Util().createCombinationKey(entry.getValue().patientKey,token))){
                            String patientKey = new Util().splitCombinationKey(key)[0];
                            myPatients.put(patientKey, db.getPatient(patientKey));
                        }
                    }
                    String json = gson.toJson(new Util().patientsToList(myPatients));

                    response.setStatus(200);
                    out.print(json);
                }
                else{
                    response.setStatus(401);
                    out.print(gson.toJson(new Util().message("Unauthorized")));
                }
            }
            else{
                response.setStatus(401);
                out.print(gson.toJson(new Util().message("Unauthorized")));
            }
        }
        else{
            response.setStatus(500);
            out.print(gson.toJson(new Util().message("No DB found")));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();

        String token = request.getHeader("Authorization");

        if (db!=null){
            if (token != null){
                if(db.checkPharmacistExist(token)){
                    PharmacistProvisionSchema item = gson.fromJson(reader, PharmacistProvisionSchema.class);
                    if (db.checkPatientPharmacistExist(new Util().createCombinationKey(item.patientKey, token))){
                        String[] res = db.addPharmacistProvision(new Util().createCombinationKey(token, item.patientKey), item);
                        response.setStatus(Integer.parseInt(res[0]));
                        out.print(gson.toJson(new Util().message(res[1])));
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
