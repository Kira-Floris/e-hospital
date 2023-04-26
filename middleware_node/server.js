const express = require("express");
const path = require("path");
const dotenv = require("dotenv");
const cookieParser = require("cookie-parser");

const app = express();
app.use(express.json());
app.use(cookieParser());

// routes
const auth = require("./routes/auth");

// mounting routes
app.use('/auth', auth);

const PORT = 5000;

const server = app.listen(PORT, console.log(`Server running on port ${PORT}`));