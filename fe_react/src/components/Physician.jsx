import React, {useState, useEffect, useContext} from 'react';
import AuthContext from "../context/AuthContext";
import {toast} from "react-toastify";

const Physician = () => {
    const {authTokens} = useContext(AuthContext);
    const [myPatients, setMyPatients] = useState([]);

    const fetchMyPatients = async () =>{
        const url = "http://localhost:5000/api/physician/consultation";
        try{
            const response = await fetch(url,{
                method: "GET",
                headers: {"Content-Type": "application/json", "Authorization":authTokens}}
            )
            let data = await response.json();
            if (response.status===200){
                setMyPatients(data.data);
            }
        } catch(err){
            console.log(err);
        }
    };

    const consultPatient = async (e) =>{
        e.preventDefault();
        const url = "http://localhost:5000/api/physician/consultation";
        try{
            const response = await fetch(url, {
                method: 'POST',
                headers: {"Content-Type": "application/json", "Authorization":authTokens},
                body: JSON.stringify({"patientKey":e.target.patientKey.value.toString(), "consult":e.target.consult.value.toString()})
            });
            const data = await response.json();
            if (response.status ===200){
                toast.success("Successful");
            }
        }catch(err){
            console.log(err);
        }
    }

    useEffect(()=>{
        fetchMyPatients();
    }, []);

    return (
        <div className="d-flex justify-content-center pt-5">
            <div>
                <h1 className="text-center">My Patients</h1>
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Username</th>
                            <th scope="col">Age</th>
                            <th scope="col">Genders</th>
                            <th scope="col">Consult</th>
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
                                        Consult
                                        </button>
                                        <div className="modal fade" id="exampleModal" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div className="modal-dialog">
                                                <div className="modal-content">
                                                <div className="modal-header">
                                                    <h5 className="modal-title" id="exampleModalLabel">Consult {patient.name}</h5>
                                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div className="modal-body">
                                                    <form onSubmit={(e)=>consultPatient(e)}>
                                                        <div>
                                                            <label className="form-label">Consultation</label>
                                                            <input className="form-control" type="text" name="consult"/>
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
        </div>
    )
}

export default Physician