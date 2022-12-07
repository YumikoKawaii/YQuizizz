const mongoose = require('mongoose')
const Schema = mongoose.Schema;

const userSchema = new Schema({
    
    email: {
        type: String,
        require: true,
        unique: true
    },
    username: {
        type: String,
        require: true
    },
    currentExp: {
        type: Number
    },
    currentLevel: {
        type: Number
    }
})

module.exports = mongoose.model('User', userSchema);