const express = require('express')
const router = express.Router()
const history = require('../Controller/history')
const catchAsync = require('../Utils/catchAsync')

router.post('/uploadHistory', catchAsync(history.uploadHistory))

router.post('/getHistoryData', catchAsync(history.getUserHistory))

module.exports = router;