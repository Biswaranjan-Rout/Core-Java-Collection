const PD = require('../models').PersonalDetail
const config = require('config')

const fillPersonalDetail = async (req, res) => {
        const pd = PD.findOne({ where: { user_id: req.user.id }})
        var profile_image = typeof req.file === "undefined" ? pd.profile_image : req.file.location
        if (pd) {
            const result = await PD.update({
                user_id: req.user.id,
                first_name: req.body.first_name,
                last_name: req.body.last_name,
                alternate_number: req.body.alternate_number,
                dob: req.body.dob,
                gender: req.body.gender,
                profile_image: profile_image
            }, {returning: true, where : { user_id : req.user.id}})
            if(result) {
                const data = await PD.findOne({ where: { user_id: req.user.id}})
                return data.dataValues
            }
        } else {
            const result = await PD.create({
                user_id: req.user.id,
                first_name: req.body.first_name,
                last_name: req.body.last_name,
                alternate_number: req.body.alternate_number,
                dob: req.body.dob,
                gender: req.body.gender
            })
            return result.dataValues
        }
}
module.exports = fillPersonalDetail