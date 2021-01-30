const express = require("express");
const router = express.Router();
const _ = require("lodash");
const fetch = require("node-fetch")
const Op = require("sequelize").Op;

//models
const Product = require("../models").Product;
const ProductPrice = require("../models").ProductPrice;
const ProductImage = require("../models").ProductImage;
const ProductQuantity = require("../models").ProductQuantity;
const ProductQCStatus = require("../models").ProductQCStatus;

//config
const {
  USER_SERVICE, DEFAULT_SIZE, APPROVED,
  UNAPPROVED, ACTIVE, INACTIVE, OUT_OF_STOCK,
  DELETED, NOT_DELETED, PERSONALIZE, READYMADE,
  QC_PENDING, QC_VERIFIED, QC_FAILED} = require("config")
//services
const paginate = require("../services/paginate")
//middlewares
const verify = require("../services/verifyToken");
//authorization middlewares
const authorize = require("../middleware/authorize");

//Get products of a particular merchants, merchants must be either designer or boutique or design_house
router.get("/:id/products", verify, async (req, res) => {
  const whereStatement = { supplier_id: req.params.id, status: ACTIVE, is_approved: APPROVED}
  if(req.query.type) {
    if(req.query.type ==='personalize') type = PERSONALIZE
    if(req.query.type === 'readymade') type = READYMADE
    whereStatement.product_type = type
  }
  
  const products = await Product.findAll({
    where: whereStatement,
    include: [
      {
        model: ProductImage,
        where: {
          product_id: {
            [Op.ne]: null
          }
        }
      },
      {
        model: ProductPrice,
        where: {
          product_id: {
            [Op.ne]: null
          }
        }
      }
    ],
    ...paginate(+req.query.page, +req.query.per_page)
  });
  if (products.length > 0) {
    let result = JSON.parse(JSON.stringify(products));
    const options = {
      method: "GET",
      headers: {
        Authorization: req.headers.authorization
      }
    };
    const selectedDesigns = await fetch(
      USER_SERVICE + "me/selected-designs",
      options
    );
    const wishlistedDesigns = await fetch(
      USER_SERVICE + "me/wishlists?type=product",
      options
    );
    const response1 = await selectedDesigns.json();
    const selected_designs = _.map(response1.data, "product_id");

    const response2 = await wishlistedDesigns.json()
    const wishlisted_design = _.map(response2.data, "id")
    result = _.map(products, function(obj) {
      const price = obj.ProductPrice[DEFAULT_SIZE];
      const discount = (obj.ProductPrice.discount * price) / 100;
      const net_price = _.ceil(price - discount);
      const is_selected = selected_designs.includes(obj.id)?true:false
      const is_wishlisted = wishlisted_design.includes(obj.id)?true:false
      return {
        id: obj.id,
        product_name: obj.product_name,
        category: obj.category,
        description: obj.product_description,
        product_code : obj.product_code,
        supplier_id:obj.supplier_id,
        status: obj.status,
        is_selected:is_selected,
        is_wishlisted:is_wishlisted,
        price: price,
        discount: obj.ProductPrice.discount + "%",
        net_price: net_price,
        cover_image: obj.ProductImages[0].image
      };
    })
    return res.status(200).send({
      status: true,
      message: "successfully fetched all all products of a given merchant",
      data: result
    });
  } else
    return res
      .status(404)
      .send({status: false, message: "Product not found!"})
})

router.get('/products/online', verify, authorize(['admin','boutique','designer','design_house']),
  async (req, res) => {
    const filter = {}
    //add neccessary filter for online products
    filter.supplier_id = req.user.id
    filter.is_approved = APPROVED

    //get valid product type
    if(req.query.type)
      filter.product_type = req.query.type === 'readymade'? READYMADE: PERSONALIZE

   //Add product status filter
    if(req.query.status === 'active') {
      filter.status = ACTIVE
    } else if(req.query.status === 'inactive') {
      filter.status = INACTIVE
    } else if (req.query.status === 'out_of_stock') {
      filter.status = OUT_OF_STOCK
    } 

    let products = await Product.findAll({
      where: filter,
      include: [
        {
          model: ProductImage,
          where: {
            product_id: {
              [Op.ne]: null
            }
          },
          attributes:["image"],
          limit:1,
        },
        {
          model: ProductPrice,
          attributes:["l","discount"],
          where: {
            product_id: {
              [Op.ne]: null
            }
          }
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
      ],
      attributes:["id","product_name","product_description","category"],
      ...paginate(+req.query.page, +req.query.per_page)
    })
    products = JSON.parse(JSON.stringify(products))
    result = _.map(products, obj => {
        const data = {}
        data.Product = {}
        data.Product.id = obj.id
        data.Product.name = obj.product_name
        data.Product.image = obj.ProductImages[0].image
        data.Product.description = obj.product_description
        data.Product.category = obj.category
        data.total_quantity = Object.values(obj.ProductQuantity).reduce((a, b) => a + b, 0)
        //get net price
        const price = obj.ProductPrice[DEFAULT_SIZE];
        const discount = (obj.ProductPrice.discount * price) / 100;
        data.min_selling_price = _.ceil(price - discount);
        return data 
    })
    return res.status(200).send({ status: true, message: "Successfully fetched " + req.query.type + " products!", data : result})
  })

  router.get('/products/offline', verify, authorize(['admin','boutique','designer','design_house']),
  async (req, res) => {
    //Create proudct filter
    const ProductFilter = {}
    ProductFilter.is_approved = UNAPPROVED
    if(req.query.type)
    ProductFilter.product_type = req.query.type === 'readymade'? READYMADE: PERSONALIZE

    //Create Produc QC Filter
    const QCfilter = {}
    QCfilter.supplier_id = req.user.id
    
    if(req.query.status === 'qc_pending') {
      QCfilter.status = QC_PENDING
    } else if(req.query.status === 'qc_failed') {
      QCfilter.status = QC_FAILED
    } else if (req.query.status === 'qc_verified') {
      QCfilter.status = QC_VERIFIED
    } 

    let products = await ProductQCStatus.findAll({
      where: QCfilter,
      include: [
        {
          model: Product,
          attributes:["id","product_name","product_description","category"],
          where: {
            id: {
              [Op.ne]: null
            },
           ...ProductFilter
          },
          include:[
            {
              model: ProductImage,
              where: {
                product_id: {
                  [Op.ne]: null
                }
              },
              attributes:["image"]
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
      attributes:[["created_at", "pending_since"],"status","supplier_id","description"],
      ...paginate(+req.query.page, +req.query.per_page)
    })
    products = JSON.parse(JSON.stringify(products))
    result = _.map(products, obj => {
        const data = {}
        data.Product = {}
        data.Product.id = obj.Product.id
        data.Product.name = obj.Product.product_name
        data.Product.image = obj.Product.ProductImages[0].image
        data.Product.description = obj.Product.product_description,
        data.qc_description = obj.description,
        data.Product.category = obj.Product.category
        data.status = obj.status
        data.type_of_request  = ""
        return data 
    })
    return res.status(200).send({ status: true, message: "Successfully fetched " + req.query.type + " products!", data : result})
  })

router.put('/products/verification/retry/:id',verify, 
    authorize(['admin','boutique','designer','design_house'] ),
    async (req, res) => {
      if(!req.params.id) return res.status(422).send({status:false, message:"Please provide product ID!"})
      product = await ProductQCStatus.findOne({ where: { product_id : req.params.id, supplier_id: req.user.id}})
      if(product)
          product.update({status: QC_PENDING})
      return res.status(200).send({status:true, message:"Product submitted for re-valuation!"})
})
module.exports = router;
