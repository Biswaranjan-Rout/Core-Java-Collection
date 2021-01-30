const fetch = require('node-fetch')
const {getOrder} = require('./orderService')
const Transaction = require('../models').Transaction



module.exports = async function (data) {
    //fetch buyer_id and supplier_id from order service
    const order = await getOrder(data['ORDERID'])
    if(order.status){
        txnData = {}
        //buyer and supllier data
        txnData.supplier_id = order.data.supplier_id
        txnData.buyer_id = order.data.buyer_id
        //transaction data
        if(data['TXNAMOUNT'] === '' || data['REFUNDAMT'] === '') {
            data['TXNAMOUNT'] = 0
            data['REFUNDAMT'] = 0
        }
        if(data['TXNDATE'] === '') {
            data['TXNDATE'] = new Date()
        }
        txnData.bank_txn_id = data['BANKTXNID']
        txnData.txn_id = data['TXNID']
        txnData.order_number = data['ORDERID']
        txnData.txn_amount = parseFloat(data['TXNAMOUNT'])
        txnData.txn_status = data['STATUS']
        txnData.txn_type = data['TXNTYPE']
        txnData.gateway = data['GATEWAYNAME']
        txnData.res_code = data['RESPCODE']
        txnData.res_msg = data['RESPMSG']
        txnData.bank_name = data['BANKNAME']
        txnData.pay_mode = data['PAYMENTMODE']
        txnData.refund_amt = parseFloat(data['REFUNDAMT'])
        txnData.txn_date = data['TXNDATE']
        const transaction = await Transaction.create(txnData)
        return { status: true, message: "Payment Successfull!", data: transaction}
    } else {
        return { status: false, message: "Payment Failed!"}
    }
}