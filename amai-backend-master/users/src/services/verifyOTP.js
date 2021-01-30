//models
const UserOTP = require('../models').UserOTP

const fetch = require('node-fetch')
const sgMail = require('@sendgrid/mail')
const SENDGRID_API_KEY = require('config').get("sendgrid_api_key")
const SMS_API_KEY = require('config').get("sms_api_key")


exports.verifyPhoneOTP = async function (phone, session_id, otp) {
    const send = await fetch(`https://2factor.in/API/V1/${SMS_API_KEY}/SMS/VERIFY/${session_id}/${otp}`)
    const result = await send.json()
    return result
}

exports.verifyMailOTP = async function (email) {
    const result = await UserOTP.findOne({where:{email:email}}) 
    const date = new Date()
    if(result && result.expiry < date)
        return {status : false, message: "OTP has been expired!"}
    if(!result || (result.OTP != otp))
        return { status: false, message:"Invalid OTP!"}
    if(result.OTP === otp){
        result.update({is_verified: true})
        return { status: true, message:"OTP verified successfully!", data: result}
    }
}
