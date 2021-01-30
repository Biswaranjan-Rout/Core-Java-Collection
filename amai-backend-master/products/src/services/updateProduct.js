const Product = require('../models').Product
const updateProduct = async (req, res) => {

    try {
        const result = await Product.update({
            product_name: req.body.product_name,
            product_description: req.body.product_description,
            product_code: req.body.product_code,
            supplier_id: req.user.id,
            category: req.body.category,
            discount: req.body.discount,
        }, { where: { id : req.params.id }})
        return result
    } catch (e) {
        console.log(e)
        return res.status(422).send({ error: { status: 422, "message": e } })
    }
}
module.exports = updateProduct