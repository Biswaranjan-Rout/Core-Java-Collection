const Product = require('../models').Product
module.exports = async function(req, res, next)
{
    let product_id = req.params.id
    const product = await Product.findOne({ where : { id : product_id}})
    if(!product) return res.status(400).send({status:false,message:'Product does not exist!'})
    next()
}

