const express = require("express");
const router = express.Router();
const _ = require("lodash");
const op = require("sequelize").Op;
const config = require('config')
const jwt = require('jsonwebtoken')
const sgMail = require('@sendgrid/mail')
const SENDGRID_API_KEY = require('config').get("sendgrid_api_key")
//models
const User = require("../models").User;
const Role = require('../models').Role
const UserRole = require('../models').UserRole
const UserMeasurement = require('../models').UserMeasurement
const PersonalDetail = require('../models').PersonalDetail
//services
const { sendOTPOnPhone, sendOTPOnMail } = require('../services/sendOTP')
//middleware
const authorize = require("../middleware/authorize");
const verify = require("../services/verifyToken");
//Get designers


//............................ RESET PASSWORD .....................................
router.get('/:username/reset-password', async(req, res) => {
  const user = await User.findOne({ where: { user_name: req.params.username}})
  if(!user) return res.status(404).send({ status: false, message: "User not found!"})
  const user_role = await UserRole.findOne({ where : { user_id : user.id}})
  const role = await Role.findOne({ where: { id : user_role.role_id}})
  const token = jwt.sign({exp: Math.floor(Date.now() / 1000) + (60 * 30),username: user.user_name,role:role.role_type},config.get('jwt_secret'))
  sgMail.setApiKey(SENDGRID_API_KEY)
  const msg = {
    from: 'amai-web@antino.io',
    to:'sujeet.rgcsm@gmail.com',
    subject: 'AMAI - Reset Password',
    html: '<h4><b>Reset Password</b></h4>' +
    '<p>Click on below link to reset your password.</p>' +
    '<a href='  + config.get('app_url') + '/users/reset-password/'+ token + '> Reset Your Password</a>' +
    '<br><br>' +
    '<p>--AMAI Team</p>'
    };
    sgMail.send(msg);
    return res.status(200).send({status: true, message: "A password reset link has been sent to your registered email!"})
})

router.get('/reset-password/:token', async(req, res) => {
  const token = req.params.token
  try {
      const verified = jwt.verify(token,config.get('jwt_secret') ) //it return provided sign afte succcessfully verified here user_id
      req.user = verified //making user_id universally accessible
      res.redirect(config.get('web_url') + '/forgot-password?status=true&key='+ token);
  } catch (err) {
    res.redirect(config.get('web_url') + '/forgot-password?status=false');
  }
})

router.put('/reset-password', verify, async(req, res) => {
  if(!req.body.password)
  if (error)
    return res
      .status(400)
      .send({status: false, message: "Please enter new password!"});
  password = req.body.password
  const user = await User.findOne({where: { user_name: req.user.username }})
  if(!user) return res.status(404).send("User not found!")
  const updated = await user.update({password: password})
  return res.status(200).send({status: true, message: "Password has been reset successfully!", data: []})
})
// router.post('/:username/reset-password/generate-otp/:medium', async(req, res) => {
//   const user = await User.findOne({ where: { user_name: req.params.username}})
//   if(!user) return res.status(404).send({ status: false, message: "User not found!"})
//   if(req.params.medium === 'phone') {
//     const phone = user.contact_number
//     const result = await sendOTPOnPhone(phone)
//     if(result.Status === 'Success') 
//       return res.status(200).send({status : true, message: "OTP sent to your phone ending with XXXXXXXX"+ phone.toString().slice(-2), data:result})
//     return res.status(400).send({status:false, message:result.Details})
//   } else {
//     const email = user.email
//     sendOTPOnMail(email)
//     return res.status(200).send({status: true, message: " OTP sent to your email " + email.slice(0,2) + "*******@" + email.split("@")[1]})
//   }
// })

// router.put('/:username/reset-password/verify-otp', async(req, res) => {
//   const user = await User.findOne({ where: { user_name: req.params.username}})
//   if(!user) return res.status(404).send({ status: false, message: "User not found!"})
//   if(req.params.medium === 'phone') {
//     const phone = user.contact_number
//     const result = await sendOTPOnPhone(phone)
//     if(result.Status === 'Success') 
//       return res.status(200).send({status : true, message: "OTP sent to your phone ending with XXXXXXXX"+ phone.toString().slice(-2), data:result})
//     return res.status(400).send({status:false, message:result.Details})
//   } else {
//     const email = user.email
//     sendOTPOnMail(email)
//     return res.status(200).send({status: true, message: " OTP sent to your email " + email.slice(0,2) + "*******@" + email.split("@")[1]})
//   }
// })

router.get('/:id/measurement', verify, async(req, res) => {
  let measurement = await UserMeasurement.findAll({
    limit:1,
    where: { user_id: req.params.id},
    order: [ [ 'updated_at', 'DESC' ]],
    attributes: {exclude: ["created_at","updated_at", "id", "user_id"]},
    raw:true
  })
  const personalDetail = await PersonalDetail.findOne({
    where: {
      user_id: req.params.id
    },
    attributes: ["first_name", "last_name", "gender"],
    raw:true
  })
 measurement = measurement[0]
 measurement.userDetail = {}
 measurement.userDetail.first_name= personalDetail.first_name
 measurement.userDetail.last_name= personalDetail.last_name
 measurement.userDetail.user_gender= personalDetail.gender
  return res.status(200).send({ status: true, message: "Successfully fetched lastest measurement!", data: measurement})
})


module.exports = router;
