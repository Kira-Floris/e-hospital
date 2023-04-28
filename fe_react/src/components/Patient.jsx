import React, {useState, useEffect, useContext} from "react";
import AuthContext from "../context/AuthContext";
import {toast} from "react-toastify";
import {CSVLink} from "react-csv";

let Patient = () =>{
    const [selectedButton, setSelectedButton] = useState("physician");
    const {authTokens} = useContext(AuthContext);

    const [physicians, setPhysicians] = useState([]);
    const [pharmacists, setPharmacists] = useState([]);
    const [consultations, setConsultations] = useState([]);
    const [prescriptions, setPrescriptions] = useState([]);
    const [isPhysicianCheck, setIsPhysicianCheck] = useState(false);
    const [isPharmacistCheck, setIsPharmacistCheck] = useState(false);

    const handleButtonClick = (event) => {
        setSelectedButton(event.target.value);
    };

    const fetchPhysicians = async () =>{
        const url = "http://localhost:5000/api/patient/connection/physicians";
        try{
            const response = await fetch(url,{
                method: "GET",
                headers: {"Content-Type": "application/json", "Authorization":authTokens}}
            )
            let data = await response.json();
            if (response.status===200){
                setPhysicians(data.data);
            }
        } catch(err){
            console.log(err);
        }
    };

    const handlePhysicianConnect = async (e, value) =>{
        
        if (e.target.checked){
            const url = "http://localhost:5000/api/patient/connection/physicians";
            try{
                const response = await fetch(url,{
                    method: "POST",
                    headers: {"Content-Type": "application/json", "Authorization":authTokens},
                    body: JSON.stringify({"key":value})
                    }
                );
                let data = await response.json();
                if (response.status === 200){
                    toast.success(data.data.message);
                }else{
                    
                }
            } catch(err){
                console.log(err);
            }
        }
    };

    const fetchPhysicianConsultation = async () =>{
        const url = "http://localhost:5000/api/patient/consultation";
        try{
            const response = await fetch(url,{
                method: "GET",
                headers: {"Content-Type": "application/json", "Authorization":authTokens}
            });
            let data = await response.json();
            if (response.status ===200){
                setConsultations(data.data);
            }
        }catch(err){
            console.log(err);
        }

    }

    const fetchPharmacists = async () =>{
        const url = "http://localhost:5000/api/patient/connection/pharmacists";
        try{
            const response = await fetch(url,{
                method: "GET",
                headers: {"Content-Type": "application/json", "Authorization":authTokens}}
            )
            let data = await response.json();
            if (response.status===200){
                setPharmacists(data.data);
            }
        } catch(err){
            console.log(err);
        }
    };

    const handlePharmacistConnect = async (e, value) =>{
        
        if (e.target.checked){
            const url = "http://localhost:5000/api/patient/connection/pharmacists";
            try{
                const response = await fetch(url,{
                    method: "POST",
                    headers: {"Content-Type": "application/json", "Authorization":authTokens},
                    body: JSON.stringify({"key":value})
                    }
                );
                let data = await response.json();
                if (response.status === 200){
                    toast.success(data.data.message);
                }else{
                    
                }
            } catch(err){
                console.log(err);
            }
        }
    };

    const fetchPharmacistPrescriptions = async () => {
        const url = "http://localhost:5000/api/patient/drugs";
        try{
            const response = await fetch(url, {
                method:"GET",
                headers:{"Content-Type":"application/json", "Authorization":authTokens}
            });
            let data = await response.json();
            if (response.status === 200){
                setPrescriptions(data.data);
            }
        }catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        fetchPhysicians();
        fetchPharmacists();
        fetchPhysicianConsultation();
        fetchPharmacistPrescriptions();
    }, []);

    return (
        <div className="pt-5">
            <div className="d-flex justify-content-center mb-3 mx-5 p-2">
                <button className="btn btn-primary rounded-0 mx-2" value="physician" onClick={handleButtonClick}>
                Connect with Physicians
                </button>
                <button className="btn btn-primary rounded-0 mx-2" value="pharmacist" onClick={handleButtonClick}>
                Connect with Pharmacists
                </button>
                <button className="btn btn-primary rounded-0 mx-2" value="physicianConsultation" onClick={handleButtonClick}>
                Get Physicians Consultations
                </button>
                <button className="btn btn-primary rounded-0 mx-2" value="pharmacistsPrescriptions" onClick={handleButtonClick}>
                Get Pharmacist Prescriptions
                </button>
            </div>
            <div className="d-flex justify-content-center">
            {selectedButton === "physician" && (
                <div>
                    <h1 className="text-center pb-3">Connect with Physicians</h1>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Age</th>
                                <th scope="col">Genders</th>
                                <th scope="col">Grant Access</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(physicians.length!==0)?physicians.map(function(physician, index){
                                return (
                                    <tr key={index}>
                                        <td>{physician.name}</td>
                                        <td>{physician.email}</td>
                                        <td>{physician.age}</td>
                                        <td>{physician.gender}</td>
                                        <td>
                                            <div className="form-check">
                                                <input
                                                    className="form-check-input"
                                                    type="checkbox" 
                                                    value={physician.userKey}
                                                    onChange = {(e) => handlePhysicianConnect(e, physician.userKey)} 
                                                    id="flexCheckDefault"/>
                                            </div>
                                        </td>
                                    </tr>
                                )
                            }):<span></span>}
                        </tbody>
                    </table>
                </div> 
            )}

            {selectedButton === "pharmacist" && (
                <div>
                    <h1 className="text-center pb-3">Connect with Pharmacists</h1>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">Names</th>
                                <th scope="col">Phone</th>
                                <th scope="col">Age</th>
                                <th scope="col">Genders</th>
                                <th scope="col">Grant Access</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(pharmacists.length!==0)?pharmacists.map(function(pharmacist, index){
                                return (
                                    <tr key={index}>
                                        <td>{pharmacist.name}</td>
                                        <td>{pharmacist.phone}</td>
                                        <td>{pharmacist.age}</td>
                                        <td>{pharmacist.gender}</td>
                                        <td>
                                            <div className="form-check">
                                                <input
                                                    className="form-check-input"
                                                    type="checkbox" 
                                                    value={pharmacist.userKey}
                                                    onChange = {(e) => handlePharmacistConnect(e, pharmacist.userKey)} 
                                                    id="flexCheckDefault"/>
                                            </div>
                                        </td>
                                    </tr>
                                )
                            }):<span></span>}
                        </tbody>
                    </table>
                </div> 
            )}

            {selectedButton === "physicianConsultation" && (
                <div>
                    <h1 className="text-center pb-3">Physician Consultations</h1>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">Consultation</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(consultations.length!==0)?consultations.map(function(consultation, index){
                                return (
                                    <tr key={index}>
                                        <td>{consultation.consult}</td>
                                    </tr>
                                )
                            }):<span></span>}
                        </tbody>
                    </table>
                </div> 
            )}

            {selectedButton === "pharmacistsPrescriptions" && (
                <div>
                    <h1 className="text-center pb-2">Pharmacist Prescription</h1>
                    <div className="text-center pb-2">
                        <CSVLink data={prescriptions} filename={"drugs-prescriptions.csv"}>Download Prescriptions</CSVLink>
                    </div>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">Medicine Name</th>
                                <th scope="col">Medicine Price</th>
                                <th scope="col">Medicine Expiration Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(prescriptions.length!==0)?prescriptions.map(function(prescription, index){
                                return (
                                    <tr key={index}>
                                        <td>{prescription.med_name}</td>
                                        <td>{prescription.med_price} $</td>
                                        <td>{prescription.med_expiration}</td>
                                    </tr>
                                )
                            }):<span></span>}
                        </tbody>
                    </table>
                </div> 
            )}

            </div>

        </div>
    );
}

export default Patient;