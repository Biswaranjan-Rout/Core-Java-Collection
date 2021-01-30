const jwt = require('jsonwebtoken')
const config = require('config')
module.exports = function(req, res, next)
{
    let token = req.header('Authorization') || req.header('authorization')
    if(!token) return res.status(401).send({error:{status:401,message:'Access denied!'}})
    token = token.split(" ")[1];
    try {
        const verified = jwt.verify(token,config.get('jwt_secret') ) //it return provided sign afte succcessfully verified here user_id
        req.user = verified //making user_id universally accessible
        next()//passing request to next
    } catch (err) {
        return res.status(401).send({status: false, name: err.name, message: err.message})
    }
}

