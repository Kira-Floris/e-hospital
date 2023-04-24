package com.servlet_tomcat;

import java.util.*;

public class Util {
    public String createKey(String role, String unique_identifier){
        return role + "__" + unique_identifier;
    }

    public String createCombinationKey(String key1, String key2){
        return key1 + "___" + key2;
    }

    public String[] splitCombinationKey(String key){
        return key.split("___");
    }

    public String[] response(String status){
        return status.split("__");
    }

    public Message message(String text){
        return new Message(text);
    } 
    class Message{
        public String message;
        Message(String message){
            this.message = message;
        }
    }

    public Token token(String key){
        return new Token(key);
    }

    class Token{
        public String token;
        Token(String key){
            this.token = key;
        }
    }

    public List<Physician> physiciansToList(Map<String, Physician> physicians){
        List<Physician> physiciansList = new ArrayList<>(physicians.values());
        return physiciansList;
    }

    public List<Physician> physiciansSortByName(List<Physician> physicians){
        Collections.sort(physicians, Comparator.comparing(Physician::getName));
        return physicians;
    }

    public List<Pharmacist> pharmacistsToList(Map<String, Pharmacist> pharmacists){
        List<Pharmacist> pharmacistsList = new ArrayList<>(pharmacists.values());
        return pharmacistsList;
    }

    public List<Pharmacist> pharmacistsSortByAge(List<Pharmacist> pharmacists){
        Collections.sort(pharmacists, Comparator.comparing(Pharmacist::getAge));
        return pharmacists;
    }

    public List<Patient> patientsToList(Map<String, Patient> patients){
        List<Patient> patientsList = new ArrayList<>(patients.values());
        return patientsList;
    }

}

class Identifier{
    public String key;
}

class PatientPhysicianSchema{
    public String patientKey;
    public String physicianKey;

    PatientPhysicianSchema(String patientKey, String physicianKey){
        this.patientKey = patientKey;
        this.physicianKey = physicianKey;
    }
}

class PatientPharmacistSchema{
    public String patientKey;
    public String pharmacistKey;

    PatientPharmacistSchema(String patientKey, String pharmacistKey){
        this.patientKey = patientKey;
        this.pharmacistKey = pharmacistKey;
    }

}
class PhysicianConsultationSchema{
    public String patientKey;
    public String consult;

    PhysicianConsultationSchema(String patientKey, String consult){
        this.patientKey = patientKey;
        this.consult = consult;
    }
}

class PharmacistProvisionSchema{
    public String patientKey;
    public String medicine;

    PharmacistProvisionSchema(String patientKey, String medicine){
        this.patientKey = patientKey;
        this.medicine = medicine;
    }
}

class Medicine{
    public String id;
    public String name;
    public String type;
    public String description;
    public String price;

    Medicine(String id, String name, String type, String description, String price){
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
    }
}
