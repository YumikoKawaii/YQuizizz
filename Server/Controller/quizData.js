const pendingQuiz = require('../Models/pendingQuiz')

module.exports.submitQuizToServer = async(req, res) => {
    try {
        const rawData = JSON.parse(req.body.data)
        const answerList = rawData.answerList.replace("[","").replace("]","").split(",");

        const newQuiz = new pendingQuiz({category: rawData.category,
            difficulty: rawData.difficulty,
            question: rawData.question,
            answerList: answerList})

        await newQuiz.save()
        const respone = 200
        res.send(respone)
    } catch(e) {
        console.log(e)
        res.send(e);
    }
}