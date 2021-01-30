const express = require("express");
const router = express.Router();
const _ = require("lodash");
const Op = require("sequelize").Op;
const Order = require('../models').Order
const OrderStatus = require('../models').OrderStatus
const { getProductById } = require('../services/productService')
const sequelize = require('sequelize')


//Middlewares
const auth = require('../middleware/auth')
const authorize = require('../middleware/authorize')

//services
const paginate = require('../services/paginate')

//configs
const {
    PERSONALIZE,
    READYMADE
} = require('config')

router.get('/orders', auth, async (req, res) => {
    //check if request is from merchant or user
    if(req.user.role === 'user')
        return res.status(403).send({status: false, message: "Now allowed!"})
    //order filters
    const orderFilter = {
        supplier_id: req.user.id
    }
    if(req.query.type === 'readymade')
        orderFilter.order_type = READYMADE
    if(req.query.type === 'personalize')
        orderFilter.order_type = PERSONALIZE

    const totalOrderByStatus = await Order.findAll({
        attributes: [[sequelize.fn('COUNT','order_status_code'), 'total'],'order_status_code'],
        group:['order_status_code'],
        order:[['order_status_code', 'ASC']],
        include: [{
            model: OrderStatus,
            attributes: ['status']
        }],
        where: orderFilter,
        raw:true
    });
    //collect all counts by status
    const orderData = {}
    orderData.orderCount = {}
    orderData.orders = []
    _.forEach(totalOrderByStatus, function(el) {
        orderData.orderCount[el["OrderStatus.status"]] = el.total
    })

    //order status filters
    const orderStatusFiler = {}
    if(req.query.status) 
        orderStatusFiler.status = req.query.status.toUpperCase()
        
    let  orders = await Order.findAll({
        where: orderFilter,
        include: [{
            model: OrderStatus,
            attributes: ["status"],
            where: orderStatusFiler
        }],
        attributes: [
            "id","order_number", "order_date", "buyer_id",
            "order_detail","product_id", "product_quantity",
            "product_price", "product_discount_per"
        ],
        ...paginate(+req.query.page, +req.query.per_page)
    })
    orders = JSON.parse(JSON.stringify(orders)) 
    for(order of orders) {
      let data = {}
      data.product = {}
      const product = await getProductById(order.product_id, req.headers.authorization)
      if(_.isEmpty(product)) continue
      data.order_id = order.id
      data.order_number = order.order_number
      data.progress_percent = '50%'
      data.hand_over_date = '20th Jan 2020'
      data.order_tags = 'Wedding PartyWear'
      data.amount = order.product_price
      data.dispatched_by = product.deliver_by
      data.product.id = product.product_id
      data.product.quantity = order.product_quantity
      data.product.name = product.product_name
      data.product.description = product.product_description
      data.product.image = product.images[0]
      orderData.orders.push(data)
    }
    return res.status(200).send({ status: true, message: "Feched orders successfully!", data: orderData})
})
router.put('/orders', auth, authorize(["boutique", "designer", "design_house"]),async(req, res) => {
    if(!req.body.status || !req.body.ids) return res.status(422).send({status:false, message: "Please provide ids and status!"})
    const status = (req.body.status).toUpperCase()
    let ids = (req.body.ids).split(',').map(num=>parseInt(num, 10))
    const orderStatusCode = await OrderStatus.findOne({
        where: {
            status: status
        },
        attributes: ["id"]
    })
    if(!orderStatusCode) return res.status(400).send({ status: false, message: "Invalid action!"})
    Order.update({
        order_status_code: orderStatusCode.id
    },{
        where: {
            id:ids,
            supplier_id: req.user.id
        }
    })
    return res.status(200).send({ status: true, message: "Successfully marked order as " + req.body.status, data: []})
})

module.exports = router