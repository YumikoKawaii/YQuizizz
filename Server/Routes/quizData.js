const express = require('express')
const router = express.Router()
const quizData = require('../Controller/quizData')
const catchAsync = require('../Utils/catchAsync')

router.post('/submitQuizToServer', catchAsync(quizData.submitQuizToServer))

module.exports = router;