const mongoose = require('mongoose')
const Schema = mongoose.Schema

const accountSchema = new Schema({

    userID: {
        type: String
    },
    email: {
        type: String,
        require: true,
        unique: true
    },
    password: {
        type: String,
        require: true,
        unique: true
    }

})

module.exports = mongoose.model('Account', accountSchema);