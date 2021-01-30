const ProductQuantity = require('../models').ProductQuantity

const fillProductQuantity = async (req, res) => {

    try {
        const filledProductQuantity = await ProductQuantity.create({
            product_id: req.params.id,
            s: req.body.s,
            m: req.body.m,
            l: req.body.l,
            xl: req.body.xl
        })
        return filledProductQuantity.dataValues
    } catch (e) {
        console.log(e)
        return res.status(422).send({ error: { status: 422, "message": e.errors[0].message } })
    }
}
module.exports = fillProductQuantity