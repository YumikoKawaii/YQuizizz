const User = require('../Models/user')
const Account = require('../Models/account')

module.exports.register = async(req, res) => {
    try {
        const {username, email, password} = req.body;
        const data = await Account.find({email:email})
        if (data.length == 0) {
            const user = new User({email: email, username: username, currentExp: 0, currentLevel: 1});
            await user.save()
            const account = new Account({email: email, password: password})
            await account.save()
            res.send("200")
        } else {
            res.send("406")
        }
    } catch (e){
        console.log(e)
    }
}

module.exports.login = async(req, res) => {
    try {

        const {email, password} = req.body;
        const accountData = await Account.findOne({email: email})

        if (accountData == null) {
            const response = {resCode: 1}
            res.send(response)
        } else if (accountData.password != password) {
            const response = {resCode: 2}
            res.send(response)
        }
        else {
            const userData = await User.findOne({email: email})
            const response = {resCode: 3, data: userData}
            res.send(response)
        }

    } catch (e) {
        console.log("error")
        res.send("error", e.message)
    }
}

module.exports.leaderboardInfo = async(req, res) => {

    try {

        let userRank = 0;
        const {email} = req.body
        if (email != "") {
            
            let user = await User.findOne({email: email})

            const allData = await User.find().sort({currentLevel: -1, currentExp: - 1});

            let i = 0;
            for (i;i < allData.length;i++) {
                if (user.email === allData[i].email)
                {                
                    break
                }
            }            
            userRank = i + 1;
        }
        
        let data = await User.find({}, {_id: 0,username: 1, currentExp: 1, currentLevel: 1}).sort({currentLevel: -1, currentExp: -1}).limit(20)
        
        data = {userRank: userRank, data: data}
        res.send(data)
    } catch(e) {
        console.log(e)
    }

}

module.exports.modifyUserData = async(req, res) => {

    try {
        const {email, username, currentExp, currentLevel} = req.body

        const user = await User.findOne({email: email})
        if (user != null) {
            await User.updateOne({email: email}, {$set : {username: username, currentExp: currentExp, currentLevel: currentLevel}})
        } else {
            res.send("User is not Exists!")
        }

        res.send("Successful!")
    } catch(e) {
        res.send("Request Failed!")
        console.log(e)
    }

}