package com.servlet_tomcat;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseSingletonMap {
    public static DatabaseSingletonMap instance;
    public Map<String, Patient> patientData;
    public Map<String, Physician> physicianData;
    public Map<String, Pharmacist> pharmacistData;
    public Map<String, PatientPhysicianSchema> patientPhysicianData;
    public Map<String, PatientPharmacistSchema> patientPharmacistData;
    public Map<String, PhysicianConsultationSchema> physicianConsultationData;
    public Map<String, PharmacistProvisionSchema> pharmacistProvisionData;

    private DatabaseSingletonMap(){
        patientData = new LinkedHashMap<>();
        physicianData = new LinkedHashMap<>();
        pharmacistData = new LinkedHashMap<>();
        patientPhysicianData = new LinkedHashMap<>();
        patientPharmacistData = new LinkedHashMap<>();
        physicianConsultationData = new LinkedHashMap<>();
        pharmacistProvisionData = new LinkedHashMap<>();
    }

    public static synchronized DatabaseSingletonMap getInstance(){
        if (instance == null){
            instance = new DatabaseSingletonMap();
        }
        return instance;
    }

    // get list of data
    public Map<String, Patient> getPatientMap() {
        return patientData;
    }

    public Map<String, Physician> getPhysicianMap() {
        return physicianData;
    }

    public Map<String, Pharmacist> getPharmacistMap(){
        return pharmacistData;
    }

    public Map<String, PatientPhysicianSchema> getPatientPhysicianMap(){
        return patientPhysicianData;
    }

    public Map<String, PatientPharmacistSchema> getPatientPharmacistMap(){
        return patientPharmacistData;
    }

    public Map<String, PhysicianConsultationSchema> getPhysicianConsultationData(){
        return physicianConsultationData;
    }

    public Map<String, PharmacistProvisionSchema> getPharmacistProvisionData(){
        return pharmacistProvisionData;
    }


    // get single data
    public Patient getPatient(String userkey){
        return patientData.get(userkey);
    }

    public Physician getPhysician(String userkey){
        return physicianData.get(userkey);
    }

    public Pharmacist getPharmacist(String userkey){
        return pharmacistData.get(userkey);
    }

    public PatientPhysicianSchema getPatientPhysicianData(String userkey){
        return patientPhysicianData.get(userkey);
    }

    public PatientPharmacistSchema getPatientPharmacistData(String userkey){
        return patientPharmacistData.get(userkey);
    }


    // check if key exists
    public boolean checkPatientExist(String userKey){
        return patientData.containsKey(userKey);
    }

    public boolean checkPhysicianExist(String userKey){
        return physicianData.containsKey(userKey);
    }

    public boolean checkPharmacistExist(String userKey){
        return pharmacistData.containsKey(userKey);
    }

    public boolean checkPatientPhysicianExist(String userKey){
        return patientPhysicianData.containsKey(userKey);
    }

    public boolean checkPatientPharmacistExist(String userKey){
        return patientPharmacistData.containsKey(userKey);
    }


    // add a new item
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

    public String[] addPatientPhysician(String patientKey, String physicianKey) {
        if (checkPatientExist(patientKey)!=true){
            return new Util().response("404__Patient does not exist");
        }
        if (checkPhysicianExist(physicianKey)!=true){
            return new Util().response("404__Physician does not exist");
        }
        if (checkPatientPhysicianExist(new Util().createCombinationKey(patientKey, physicianKey))){
            return new Util().response("200__Access already granted");
        }
        patientPhysicianData.put(new Util().createCombinationKey(patientKey, physicianKey), new PatientPhysicianSchema(patientKey, physicianKey));
        return new Util().response("200__Ok");
    }

    public String[] addPatientPharmacist(String patientKey, String pharmacistKey) {
        if (checkPatientExist(patientKey)!=true){
            return new Util().response("404__Patient does not exist");
        }
        if (checkPharmacistExist(pharmacistKey)!=true){
            return new Util().response("404__Pharmacist does not exist");
        }
        if (checkPatientPharmacistExist(new Util().createCombinationKey(patientKey, pharmacistKey))){
            return new Util().response("200__Access already granted");
        }
        patientPharmacistData.put(new Util().createCombinationKey(patientKey, pharmacistKey), new PatientPharmacistSchema(patientKey, pharmacistKey));
        return new Util().response("200__Ok");
    }

    public String[] addPhysicianConsultation(String key, PhysicianConsultationSchema item){
        if (checkPatientExist(item.patientKey) != true){
            return new Util().response("404__Patient does not exist");
        }
        physicianConsultationData.put(key, item);
        return new Util().response("200__Ok");
    }

    public String[] addPharmacistProvision(String key, PharmacistProvisionSchema item){
        if (checkPatientExist(item.patientKey) != true){
            return new Util().response("404__Patient does not exist");
        }
        pharmacistProvisionData.put(key, item);
        return new Util().response("200__Ok");
    }
}
