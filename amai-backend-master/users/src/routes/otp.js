const express = require("express");
const router = express.Router();
const fetch = require('node-fetch')
const sgMail = require('@sendgrid/mail')
const SENDGRID_API_KEY = require('config').get("sendgrid_api_key")
const SMS_API_KEY = require('config').get("sms_api_key")

//middlewares
const verify = require('../services/verifyToken')

//models
const UserOTP = require('../models').UserOTP





router.get('/generate', async(req, res)=> {
    let  email = req.query.email
    let phone = req.query.phone
    if(phone){
        phone = +req.query.phone
        if(!(phone && (phone > 6000000000 && phone < 9999999999)))
            return res.status(422).send({status:false, message: "Please enter a valid phone number"}) 
        const send = await fetch(`https://2factor.in/API/V1/${SMS_API_KEY}/SMS/${phone}/AUTOGEN`)
        const result = await send.json()
        if(result) return res.status(200).send({status : true, message: "OTP sent to your phone ending with XXXXXXXX"+ phone.toString().slice(-2), data:result})
    } else if(email){
        email = String(email).toLowerCase()
        sgMail.setApiKey(SENDGRID_API_KEY)
        //validate email
        const emailRegexp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if(!emailRegexp.test(email))
            return res.status(422).send({ status:false, message:"Please enter a valid email!"})
        //generate otp
        const otp = Math.floor(100000 + Math.random() * 900000)
        const msg = {
            to: req.query.email,
            from: "amai-web@antino.io",
            subject: "Welcome to AMAI! Confirm Your Email",
            text: " You\'re on your way!Let\'s confirm your email address.",
            html: "<p>You're on your way! Let's confirm your email address.</p>Verification code: <strong>" + otp +"</strong>"
        };
        //generate current date
        const date = new Date()
        //check if record for email already exists
        let result = await UserOTP.findOne({where:{email: email}})
        if(result && result.expiry > date)  return res.status(200).send({status:true, message:"OTP already sent to your mail!"})
        if(result && result.expiry < date) {
            sgMail.send(msg);
            result.update({OTP:otp, expiry: new Date(date.getTime() + 5 * 60000)})
            return res.status(200).send({ status: true, message:"Verication code sent to your email!"})
        }
        if(!result) {
            sgMail.send(msg);
            UserOTP.create({
            email: email,
            OTP:otp,
            expiry: new Date(date.getTime() + 5 * 60000)
            })
         return res.status(200).send({ status: true, message:"Verication code sent to your email!"})
        }
        return res.status(500).send({status: false, message:"OTP could not be generated!"})
    } else {
        return res.status(422).send({status:false, message:"Please provide email or phone number!"})
    }
})

router.get('/verify', async(req, res) => {
    let  email = req.query.email
    let phone = req.query.phone
    let otp = req.query.otp
    let session_id = req.query.session_id
    if(phone){
        otp = +req.query.otp
        if(!(otp && (otp > 100000 && otp < 999999)))
            return res.status(422).send({status:false, message: "Invalid OTP!"}) 
        const send = await fetch(`https://2factor.in/API/V1/${SMS_API_KEY}/SMS/VERIFY/${session_id}/${otp}`)
        const result = await send.json()
        if(result.Status != 'Error') return res.status(200).send({ status: true, message:"OTP verified successfully!", data: result})
        return res.status(422).send({status:false, message:"Invalid OTP!"})
    } else if(email) {
        otp = +req.query.otp
        if(!(otp ||  (otp > 100000 && otp < 999999)) )
            return res.status(422).send({status:false, message: "Invalid OTP!"})
        const result = await UserOTP.findOne({where:{email:email}}) 
        const date = new Date()
        if(result && result.expiry < date)
            return res.status(200).send({status : false, message: "OTP has been expired!"})
        if(!result || (result.OTP != otp))
            return res.status(200).send({ status: false, message:"Invalid OTP!"})
        if(result.OTP == otp){
            result.update({is_verified: true})
            return res.status(200).send({ status: true, message:"OTP verified successfully!", data: result})
        }
    } else {
        return res.status(422).send({status:false, message:"Please provide email or phone number!"})
    }
})
module.exports = router