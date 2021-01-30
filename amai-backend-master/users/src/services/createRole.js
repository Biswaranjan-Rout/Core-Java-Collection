const Role = require('../models').Role

const createRole = async (req, res) => {

    try {
        const result = await Role.create({
            role_type: req.body.role_type,
            is_active: req.body.is_active
        })
        return result.dataValues
    } catch (e) {
        console.log(e)
        return res.status(422).send({ error: { status: 422, "message": e.errors[0].message } })
    }
}
module.exports = createRole