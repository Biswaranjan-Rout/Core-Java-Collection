const express = require("express");
const router = express.Router();
const Op = require('sequelize').Op
const _ = require("lodash");
const config = require("config")
const fetch = require('node-fetch')
const moment = require('moment')
//models
const Transaction = require('../models').Transaction
//middlewares
const auth = require('../middleware/auth')
const authorize = require('../middleware/authorize')



router.get('/payments', auth,  
    authorize(['admin','boutique','designer','design_house']),
    async(req, res) => {
        const weeklySell =  Transaction.sum('txn_amount',{
            where: {
                txn_date: {
                    [Op.between] :[moment().startOf('week').format('YYYY-MM-DD'),moment().format('YYYY-MM-DD')]
                }
            },

        })
        const monthlySell =  Transaction.sum('txn_amount',{
            where: {
                txn_date: {
                    [Op.between] :[moment().startOf('month').format('YYYY-MM-DD'),moment().format('YYYY-MM-DD')]
                }
            },

        })
        const todaySell =  Transaction.sum('txn_amount', {
            where : {
                txn_date : moment().format('YYYY-MM-DD')
            }
        })
        const data = await Promise.all([
            todaySell,
            monthlySell,
            weeklySell
        ])
        console.log(data)
        return res.status(200).send({
            status: true,
            message: "Successfully fetched payment data!",
            data : {
                todaySell: data[0],
                weeklySell: data[1],
                monthlySell: data[2]
            }
        })
    })



module.exports = router