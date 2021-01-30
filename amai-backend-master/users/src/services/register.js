const db = require('../models')
const User = require('../models').User
const Role = require('../models').Role
const PersonalDetail = require('../models').PersonalDetail
const UserRole = require('../models').UserRole
const { DEFAULT_PROFILE } = require("config")



const register = async (req, res) => {
    if(req.body.role) {
             const role = await Role.findOne({ where: { role_type : req.body.role}})
             //search for role if exist get the id or assign user role
             if(role) {
                var role_id = role.id
             } else {
                 role_id = 1
             }
    } else {
        var role_id = 1
    }
    const transaction = await db.sequelize.transaction()
    try{
        const createdUser =  await User.create({
            user_name: req.body.user_name,
            contact_number: req.body.contact_number,
            email: req.body.email,
            password: req.body.password,
            PersonalDetail: {
                email: req.body.email,
                contact_number: req.body.contact_number,
                profile_image: DEFAULT_PROFILE
            },
            UserRole: {
                role_id: role_id
            }
        }, {
            include: [ PersonalDetail, UserRole ],
            transaction
        })
        await transaction.commit();
        return createdUser.dataValues
    } catch(e) {
        await transaction.rollback();
        throw(e)
    }
        
}
module.exports = register