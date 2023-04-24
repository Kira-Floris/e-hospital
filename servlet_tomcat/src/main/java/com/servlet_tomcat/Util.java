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

    public boolean checkMedicineExist(List<Medicine> medicines, String medName){
        boolean medExist;
        medExist = false;
        for (Medicine med: medicines){
            if (med.med_name.equals(medName)){
                medExist = true;
            }
        }
        return medExist;
    }

    public Medicine getMedicine(List<Medicine> medicines, String medName){
        Medicine temp = null;
        for (Medicine med: medicines){
            if(med.med_name.equals(medName)){
                temp = new Medicine(med.med_name, med.med_price, med.med_expiration);
            }
        }
        return temp;
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
    public String medicineName;

    PharmacistProvisionSchema(String patientKey, String medicineName){
        this.patientKey = patientKey;
        this.medicineName = medicineName;
    }
}


class Medicine{
    public String med_name;
    public String med_price;
    public String med_expiration;

    Medicine(String med_name, String med_price, String med_expiration){
        this.med_name = med_name;
        this.med_price = med_price;
        this.med_expiration = med_expiration;
    }
}
