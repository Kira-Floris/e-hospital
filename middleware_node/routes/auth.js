const express = require("express");
const axios = require("axios");

const router = express.Router();

const ServletRoutes = require("../util/ServletRoutes");
const servletRoutes = new ServletRoutes();
const ResponseFormat = require("../util/ResponseFormat");

router.post("/register/:role", async (req, res)=>{
    try{
        const urlRoute = await servletRoutes.getFullRoute("UserRegisterServlet");
        const role = req.params.role;
        const url = urlRoute+"/"+role;
        const headers = {"Content-Type": "application/json"};
        const response = await axios.post(url, req.body, headers);
        const data = response.data;
        res.statusCode = response.status;
        res.json(ResponseFormat(data, "Success"));
    } catch (err) {
        res.statusCode = err.response.status;
        res.json(ResponseFormat(err.response.data, "Fail"));
    }
});

router.post("/login/:role", async (req, res) => {
    try{
        const urlRoute = await servletRoutes.getFullRoute("UserLoginServlet");
        const role = req.params.role;
        const url = urlRoute+"/"+role;
        const headers = {"Content-Type": "application/json"};
        const response = await axios.post(url, req.body, headers);
        const data = response.data;
        res.statusCode = response.status;
        res.json(ResponseFormat(data, "Success"));
    } catch (err) {
        res.statusCode = err.response.status;
        res.json(ResponseFormat(err.response.data, "Fail"));
    }
});

module.exports = router;