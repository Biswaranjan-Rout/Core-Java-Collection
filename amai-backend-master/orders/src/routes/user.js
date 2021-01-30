const express = require("express");
const router = express.Router();
const _ = require("lodash");
const Op = require("sequelize").Op;
const Order = require('../models').Order
const OrderStatus = require('../models').OrderStatus
const { getProductById } = require('../services/productService')

//services
const paginate = require('../services/paginate')

//Middlewares
const auth = require('../middleware/auth')
const authorize = require('../middleware/authorize')

router.get('/orders', auth, authorize(['user']),async (req, res) => {

    //order filters
    const orderFilter = {}
    orderFilter.buyer_id = req.user.id
    if(req.query.order_type) 
    orderFilter.order_type = req.query.order_type

    let orders = await Order.findAll({
        where: orderFilter,
        include: [{
            model: OrderStatus,
            attributes: ["status"]
        }],
        attributes: [
            "order_number", "order_date", "buyer_id",
            "order_detail","product_id", "product_quantity",
            "product_price", "product_discount_per"
        ],
        ...paginate(+req.query.page, +req.query.per_page)
    })
    orders = JSON.parse(JSON.stringify(orders)) 
    const orderData = []
    for(order of orders) {
      let data = {}
      const product = await getProductById(order.product_id, req.headers.authorization)
      if(_.isEmpty(product)) continue
      data.order_number = order.order_number
      data.order_status = order.OrderStatus.status
      data.progress_percent = '50%'
      data.hand_over_date = '20th Jan 2020'
      data.order_tags = 'Wedding PartyWear'
      data.amount = order.product_price
      data.dispatched_by = product.deliver_by
      data.product_id = product.product_id
      data.product_quantity = order.product_quantity
      data.product_name = product.product_name
      data.product_description = product.product_description
      data.product_image = product.images[0]
      orderData.push(data)
    }
    return res.status(200).send({ status: true, message: "Feched orders successfully!", data: orderData})
})

module.exports = router