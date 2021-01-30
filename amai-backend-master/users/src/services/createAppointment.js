const Appointment = require('../models').Appointment
const _ = require('lodash')

const createAppointment = async (req, res) => {

    try{
        const result = await Appointment.create({
            user_id: req.user.id,
            supplier_id: req.body.supplier_id,
            product_id: req.body.product_id,
            product_name: req.body.product_name,
            booked_date: _.now(),
            slot:req.body.slot
        })
        return result.dataValues
    } catch (e) {
        console.log(e)
        return res.status(422).send({ error: { status: 422, "message": e.errors[0].message } })
    }
}
module.exports = createAppointment