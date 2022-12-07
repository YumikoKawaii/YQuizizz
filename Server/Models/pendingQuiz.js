const mongoose = require('mongoose')
const Schema = mongoose.Schema;

const pendingQuizSchema = new Schema({
    
    category: {
        type: String,
        require: true
    }, 
    difficulty: {
        type: String,
        require: true
    },
    question: {
        type: String,
        require: true,
    },
    answerList: [{
        type: String,
        require: true,
    }]
})

module.exports = mongoose.model('PendingQuiz', pendingQuizSchema);