package com.servlet_tomcat;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PatientDrugsServlet  extends HttpServlet{
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
                    Map<String, PharmacistProvisionSchema> myDrugs = db.getPharmacistProvisionMap();
                    List<Medicine> drugs = new ArrayList<>();
                    for (Map.Entry<String, PharmacistProvisionSchema> entry: myDrugs.entrySet()){
                        String key = entry.getKey();
                        if (token.equals(new Util().splitCombinationKey(key)[1])){
                            drugs.add(new Util().getMedicine(medicines, entry.getValue().medicineName));
                        }
                    }
                    response.setContentType("text/csv");
                    response.setHeader("Content-Disposition","attachment; filename= \"my-drugs-prescription.csv\"");
                    String text = "";
                    for (Medicine med: drugs){
                        text = text + med.med_name+","+med.med_price+","+med.med_expiration+"\n";
                    }
                    out.write(text);
                    out.flush();
                    out.close();
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
