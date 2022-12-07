const express = require('express')
const mongoose = require('mongoose')
const userRoutes = require('./Routes/user')
const quizDataRoutes = require('./Routes/quizData')

const server = express();
const port = 3000;

mongoose.connect('mongodb://localhost:27017/prototype', {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

const db = mongoose.connection;
db.on("error", console.error.bind(console,"connection error: "))
db.once("open", () => {
    console.log("database connected")
})

server.use(express.urlencoded({extended: true}))

server.get('/', async (req, res) => {
    res.send("Connected!")
    console.log("got get request")
})

server.use('/', userRoutes)

server.use('/', quizDataRoutes)


server.listen(port, () => {
    console.log("Listening at port 3000")
})