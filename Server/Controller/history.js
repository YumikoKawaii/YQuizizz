const Account = require('../Models/account')
const History = require('../Models/history')

module.exports.uploadHistory = async(req, res) => {
    try {

        const {email, index, topic, difficulty, totalPoint, numberOfQuiz, numberOfRightAnswer, userPoint, dateAttempt} = req.body;
        const isUser = await Account.findOne({email: email})
        if (isUser != null) {
            
            const history = new History({email: email, index: index, topic: topic, difficulty: difficulty, totalPoint: totalPoint, numberOfQuiz: numberOfQuiz, numberOfRightAnswer: numberOfRightAnswer, userPoint: userPoint, dateAttempt: dateAttempt})
            await history.save()

        } else {
            res.send("Invalid User!")
        }

        res.send("Successful")
        
    } catch (e) {
        console.log(e)
        res.send("Failed")
    }
}

module.exports.getUserHistory = async(req, res) => {

    try{

        const {email} = req.body;
        const isUser = await Account.find({email: email})
        if (isUser != null) {

            const historyData = await History.find({email: email}, {_id: 0}).sort({index: -1})
            res.send(historyData)
        } else {
            res.send("Invalid User!")
        }


    } catch (e) {
        console.log(e)
        res.send("Failed")
    }

}