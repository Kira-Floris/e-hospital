const express = require("express");
const axios = require("axios");
const csv = require("csv-parser");
const {Transform} = require("json2csv");
const fs = require("fs");

const router = express.Router();

const ServletRoutes = require("../util/ServletRoutes");
const servletRoutes = new ServletRoutes();
const ResponseFormat = require("../util/ResponseFormat");

router.get("/connection/:role", async (req, res) => {
    try{
        const urlRoute = await servletRoutes.getFullRoute("PatientConnectionServlet");
        const role = req.params.role;
        const token = req.header("Authorization");
        const url = urlRoute+"/"+role;
        const headers = {
            headers: {
                "Authorization": token,
                "Content-Type": "application/json"
            }
        };
        const response = await axios.get(url, headers);
        const data = response.data;
        res.statusCode = response.status;
        res.json(ResponseFormat(data, "Success"));
    } catch (err) {
        res.statusCode = err.response.status;
        res.json(ResponseFormat(err.response.data, "Fail"));
    }
});

router.post("/connection/:role", async (req, res) => {
    try{
        const urlRoute = await servletRoutes.getFullRoute("PatientConnectionServlet");
        const role = req.params.role;
        const token = req.header("Authorization");
        const body = req.body;
        const url = urlRoute+"/"+role;
        const headers = {
            headers: {
                "Authorization": token,
                "Content-Type": "application/json"
            }
        };
        const response = await axios.post(url, body, headers);
        const data = response.data;
        res.statusCode = response.status;
        res.json(ResponseFormat(data, "Success"));
    } catch (err) {
        res.statusCode = err.response.status;
        res.json(ResponseFormat(err.response.data, "Fail"));
    }
});

router.get("/consultation", async (req, res) => {
    try{
        const urlRoute = await servletRoutes.getFullRoute("PatientConsultationServlet");
        const token = req.header("Authorization");
        const url = urlRoute;
        const headers = {
            headers: {
                "Authorization": token,
                "Content-Type": "application/json"
            }
        };
        const response = await axios.get(url, headers);
        const data = response.data;
        res.statusCode = response.status;
        res.json(ResponseFormat(data, "Success"));
    } catch (err) {
        res.statusCode = err.response.status;
        res.json(ResponseFormat(err.response.data, "Fail"));
    }
});

router.get("/drugs", async (req, res) => {
    try{
        
        const fetchData = await servletRoutes.getSubRoute("PatientDrugsServlet");
        const urlRoute = await servletRoutes.getFullRoute("PatientDrugsServlet");
        const fields = fetchData.responseFormat.columns;
        const token = req.header("Authorization");
        const url = urlRoute;
        const headers = {
            headers: {
                "Authorization": token,
                "responseType": "blob"
            }
        };
        const response = await axios.get(url, headers);
        let data = response.data;
        data = fields.toString(",")+
                "\n"+
                data;
        
        res.setHeader("Content-Type", "text/csv");
        res.setHeader("Content-Disposition", "attachment; filename=drugs-prescriptions.csv");

        res.statusCode = response.status;
        res.send(data);
    } catch (err) {
        res.statusCode = 500;
        res.send("Internal server error");  
    }
});

module.exports = router;