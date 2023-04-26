const express = require("express");
const axios = require("axios");

const router = express.Router();

const ServletRoutes = require("../util/ServletRoutes");
const servletRoutes = new ServletRoutes();
const ResponseFormat = require("../util/ResponseFormat");

router.get("/consultation", async (req, res) => {
    try{
        const urlRoute = await servletRoutes.getFullRoute("PhysicianConsultationServlet");
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

router.post("/consultation", async (req, res) => {
    try{
        const urlRoute = await servletRoutes.getFullRoute("PhysicianConsultationServlet");
        const token = req.header("Authorization");
        const body = req.body;
        const url = urlRoute;
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

module.exports = router;