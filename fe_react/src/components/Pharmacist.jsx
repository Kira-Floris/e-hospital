import React, {useState, useEffect, useContext} from 'react';
import AuthContext from "../context/AuthContext";
import {toast} from "react-toastify";

const Pharmacist = () => {
    const [selectedButton, setSelectedButton] = useState("drugs");
    const [drugs, setDrugs] = useState([]);
    const [myPatients, setMyPatients] = useState([]);
    const {authTokens} = useContext(AuthContext);

    const handleButtonClick = (event) => {
        setSelectedButton(event.target.value);
    };

    const fetchDrugs = async () =>{
        const url = "http://localhost:5000/api/pharmacist/drugs";
        try{
            const response = await fetch(url,{
                method:"GET",
                headers:{"Authorization":authTokens}
            });
            const data = await response.json();
            if (response.status === 200){
                setDrugs(data.data);
            }
        }catch(err){
            console.log(err);
        }
    };

    const addDrug = async (e) => {
        e.preventDefault();
        const url = "http://localhost:5000/api/pharmacist/drugs";
        try{
            const response = await fetch(url,{
                method:"POST",
                headers:{"Authorization":authTokens, "Content-Type": "application/json"},
                body:JSON.stringify({"med_name":e.target.med_name.value.toString(),"med_price":e.target.med_price.value.toString(),"med_expiration":e.target.med_expiration.value.toString()})
            });
            const data = await response.json();
            if (response.status === 200){
                toast.success("Added");
            }
        }catch(err){
            console.log(err);
        }
    };

    const fetchMyPatients = async () => {
        const url = "http://localhost:5000/api/pharmacist/provision";
        try{
            const response = await fetch(url,{
                method:"GET",
                headers:{"Authorization":authTokens}
            });
            const data = await response.json();
            if (response.status === 200){
                setMyPatients(data.data);
            }
        }catch(err){
            console.log(err);
        }
    }

    const prescribePatient = async (e) =>{
        e.preventDefault();
        const url = "http://localhost:5000/api/pharmacist/provision";
        try{
            const response = await fetch(url,{
                method:"POST",
                headers:{"Authorization":authTokens, "Content-Type": "application/json"},
                body:JSON.stringify({"patientKey":e.target.patientKey.value.toString(), "medicineName":e.target.prescription.value.toString()})
            });
            const data = await response.json();
            if (response.status === 200){
                toast.success("Added");
            }
        }catch(err){
            console.log(err);
        }
    };

    useEffect(()=>{
        fetchDrugs();
        fetchMyPatients();
    });

    return (
        <div className="pt-5">
            <div className="d-flex justify-content-center mb-3 mx-5 p-2">
                <button className="btn btn-primary rounded-0 mx-2" value="drugs" onClick={handleButtonClick}>
                Drugs
                </button>
                <button className="btn btn-primary rounded-0 mx-2" value="prescriptions" onClick={handleButtonClick}>
                Prescriptions
                </button>
            </div>
            <div className="d-flex justify-content-center">
            {selectedButton === "drugs" && (
                <div>
                    <h1 className="text-center pb-3">Drugs</h1>
                    <div className="d-flex justify-content-center">
                        <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        Add New Drug
                        </button>
                    </div>
                    <div className="modal fade" id="exampleModal" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div className="modal-dialog">
                            <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLabel">Add New Drug</h5>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div className="modal-body">
                                <form onSubmit={(e)=>addDrug(e)}>
                                    <div>
                                        <label className="form-label">Medical Name</label>
                                        <input className="form-control" type="text" name="med_name"/>
                                    </div>
                                    <div>
                                        <label className="form-label">Medical Price</label>
                                        <input className="form-control" type="number" name="med_price"/>
                                    </div>
                                    <div>
                                        <label className="form-label">Medical Expiration</label>
                                        <input className="form-control" type="text" name="med_expiration"/>
                                    </div>
                                    <button type="submit" className="btn btn-primary">Submit</button>
                                </form>
                            </div>
                            </div>
                        </div>
                    </div>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">Medication Name</th>
                                <th scope="col">Medication Price</th>
                                <th scope="col">Medication Expiration</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(drugs.length!==0)?drugs.map(function(drug, index){
                                return (
                                    <tr key={index}>
                                        <td>{drug.med_name}</td>
                                        <td>{drug.med_price} $</td>
                                        <td>{drug.med_expiration}</td>   
                                    </tr>
                                )
                            }):<span></span>}
                        </tbody>
                    </table>
                </div> 
            )}

            {selectedButton === "prescriptions" && (
            <div>
                <h1 className="text-center">My Patients</h1>
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Username</th>
                            <th scope="col">Age</th>
                            <th scope="col">Genders</th>
                            <th scope="col">Prescribe</th>
                        </tr>
                    </thead>
                    <tbody>
                        {(myPatients.length!==0)?myPatients.map(function(patient, index){
                            return (
                                <tr key={index}>
                                    <td>{patient.name}</td>
                                    <td>{patient.username}</td>
                                    <td>{patient.age}</td>
                                    <td>{patient.gender}</td>
                                    <td>
                                        <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                        Prescribe Medicine
                                        </button>
                                        <div className="modal fade" id="exampleModal" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div className="modal-dialog">
                                                <div className="modal-content">
                                                <div className="modal-header">
                                                    <h5 className="modal-title" id="exampleModalLabel">Prescribe {patient.name}</h5>
                                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div className="modal-body">
                                                    <form onSubmit={(e)=>prescribePatient(e)}>
                                                        <div>
                                                            <label className="form-label">Prescription</label>
                                                            <input className="form-control" type="text" name="prescription"/>
                                                        </div>
                                                        <input type="hidden" name="patientKey" value={patient.userKey}/>
                                                        <button type="submit" className="btn btn-primary">Submit</button>
                                                    </form>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            )
                        }):<tr></tr>}
                    </tbody>
                </table>
            </div>
            )}

            </div>
        </div>
    )
}

export default Pharmacist