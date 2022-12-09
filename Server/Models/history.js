const mongoose = require('mongoose')
const Schema = mongoose.Schema

const historySchema = new Schema({

    email: {
        type: String,
        require: true
    },
    index: {
        type: String,
        require: true
    },
    topic: {
        type: String,
        require: true
    },
    difficulty: {
        type: String,
        require: true
    },
    totalPoint: {
        type: Number,
        require: true
    },
    numberOfQuiz: {
        type: Number,
        require: true
    },
    numberOfRightAnswer: {
        type: Number,
        require: true
    },
    userPoint: {
        type: Number,
        require: true
    },
    dateAttempt: {
        type: String,
        require: true
    }
    
})

module.exports = mongoose.model('History', historySchema);