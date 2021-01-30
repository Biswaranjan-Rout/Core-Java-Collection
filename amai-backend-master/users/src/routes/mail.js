const express = require("express")
const router = express.Router()
const sgMail = require('@sendgrid/mail')
const SENDGRID_API_KEY = require('config').get("sendgrid_api_key")

router.get("/otp/ge", async (req, res) => {
  sgMail.setApiKey(process.env.SENDGRID_API_KEY);
  const emailRegexp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
  if(!emailRegexp.test(req.body.email)) return res.status(422).send({ status:false, message:"Please enter a valid email!"})
  console.log(!emailRegexp.test(req.body.email))
  const msg = {
    to: req.body.email,
    from: "amai-web.antino.io",
    subject: "Welcome to AMAI! Confirm Your Email",
    text: " You\'re on your way!Let\'s confirm your email address.",
    html: "<p>You're on your way! Let's confirm your email address.</p>Verification code: <strong>" + Math.floor(100000 + Math.random() * 900000) +"</strong>"
  };
  sgMail.send(msg);
   res.status(200).send({ status: true, message:"Verication code sent to your email!"})
   console.log("Hello");
});

module.exports = router