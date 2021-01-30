const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs')
const config = require('config')
const Role = require('../models').Role
const UserRole = require('../models').UserRole
const randtoken = require('rand-token') 
const RefreshToken = require('../models').RefreshToken
const moment = require('moment')




const login = async (req, user) => {
   //check if password correct
   const validPass = bcrypt.compareSync(req.body.password, user.password)
   if(!validPass) return ({status:false, message:"Username or Password is invalid!"})
   //get user role
   const user_role = await UserRole.findOne({ where : { user_id : user.id}})
   const role = await Role.findOne({ where: { id : user_role.role_id}})
   //create and assing a jwt token
   const token = jwt.sign({exp: Math.floor(Date.now() / 1000) + (60 * 60 * 24 * 30),id: user.id,role:role.role_type},config.get('jwt_secret'))
   //check if user already has refresh token
   const Rtoken = await RefreshToken.findOne({where: { user_id: user.id}})
   let userRfreshToken =''
   if(!Rtoken) {
      const now = moment()
      const createdRToken = await RefreshToken.create({
         user_id: user.id,
         ref_token: randtoken.uid(256) ,
         issued_time: now.format(),
         expired_time: now.add('60', 'days')
      })
      userRfreshToken = createdRToken.ref_token
   } else {
      userRfreshToken = Rtoken.ref_token
   }
 
   return { 
         status: true,
         message: "Successfully logged in ", 
         data: {
            token:token, 
            expires: 3600,
            refresh_token: userRfreshToken,
            id: user.id, 
            user_name:user.user_name, 
            role:role.role_type,
            is_approved: user.is_approved
         }
      }
}
module.exports = login