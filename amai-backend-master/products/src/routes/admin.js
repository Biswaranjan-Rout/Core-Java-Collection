const express = require("express");
const router = express.Router();
const _ = require("lodash");
const Op = require("sequelize").Op;

//models
const Product = require("../models").Product;
const ProductQuantity = require("../models").ProductQuantity;
const ProductPrice = require("../models").ProductPrice;
const ProductImage = require("../models").ProductImage;
const ProductQCStatus = require("../models").ProductQCStatus
//configs
const { QC_PENDING, QC_FAILED, QC_VERIFIED } = require('config')
//middlewares
const verify = require("../services/verifyToken");

//services
const paginate = require("../services/paginate")

//authorization middlewares
const authorize = require("../middleware/authorize");

router.put('/quality', 
    verify, 
    authorize(["admin"]),
    async ( req, res) => {
      if(!req.body.product_id) return res.status(422).send({status:false, message: "Product ID is required!"})
        if(![QC_FAILED, QC_PENDING, QC_VERIFIED].includes(req.body.status)) 
            return res.status(400).send({status:false, message:"Please provide a valid status!"})
        await ProductQCStatus.update({
            status: req.body.status,
            description: req.body.description
        },{
            where: {
                product_id: req.body.product_id
            }
        })
        return res.status(200).send({status:true, message:"Successfully updated product QC status!"})
})

router.get('/quality',
    verify,
    authorize(['admin']),
    async (req, res) => {
        let  product_status = 0
        if(req.query.type === 'qc_pending') {
          product_status = QC_PENDING
        } else if(req.query.type === 'qc_failed') {
          product_status = QC_FAILED
        } else if (req.query.type === 'qc_verified') {
          product_status = QC_VERIFIED
        } else if( req.query.type === 'all'){
          product_status = 'all'
        } else {
            return res.status(400).send({ status: false, message: "Please provide valid type!"})
        }
        const where = product_status !== 'all' ?{status: product_status}:{}
        let products = await ProductQCStatus.findAll({
          where : where,
          include: [
            {
              model: Product,
              attributes:["id","product_name","product_description","category"],
              where: {
                id: {
                  [Op.ne]: null
                }
              },
              include:[
                {
                  model: ProductImage,
                  where: {
                    product_id: {
                      [Op.ne]: null
                    }
                  },
                  attributes:['image']
                },
                {
                  model: ProductQuantity,
                  attributes:["s","m","l","xl"],
                  where: {
                    product_id: {
                      [Op.ne]: null
                    }
                  }
                }
              ]
            }
          ],
          attributes:[["created_at", "pending_since"],"status","supplier_id"],
          ...paginate(+req.query.page, +req.query.per_page)
        })
        products = JSON.parse(JSON.stringify(products))
        result = _.map(products, obj => {
            const data = {}
            data.Product = {}
            data.Product.id = obj.Product.id
            data.Product.name = obj.Product.product_name
            data.Product.image = obj.Product.ProductImages[0].image
            data.Product.description = obj.Product.product_description
            data.Product.category = obj.Product.category
            data.status = obj.status
            data.type_of_request  = ""
            return data 
        })
        return res.status(200).send({ status: true, message: "Successfully fetched " + req.query.type + " products!", data : result})
    })
module.exports = router