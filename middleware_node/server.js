const express = require("express");
const path = require("path");
const dotenv = require("dotenv");
const cookieParser = require("cookie-parser");

const app = express();
app.use(express.json());
app.use(cookieParser());

// routes
const auth = require("./routes/auth");
const patient = require("./routes/patient");
const physician = require("./routes/physician");
const pharmacist = require("./routes/pharmacist");

// mounting routes
app.use('/api/auth', auth);
app.use('/api/patient', patient);
app.use('/api/physician', physician);
app.use('/api/pharmacist', pharmacist);

const PORT = 5000;

const server = app.listen(PORT, console.log(`Server running on port ${PORT}`));