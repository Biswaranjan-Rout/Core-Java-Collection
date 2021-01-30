
const Order = require('../models').Order
const moment = require('moment')



const createOrder = async (data) => {
    try {
        const createdOrder =  await Order.create({
            buyer_id: data.buyer_id,
            supplier_id: data.supplier_id,
            order_detail: data.order_detail,
            order_status_code: data.order_status_code,
            order_date: moment(),
            order_type: data.order_type,
            product_id: data.product_id,
            product_size: data.product_size,
            product_quantity: data.product_quantity,
            product_price: data.product_price,
            product_discount_per: data.discount_per
        })
        return createdOrder.dataValues
    } catch(e) {
        throw(e)
    }     
}
module.exports = createOrder