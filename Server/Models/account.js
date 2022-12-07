const mongoose = require('mongoose')
const Schema = mongoose.Schema

const accountSchema = new Schema({

    email: {
        type: String,
        require: true,
        unique: true
    },
    password: {
        type: String,
        require: true,
    }

})

module.exports = mongoose.model('Account', accountSchema);