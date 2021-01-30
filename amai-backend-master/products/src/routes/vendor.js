const express = require("express");
const router = express.Router();
const _ = require("lodash");

//models
const Product = require("../models").Product;
const ProductQuantity = require("../models").ProductQuantity;
const ProductPrice = require("../models").ProductPrice;
const ProductImage = require("../models").ProductImage;
//middlewares
const verifyProduct = require("../middleware/verifyProduct");
const verify = require("../services/verifyToken");
const upload = require("../middleware/imageUpload");

//services
const createProduct = require("../services/createProduct");
const fillProductQuantity = require("../services/fillProductQuantity");
const fillProductPrice = require("../services/fillProductPrice");
const updateProduct = require("../services/updateProduct");

//authorization middlewares
const authorize = require("../middleware/authorize");

//Get products of a particular vendor, vendor must be either designer or boutique
router.get("/:id/products", verify, async (req, res) => {
  const products = await Product.findAll({
    where: { supplier_id: req.params.id },
    include: [ProductImage, ProductPrice]
  });
  if (products) {
    let result = JSON.parse(JSON.stringify(products));
    result = _.map(products, function(obj) {
      const price = obj.ProductPrice.l;
      const discount = (obj.discount * price) / 100;
      const net_price = _.ceil(price - discount);
      return {
        id: obj.id,
        product_name: obj.product_name,
        price: price,
        discount: discount + "%",
        net_price: net_price,
        cover_image: obj.ProductImages[0].image
      };
    });
    return res.status(200).send({
      status: true,
      message: "successfully fetched all products of a particular vendor",
      data: result
    });
  } else
    return res
      .status(404)
      .send({ error: { status: 404, message: "Product not found!" } });
});

module.exports = router;
