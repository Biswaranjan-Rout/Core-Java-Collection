//models
const UserOTP = require('../models').UserOTP

const fetch = require('node-fetch')
const sgMail = require('@sendgrid/mail')
const SENDGRID_API_KEY = require('config').get("sendgrid_api_key")
const SMS_API_KEY = require('config').get("sms_api_key")


exports.sendOTPOnPhone = async function (phone) {
    const send = await fetch(`https://2factor.in/API/V1/${SMS_API_KEY}/SMS/${phone}/AUTOGEN`)
    const result = await send.json()
    return result
}

exports.sendOTPOnMail = async function (email) {
    //generate otp
    const otp = Math.floor(100000 + Math.random() * 900000)
    sgMail.setApiKey(SENDGRID_API_KEY)
    const msg = {
        to: email,
        from: "amai-web@antino.io",
        subject: "Welcome to AMAI! Confirm Your Email",
        text: " You\'re on your way!Let\'s confirm your email address.",
        html: "<p>You're on your way! Let's confirm your email address.</p>Verification code: <strong>" + otp + "</strong>"
    };
    //generate current date
    const date = new Date()
    //check if record for email already exists
    let result = await UserOTP.findOne({ where: { email: email } })
    //otp not expired
    if (result && result.expiry > date) return res.status(200).send({ status: true, message: "OTP already sent to your mail!" })
    //otp expired, send otp and update
    if (result && result.expiry < date) {
        sgMail.send(msg);
        result.update({ OTP: otp, expiry: new Date(date.getTime() + 5 * 60000) })
        return res.status(200).send({ status: true, message: "Verication code sent to your email!" })
    }
    //if no record, create one with expiry 5 minutes
    if (!result) {
        sgMail.send(msg);
        UserOTP.create({
            email: email,
            OTP: otp,
            expiry: new Date(date.getTime() + 5 * 60000)
        })
        sgMail.send(msg);
        return true
    }
}
