// app dependencies
const express = require("express");
const bodyParser = require("body-parser");
const fs = require('fs');
const path = require('path')
const formidable = require('formidable');
const app = express();

// app setup
const serverPort = 8089;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.set("view engine", "ejs");

//render css files
app.use(express.static("public"));

// The following variables represent the app state.

var latestBody = 0;
var latestBodyTimestamp = 0;

/**
 * render the ejs and display added clients
 */
app.get("/", function (req, res) {
    res.render("index", { body: latestBody, timestamp: latestBodyTimestamp });
});

/**
 * set app to listen on port 'serverPort'
 */
app.listen(serverPort, function () {
    console.log("--- H-IAAC - CST COLLECTOR TESTER ---");
    console.log("server is running on port " + serverPort);
});

/**************************************************/
/** Bellow APIs are related to the web frontend. **/
/**************************************************/
app.get("/getValues", function (req, res) {
    res.json({ body: latestBody, timestamp: latestBodyTimestamp });
});

/**************************************************/
/** Bellow APIs are related to the Android app. ***/
/**************************************************/
app.post("/data", function (req, res) {
    latestBody = JSON.stringify(req.body, null, "\t");
    latestBodyTimestamp = getDateTime();
    res.json({ status: "success" });
});

function getDateTime() {
    var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
    return new Date(Date.now() - tzoffset).toISOString().replace(/T/, ' ').replace(/\..+/, '');
}

