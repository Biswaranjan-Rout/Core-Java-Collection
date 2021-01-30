const express = require("express");
const router = express.Router();
const _ = require("lodash");
const Op = require("sequelize").Op;
const Order = require('../models').Order
const OrderStatus = require('../models').OrderStatus
const { getProductById } = require('../services/productService')


//Middlewares
const auth = require('../middleware/auth')
const authorize = require('../middleware/authorize')

//services
const { validateOrderCreate } = require('../services/validator')
const createOrder = require('../services/createOrder')

//configs
const {
    PERSONALIZE,
    READYMADE
} = require('config')

router.post('/', auth, async (req, res) => {
    const { error } = validateOrderCreate(req.body)
    if (error)
        return res.status(422).send({ status: false, message: error.details[0].message })
    const product = await getProductById(req.body.product_id, req.headers.authorization)
    if(_.isEmpty(product)) return res.send({ status: false, message: "Product not found!"})
    let order_status = READYMADE
    if(product.product_type === PERSONALIZE) {
         order_status = await OrderStatus.findOne({
            where : {
                status: "PENDING"
            },
            attributes:["id"]
        })
    }
    if(product.product_type === READYMADE) {
        order_status = await OrderStatus.findOne({
            where : {
                status: "RTD"
            },
            attributes:["id"]
        })
    }
    const orderData = {
        buyer_id : req.user.id,
        order_status_code: order_status.id,
        supplier_id: product.supplier_id,
        product_id: req.body.product_id,
        order_detail : req.body.order_detail,
        order_type : product.product_type,
        product_quantity : req.body.product_quantity,
        product_price : product.prices[req.body.product_size],
        discount_per : product.discount
    }
    const order = await createOrder(orderData)
    if (order) return res.status(200).send({status: true, message: "Successfully placed your order!", data: order})
})

router.get('/', auth, async (req, res) => {
    //order filters
    const orderFilter = {}
    //check if request is from merchant or user
    if(req.user.role === 'user')
        orderFilter.buyer_id = req.user.id
    else 
        orderFilter.supplier_id = req.user.id
    if(req.query.order_type) 
         orderFilter.order_type = req.query.order_type

    //order status filters
    const orderStatusFiler = {}
    if(req.query.status) 
        orderStatusFiler.status = req.query.status
   
    let orders = await Order.findAll({
        where: orderFilter,
        include: [{
            model: OrderStatus,
            attributes: ["status"],
            where: orderStatusFiler
        }],
        attributes: [
            "order_number", "order_date", "buyer_id",
            "order_detail","product_id", "product_quantity",
            "product_price", "product_discount_per"
        ]
    })
    orders = JSON.parse(JSON.stringify(orders))
    const orderData = []
    for(order of orders) {
      let data = {}
      const product = await getProductById(order.product_id, req.headers.authorization)
      if(_.isEmpty(product)) continue
      data.order_id = order.id
      data.product_id = order.product_id
      data.product_quantity = order.product_quantity
      data.product_name = product.product_name
      data.order_number = order.order_number
      data.amount = order.product_price
      data.quantity = order.product_quantity
      data.supplier_id = product.supplier_id
      data.deliver_by = product.deliver_by
      data.image = product.images[0]
      orderData.push(data)
    }
    return res.status(200).send({ status: true, message: "Feched orders successfully!", data: orderData})
})
router.put('/', auth, authorize(["boutique", "designer", "design_house"]),async(req, res) => {
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
    return res.status(200).send({ status: true, message: "Successfully marked order as " + req.params.status, data: []})
})
router.get('/:id', async(req, res) => {
    const order = await Order.findOne({
        where: {
            order_number : req.params.id
        }
    })
    return res.status(200).send({ status: true, message: "Successfully fetched order details!", data: order})
})
module.exports = router