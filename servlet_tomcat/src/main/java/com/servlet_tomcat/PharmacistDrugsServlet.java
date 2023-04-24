package com.servlet_tomcat;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.io.FileReader;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PharmacistDrugsServlet extends HttpServlet{
    private final Gson gson = new Gson();
    private String filePath;
    private boolean fileFound;
    private List<Medicine> medicines;

    public void init() {
        filePath = "Medicines.csv";
        medicines = new ArrayList<>();

        try{
            ServletContext context = getServletContext();
            InputStream inputStream = context.getResourceAsStream(filePath);
            if (inputStream != null){
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = br.readLine())!= null){
                    String[] data = line.split(",");

                    fileFound=true;
                    medicines.add(new Medicine(data[0], data[1], data[2]));
                }
                br.close();
            }else{
                fileFound = false;
            }
        }catch(Exception e){
            fileFound = false;
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();

        String token = request.getHeader("Authorization");
        if (db!=null){
            if (token!=null && db.checkPharmacistExist(token)){
                if (fileFound) {
                    response.setStatus(200);
                    out.print(gson.toJson(medicines));
                }
                else{
                    response.setStatus(500);
                    out.print(gson.toJson(new Util().message("Medicine File not found")));
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        BufferedReader reader = request.getReader();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        DatabaseSingletonMap db = DatabaseSingletonMap.getInstance();

        String token = request.getHeader("Authorization");
        boolean addMedOk;
        if (db!=null){
            if (token!=null && db.checkPharmacistExist(token)){
                if (fileFound) {
                    Medicine medicine = gson.fromJson(reader, Medicine.class);
                    medicines.add(medicine);
                    try{
                        FileWriter fw = new FileWriter(getServletContext().getRealPath("/"+filePath), false);
                        for (Medicine med: medicines) {
                            fw.write(med.med_name+","+med.med_price+","+med.med_expiration+ "\n");
                        }
                        addMedOk = true;
                        fw.flush();
                        fw.close();
                    }catch (Exception e){
                        addMedOk = false;
                        e.printStackTrace();
                    }
                    if (addMedOk){
                        response.setStatus(200);
                        out.print(gson.toJson(medicines));
                    }
                    else{
                        response.setStatus(500);
                        out.print(gson.toJson(new Util().message("Failed to register new med")));
                    }
                }
                else{
                    response.setStatus(500);
                    out.print(gson.toJson(new Util().message("Medicine File not found")));
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
