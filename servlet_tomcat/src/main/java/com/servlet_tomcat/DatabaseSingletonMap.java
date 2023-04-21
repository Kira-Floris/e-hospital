package com.servlet_tomcat;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseSingletonMap {
    public static DatabaseSingletonMap instance;
    public Map<String, Patient> patientData;
    public Map<String, Physician> physicianData;
    public Map<String, Pharmacist> pharmacistData;

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

    public String[] addPatient(Patient user){
        String[] checkValidation = new Util().response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = new Util().createKey(user.role, user.username);
            if (checkPatientExist(userKey)){
                return new Util().response("400__unique identifier already exists");
            }
            else{
                patientData.put(new Util().createKey(user.role, user.username), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }

    public String[] addPhysician(Physician user){
        String[] checkValidation = new Util().response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = new Util().createKey(user.role, user.email);
            if (checkPhysicianExist(userKey)){
                return new Util().response("400__unique identifier already exists");
            }
            else{
                physicianData.put(new Util().createKey(user.role, user.email), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }

    public String[] addPharmacist(Pharmacist user){
        String[] checkValidation = new Util().response(user.register());
        String status = checkValidation[0];
        if (status.equals("200")){
            String userKey = new Util().createKey(user.role, user.phone);
            if (checkPharmacistExist(userKey)){
                return new Util().response("400__unique identifier already exists");
            }
            else{
                pharmacistData.put(new Util().createKey(user.role, user.phone), user);
                return checkValidation;
            }
        }    
        else{
            return checkValidation;
        }
    }
}
