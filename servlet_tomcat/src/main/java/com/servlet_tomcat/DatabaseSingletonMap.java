package com.servlet_tomcat;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseSingletonMap {
    private static DatabaseSingletonMap instance;
    private Map<String, Patient> patientData;
    private Map<String, Physician> physicianData;
    private Map<String, Pharmacist> pharmacistData;

    private DatabaseSingletonMap(){
        patientData = new LinkedHashMap<>();
        physicianData = new LinkedHashMap<>();
        pharmacistData = new LinkedHashMap<>();
    }

    public static synchronized DatabaseSingletonMap getInstance(){
        if (instance == null){
            instance = new DatabaseSingletonMap();
        }
        return instance;
    }

    public Map<String, Patient> getPatientMap() {
        return patientData;
    }

    public Map<String, Physician> getPhysicianMap() {
        return physicianData;
    }

    public Map<String, Pharmacist> getPharmacistMap(){
        return pharmacistData;
    }

    public Patient getPatient(String userkey){
        return patientData.get(userkey);
    }

    public Physician getPhysician(String userkey){
        return physicianData.get(userkey);
    }

    public Pharmacist getPharmacist(String userkey){
        return pharmacistData.get(userkey);
    }

    public boolean checkPatientExist(String userKey){
        return patientData.containsKey(userKey);
    }

    public boolean checkPhysicianExist(String userKey){
        return physicianData.containsKey(userKey);
    }

    public boolean checkPharmacistExist(String userKey){
        return pharmacistData.containsKey(userKey);
    }

    public String createKey(User user, String unique_identifier){
        return user.role + "__" + unique_identifier;
    }

    private String[] response(String status){
        return status.split("__");
    }

    public String[] addPatient(Patient user){
        String[] checkValidation = response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = createKey(user, user.username);
            if (checkPatientExist(userKey)){
                return response("400__unique identifier already exists");
            }
            else{
                patientData.put(createKey(user, user.username), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }

    public String[] addPhysician(Physician user){
        String[] checkValidation = response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = createKey(user, user.email);
            if (checkPhysicianExist(userKey)){
                return response("400__unique identifier already exists");
            }
            else{
                physicianData.put(createKey(user, user.email), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }

    public String[] addPharmacist(Pharmacist user){
        String[] checkValidation = response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = createKey(user, user.phone);
            if (checkPharmacistExist(userKey)){
                return response("400__unique identifier already exists");
            }
            else{
                pharmacistData.put(createKey(user, user.phone), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }
}
