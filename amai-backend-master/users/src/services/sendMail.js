const sgMail = require('@sendgrid/mail')
const SENDGRID_API_KEY = require('config').get("sendgrid_api_key")



module.exports = function sendMail(to, sbj, body) {
    sgMail.setApiKey(SENDGRID_API_KEY)
    const msg = {
        to: to,
        from: "amai-web@antino.io",
        subject: sbj,
        text: " You\'re on your way!Let\'s confirm your email address.",
        html: "Dear Sir/Ma\'am <br>  &emsp;&emsp;&emsp;" + body + "<br>" + "Thanks,<br>AMAI Team"
    };
    sgMail.send(msg);
    return true
}

