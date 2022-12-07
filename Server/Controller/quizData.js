const pendingQuiz = require('../Models/pendingQuiz')

module.exports.submitQuizToServer = async(req, res) => {
    try {
        console.log(req.body.data)
        const rawData = JSON.parse(req.body.data)
        const answerList = rawData.answerList.replace("[","").replace("]","").split(",");
        console.log(answerList)

        const newQuiz = new pendingQuiz({category: rawData.category,
            difficulty: rawData.difficulty,
            question: rawData.question,
            answerList: answerList})
        newQuiz.save()
        const respone = 200
        res.send(respone)
    } catch(e) {
        console.log(e)
    }
}