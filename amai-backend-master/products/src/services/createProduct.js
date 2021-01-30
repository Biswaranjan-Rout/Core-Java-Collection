const db = require('../models')
const Product = require('../models').Product
const ProductQCStatus = require('../models').ProductQCStatus
const { QC_PENDING } = require('config')
const createProduct = async (req, res) => {
    const transaction = await db.sequelize.transaction()
    try {
        const createdProduct = await Product.create({
            product_name: req.body.product_name,
            product_type:req.body.product_type,
            product_description: req.body.product_description,
            product_code: req.body.product_code,
            supplier_id: req.user.id,
            category: req.body.category,
            material_detail: req.body.material_detail,
            ProductQCStatus:{
                status: QC_PENDING,
                supplier_id: req.user.id,
            }
        }, {
            include: [ ProductQCStatus],
            transaction
        })
        await transaction.commit();
        return createdProduct.dataValues
    } catch (e) {
        await transaction.rollback();
        throw(e)
    }
}
module.exports = createProduct