const express = require("express");
const router = express.Router();
const _ = require("lodash");
const config = require("config")
const fetch = require('node-fetch')
//PAYTM SERVICES
const PaytmChecksum =require('../services/checksum')
const {createOrder} =require('../services/orderService')
const createTxnRecord = require('../services/createTxnRecord')
//middlewares
const auth = require('../middleware/auth')

router.post('/generate', auth, async( req, res) => {
    //Create order
    const orderData = req.body
    const order = await createOrder(orderData, req.headers.authorization)
    if(order.status) {
        data = {}
        data['MID'] = config.get('TEST_MERCHANT_ID')
        data['ORDER_ID'] = order.data.order_number,
        data["CUST_ID"] = order.data.buyer_id.toString(),
        data['INDUSTRY_TYPE_ID'] = config.get('INDUSTRY_TYPE')
        data['CHANNEL_ID'] = config.get('CHANNEL_ID_APP')
        data['TXN_AMOUNT'] = "10" // transaction amount
        data['WEBSITE'] = config.get('WEBSITE') //Provided by Paytm
        data['CALLBACK_URL'] = 'https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID='+order.data.order_number;//Provided by Paytm
        data['EMAIL'] = 'abc@gmail.com' // customer email id
        data['MOBILE_NO'] = '7777777777' // customer 10 digit mobile no.
        PaytmChecksum.genchecksum(data, config.get('TEST_MERCHANT_KEY'), function(err, checksum) {
            if(err) return res.status(500).send({status: false, message: "Checksum couldn't be generated!"})
            return res.status(200).send({ 
                status: true, 
                message: "Successfully generated checksum!", 
                data: { 
                checksumGenData : data,
                checksum: checksum,
                orderData : order.data
                }
            })
        })
    } else {
        return res.status(400).send(order)
    }

})

router.post('/verify', async(req, res) => {
    //verify checksome
    data = {}
    data['MID'] = config.get('TEST_MERCHANT_ID')
    data['ORDER_ID'] = req.query.order_id,
    PaytmChecksum.genchecksum(data, config.get('TEST_MERCHANT_KEY'), async function(err, checksum) {
        if(err) return res.status(500).send({status: false, message: "Transaction validation falied!"})
        data['CHECKSUMHASH'] = checksum
        const options = {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
              "Content-Type": "application/json"
            }
          }
        const txnData = await fetch(
           "https://securegw-stage.paytm.in/order/status",
            options
        )
        const  response = await txnData.json()
        //save records
        const txnResponse = await createTxnRecord(response)
        if(response['STATUS'] !== 'TXN_SUCCESS')
            return res.send(400).send({status: false, message: 'Payment Failed'})
        return res.status(200).send(txnResponse)

    })
   
})
module.exports = router