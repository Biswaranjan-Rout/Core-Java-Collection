const express = require("express")
const router = express.Router()
const moment = require('moment')
const User = require('../models').User
const UserRole = require('../models').UserRole
const Role = require('../models').Role
const Op = require('sequelize').Op
const jwt = require('jsonwebtoken')
const config = require('config')
const RefreshToken = require('../models').RefreshToken
const verify = require('../services/verifyToken')




router.post('/', async(req, res) => {
    const username = req.body.user_name
    const refreshToken = req.body.refresh_token
    const user = await User.findOne({ 
        where: {
            user_name : username
        },
        include:[
            {
                model: RefreshToken,
                where: {
                    user_id:{
                    [Op.ne] : null
                    }
                },
                attributes: ["expired_time","ref_token"]
            }
        ]
    })
    const now = moment()
    if(!user  || now.isAfter(user.RefreshToken.expired_time)|| refreshToken!== user.RefreshToken.ref_token)
        return res.status(401).send({ status: false, message: "Access Denied!"})
    const user_role = await UserRole.findOne({ where : { user_id : user.id}})
    const role = await Role.findOne({ where: { id : user_role.role_id}})
    //create and assing a jwt token
    const token = jwt.sign({exp: Math.floor(Date.now() / 1000) + (60 * 60 * 24 * 30),id: user.id,role:role.role_type},config.get('jwt_secret'))
    res.set("Authorization", "Bearer " + token);
    return res.status(200)
        .send({
            status: true,
            message:"Successfuly refreshed token!",
            token: token,
        })
})

router.delete('/', verify, async(req, res) => {
    RefreshToken.destroy({ where: { user_id : req.user.id}})
    return res.status(200)
        .send({
            status:false,
            message:"Successfully logged out!"
        })
})


module.exports = router