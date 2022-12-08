const express = require('express')
const router = express.Router()
const users = require('../Controller/user')
const catchAsync = require('../Utils/catchAsync')

router.post('/register', catchAsync(users.register))

router.post('/login', catchAsync(users.login))

router.post('/leaderboardInfo', catchAsync(users.leaderboardInfo))

router.post('/updateUserData', catchAsync(users.modifyUserData))

module.exports = router;