const { response } = require('express');
const User = require('../Models/user')

module.exports.register = async(req, res) => {
    try {
        const {username, email, password} = req.body;
        const user = new User({email: email, username: username, password: password, currentExp: 0, currentLevel: 1});
        const data = await User.find({email: req.body.email})
        if (data.length == 0) {
            user.save()
            res.send("200")
        } else {
            res.send("406")
        }
    } catch (e){
        res.send("error", e.message)
    }
}

module.exports.login = async(req, res) => {
    try {

        const {email, password} = req.body;
        const data = await User.findOne({email: email})

        if (data == null) {
            const response = {resCode: 1}
            res.send(response)
        } else if (data.password != password) {
            const response = {resCode: 2}
            res.send(response)
        }
        else {
            const response = {resCode: 3, data: data}
            res.send(response)
        }

    } catch (e) {
        console.log("error")
        res.send("error", e.message)
    }
}

module.exports.leaderboardInfo = async(req, res) => {

    try {

        const {email} = req.body

        let user = await User.findOne({email: email})

        const allData = await User.find().sort({currentLevel: -1, currentExp: - 1});

        let i = 0;
        for (i;i < allData.length;i++) {
            if (user.email === allData[i].email)
            {                
                break
            }
        }        

        let data = await User.find({}, {_id: 0,username: 1, currentExp: 1, currentLevel: 1}).sort({currentLevel: -1, currentExp: -1}).limit(20)
        
        
        data = {userRank: i + 1, data: data}
        console.log(data)
        res.send(data)
    } catch(e) {
        console.log(e)
    }

}