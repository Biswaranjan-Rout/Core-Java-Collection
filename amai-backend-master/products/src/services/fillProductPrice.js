const ProductPrice = require('../models').ProductPrice

const fillProductPrice = async (req, res) => {

    try {
        const filledProductPrice = await ProductPrice.create({
            product_id: req.params.id,
            s: req.body.s,
            m: req.body.m,
            l: req.body.l,
            xl: req.body.xl,
            discount: req.body.discount
        })
        return filledProductPrice.dataValues
    } catch (e) {
        console.log(e)
        return res.status(422).send({ error: { status: 422, "message": e.errors[0].message } })
    }
}
module.exports = fillProductPrice