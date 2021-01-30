const fetch = require('node-fetch')
//configs
const {
  PRODUCT_SERVICE,
  ORDER_SERVICE,
  PERSONALIZE, READYMADE
} = require("config");



const createOrder = async function (orderData, token) {
  const options = {
    method: "POST",
    body: JSON.stringify(orderData),
    headers: {
      Authorization: token,
      "Content-Type": "application/json"
    }
  }
  const product = await fetch(
    ORDER_SERVICE + "orders/",
    options
  )
  return await product.json()
}

const getOrder = async function (order_id) {
  const options = {
    method: "GET"
  }
  const order = await fetch(
    ORDER_SERVICE + "orders/" + order_id,
    options
  )
  return await order.json()
}

module.exports = {
  createOrder,
  getOrder
}