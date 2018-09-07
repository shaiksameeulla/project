
var http = require("http")
var express = require("express")
var app = express()
var port = process.env.PORT || 5000

// server static pages
app.use(express.static(__dirname + "/www/main/webapp"))

var server = http.createServer(app)
server.listen(port)
console.log("### LISTENING ON PORT " + port + ".");