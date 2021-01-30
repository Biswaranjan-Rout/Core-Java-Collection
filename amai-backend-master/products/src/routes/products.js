const express = require("express");
const router = express.Router();
const _ = require("lodash");
const {
  USER_SERVICE, DEFAULT_SIZE, APPROVED,
  UNAPPROVED, ACTIVE, INACTIVE, OUT_OF_STOCK,
  DELETED, NOT_DELETED, PERSONALIZE, READYMADE} = require("config")
const fetch = require("node-fetch");
const Op = require("sequelize").Op;
//models
const Product = require("../models").Product;
const ProductQuantity = require("../models").ProductQuantity;
const ProductPrice = require("../models").ProductPrice;
const ProductImage = require("../models").ProductImage;
//middlewares
const verifyProduct = require("..//middleware/verifyProduct");
const verify = require("../services/verifyToken");
const upload = require("../middleware/imageUpload");

//validator
const {
  validateProductCreation,
  validateProductQuantity,
  validateProductPrice
} = require("../services/validator");

//services
const createProduct = require("../services/createProduct");
const fillProductQuantity = require("../services/fillProductQuantity");
const fillProductPrice = require("../services/fillProductPrice");
const updateProduct = require("../services/updateProduct");
const addImages = require("../services/addImages");
const paginate = require("../services/paginate");

//authorization middlewares
const authorize = require("../middleware/authorize");
//CREATE PRODUCT
router.post(
  "/",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  async (req, res) => {
    //validate data before creating user
    const { error } = validateProductCreation(req.body);
    if (error)
      return res
        .status(400)
        .send({status: false, message: error.details[0].message });
    const createdProduct = await createProduct(req, res);
    return res.status(201).send({
      status: true,
      message: "successfully fetched all merchant data",
      data: createdProduct
    });
  }
);
router.put(
  "/:id",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  async (req, res) => {
    if(!req.params.id) return res.status(422).send({status:false, message:"Product ID is required!"})
    //Merchant can only approve the product can't unapprove it
    if(req.body.approve) req.body.approve = APPROVED
    const result = await Product.update({
      product_name: req.body.product_name,
      product_description: req.body.product_description,
      product_code: req.body.product_code,
      category: req.body.category,
      material_detail: req.body.material_detail,
      is_deleted:req.body.is_deleted,
      status:req.body.status,
      is_approved:req.body.approve,
      sleeve: req.body.sleeve,
      ideal_for: req.body.ideal_for,
      suitable_for: req.body.suitable_for,
      pattern: req.body.pattern,
      type: req.body.type,
      pack_of: req.body.pack_of,
      occasion:req.body.occasion,
      fabric: req.body.fabric,
      deliver_by: req.body.deliver_by
    },
      {
        where:{supplier_id: req.user.id, id: req.params.id}
    })
    return res.status(200).send({
      status: true,
      message: "Successfully updated product details!",
      data:[]
    });
  }
);

//GET ALL PRODUCTS
router.get("/", verify, async (req, res) => {
  const products = await Product.findAll({
    where: {
      is_deleted: 0
    },
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
  if (products) {
    //parse plain result
    const result = JSON.parse(JSON.stringify(products));
    //get selected designs list
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

    const modifiedProducts = _.map(result, function (obj) {
      const is_selected = selected_designs.includes(obj.id)?true:false
      const is_wishlisted = wishlisted_design.includes(obj.id)?true:false
      return _.extend({is_selected: is_selected, is_wishlisted: is_wishlisted}, obj);
    })
    return res.status(200).send({
      status: true,
      message: "successfully fetched all products",
      data: modifiedProducts
    });
  } else
    return res
      .status(404)
      .send({status: false, message: "Product not found!" });
})

//GET PRODUCT BY ID
router.get("/:id", verify, async (req, res) => {
  let product = await Product.findOne({
    where: { id: req.params.id },
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
      },
      {
        model: ProductQuantity,
        where: {
          product_id: {
            [Op.ne]: null
          }
        },
        attributes:["s","m","l","xl"]
      }
    ]
  });
  if (product) {
    product = JSON.parse(JSON.stringify(product));
    if (product.ProductImages) {
      var images = _.map(product.ProductImages, function (obj) {
        return obj.image;
      });
    }
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
    const response1 = await selectedDesigns.json();
    const selected_designs = _.map(response1.data, "product_id");

    const is_selected = selected_designs.includes(product.id)?true:false
    const is_wishlisted = false

    //get net price
    const price = product.ProductPrice[DEFAULT_SIZE];
    const discount = (product.ProductPrice.discount * price) / 100;
    const net_price = _.ceil(price - discount);

    const result = {
      product_id : product.id,
      product_name: product.product_name,
      product_code: product.product_code,
      description: product.product_description,
      category: product.category,
      material_detail: product.material_detail,
      sleeve: product.sleeve,
      ideal_for: product.ideal_for,
      suitable_for: product.suitable_for,
      pattern: product.pattern,
      type: product.type,
      pack_of: product.pack_of,
      occasion: product.occasion,
      fabric: product.fabric,
      default_size: DEFAULT_SIZE,
      default_price: product.ProductPrice.l,
      discount: discount,
      discount_per: product.ProductPrice.discount,
      net_price: net_price,
      is_wishlisted:is_wishlisted,
      product_type: product.product_type,
      is_selected:is_selected,
      status:product.status,
      deliver_by: product.deliver_by,
      prices: {
        s: product.ProductPrice.s,
        m: product.ProductPrice.m,
        l: product.ProductPrice.l,
        xl: product.ProductPrice.xl
      },
      size_quantity: product.ProductQuantity,
      images: images,
      supplier_id: product.supplier_id
    };
    return res.status(200).send({
      status: true,
      message: "successfully fetched product by id",
      data: result
    });
  } else
    return res
      .status(404)
      .send({status: false, message: "Product not found!" });
});

//DELETE PRODUCT BY ID
router.delete(
  "/:id",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  verifyProduct,
  async (req, res) => {
    const result = await Product.update(
      { is_deleted: 1 },
      { returning: true, where: { supplier_id: req.user.id, id: req.params.id } }
    );
    if (result.length < 0) {
      return res.status(200).send({
        status: true,
        message: "deleted successfully",
        data: result
      });
    } else
      return res
        .status(404)
        .send({status: false, message: "Product doesn't exist!" });
  }
);
//FILL PRODUCT QUANTITY
router.post(
  "/:id/quantity",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  verifyProduct,
  async (req, res) => {
    //validate product quantity data
    const { error } = validateProductQuantity(req.body);
    if (error)
      return res
        .status(400)
        .send({status: false, message: error.details[0].message});
    //check if product quantity already exists
    const productQuantity = await ProductQuantity.findOne({
      where: { product_id: req.params.id }
    });
    if (productQuantity)
      return res.status(400).send({
          status: false,
          message: "Quantity detail already exist for this product."
      });

    const filledProductQuantity = await fillProductQuantity(req, res);
    const result = _.omit(filledProductQuantity, ["id"]);
    return res.status(200).send({
      status: true,
      message: "successfully added product quantity",
      data: result
    });
  }
);
//GET PRODUCT QUANTITY
router.get("/:id/quantity", verify, verifyProduct, async (req, res) => {
  const quantity = await ProductQuantity.findOne({
    where: { id: req.params.id }
  });
  if (quantity) {
    result = _.omit(quantity.dataValues, ["id"]);
    return res.status(200).send({
      status: true,
      message: "successfully fetched product quantity",
      data: result
    });
  } else
    return res.status(404).
      send({status: false, message: "Quantity details does not exist!" });
});
// UPDATE PRODUCT QUANTITY
router.put(
  "/:id/quantity",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  verifyProduct,
  async (req, res) => {
    const result = await ProductQuantity.update(
      { s: req.body.s, m: req.body.m, l: req.body.l, xl: req.body.xl },
      { where: { product_id: req.params.id } }
    );
      return res.status(200).send({
        status: 200,
        message: "Updated Successfully!",
        result: result
      });
});
//FILL PRODUCT PRICE
router.post(
  "/:id/price",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  verifyProduct,
  async (req, res) => {
    //validate product quantity data
    const { error } = validateProductPrice(req.body);
    if (error)
      return res
        .status(400)
        .send({status: false, message: error.details[0].message });
    //check if product quantity already exists
    const productPrice = await ProductPrice.findOne({
      where: { product_id: req.params.id }
    });
    if (productPrice)
      return res.status(400).send({status:false,message: "Price detail already exist for this product."});

    const filledProductPrice = await fillProductPrice(req, res);
    const result = _.omit(filledProductPrice, ["id"]);
    return res.status(201).send({
      status: true,
      message: "successfully updated price of the product",
      data: result
    });
  }
);
//GET PRODUCT PRICE
router.get("/:id/price", verify, verifyProduct, async (req, res) => {
  const price = await ProductQuantity.findOne({
    where: { id: req.params.id }
  });
  if (price) {
    const result = _.omit(price.dataValues, ["id"]);
    return res.status(200).send({
      status: true,
      message: "successfully fetched product price",
      data: result
    });
  } else
    return res.status(404).
    send({status: false, message: "Quantity details does not exist!" });
});
// UPDATE PRODUCT PRICE
router.put(
  "/:id/price",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  verifyProduct,
  async (req, res) => {
    const result = await ProductPrice.update(
      { s: req.body.s, m: req.body.m, l: req.body.l, xl: req.body.xl },
      { where: { product_id: req.params.id } }
    );
      return res
        .status(200)
        .send({status: true, message: "Updated Successfully!"});
  });
//Add images for a a product
router.post(
  "/:id/images",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  verifyProduct,
  upload.array("images", 8),
  async (req, res) => {
    const result = await addImages(req, res);
    return res.status(201).send({
      status: true,
      message: "successfully posted image",
      data: result
    });
  }
);

module.exports = router;
