const express = require("express");
const router = express.Router();
const _ = require("lodash");
const verify = require("../services/verifyToken");
const fetch = require("node-fetch");
const Op = require("sequelize").Op;

//import models
const User = require("../models").User;
const PersonalDetail = require("../models").PersonalDetail;
const BusinessDetail = require("../models").BusinessDetail;
const StoreDetail = require("../models").StoreDetail;
const BankDetail = require("../models").BankDetail;
const Appointment = require("../models").Appointment;
const WishList = require("../models").WishList;
const CheckedOutDesign = require("../models").CheckedOutDesign;
const UserSelectedDesign = require("../models").UserSelectedDesign;
const UserRole = require("../models").UserRole;
const Role = require("../models").Role
const UserMeasurement = require("../models").UserMeasurement
const UserCart = require('../models').UserCart

//import services
const fillBusinessDetail = require("../services/fillBusinessDetail");
const fillPersonalDetail = require("../services/fillPersonalDetail");
const fillStoreDetail = require("../services/fillStoreDetail");
const fillBankDetail = require("../services/fillBankDetail");
const fillWishListDetail = require("../services/fillWishListDetail");
const insertUserMeasurement = require("../services/insertUserMeasurement")
const paginate = require("../services/paginate");
const {
  validatePersonalDetail,
  validateBusinessDetail,
  validateStoreDetail,
  validateBankDetail,
  validateWishList,
  validateCheckedOutDesign,
  validateAppointmentCreation,
  validateMeasurement,
  validateCartDetail
} = require("../services/validator");
const { getProductById } = require('../services/productService')

//authorization middlewares
const authorize = require("../middleware/authorize");
const upload = require("../middleware/imageUpload");

//configs
const { BOOKED, RESCHEDULED, CANCELLED, COMPLETED, PRODUCT_SERVICE, DEFAULT_PROFILE,
  PERSONALIZE, READYMADE } = require("config");


//................................. GET USER DETAILS .............................

router.get("/", verify, async (req, res) => {
  try {
    //get user id from req.user that was set when token verified
    const user_id = req.user.id;
    //check if user  exisits
    const user = await User.findOne({
      where: { id: user_id },
      include: [
        {
          model: PersonalDetail,
          attributes: { exclude: ["id", "user_id"] }
        },
        {
          model: BankDetail,
          attributes: { exclude: ["id", "userId", "UserId"] }
        },
        {
          model: BusinessDetail,
          attributes: { exclude: ["id", "userId", "UserId"] }
        },
        {
          model: StoreDetail,
          attributes: { exclude: ["id", "userId", "UserId"] }
        }
      ],
      attributes: { exclude: ["password", "createdAt", "updatedAt"] }
    });
    if (!user)
      return res.status(400).send({
        error: { status: 400, message: "User not found. Please login." }
      });
    return res.status(200).send({
      status: true,
      message: "successfully fetched users details",
      result: user
    });
  } catch (err) {
    return res
      .status(500)
      .send({ status: false, message: "Something went wrong." });
  }
});

router.get("/personal-detail", verify, async (req, res) => {
  const user = await User.findOne({ where: { id: req.user.id } });
  if (!user) {
    return res.status(400).send({
      error: { status: 400, message: "User not found. Pleae register." }
    });
  } else {
    const personal_detail = await PersonalDetail.findOne({
      where: { user_id: req.user.id }
    });
    if (personal_detail) {
      return res.status(200).send({
        status: true,
        message: "successfully fetched users personal details",
        result: personal_detail
      });
    } else {
      return res.status(404).send({
        error: {
          status: 404,
          message: "Personal detail does not exist this user!"
        }
      });
    }
  }
});

//.................................... FILL USER DETAILS ..................................

router.post(
  "/personal-detail",
  verify,
  upload.single("image"),
  async (req, res) => {
    //validate data before creating user
    const { error } = validatePersonalDetail(req.body);
    if (error)
      return res
        .status(400)
        .send({ status: false, message: error.details[0].message });
    //create user
    try {
      const user = await User.findOne({ where: { id: req.user.id } });
      if (!user) {
        return res.status(400).send({ status: false, message: "User not found. Pleae register." });
      } else {
        const personal_detail = await fillPersonalDetail(req, res);
        const result = _.omit(personal_detail, ["id"]);
        return res.status(200).send({
          status: true,
          message: "successfully added personal details",
          data: result
        });
      }
    } catch (err) {
      console.log(err);
      return res
        .status(500)
        .send({ status: false, message: "Something went wrong." });
    }
  }
);
router.post(
  "/business-detail",
  verify,
  authorize(["boutique", "designer", "design_house"]),
  async (req, res) => {
    //validate data before creating user
    const { error } = validateBusinessDetail(req.body);
    if (error)
      return res
        .status(400)
        .send({ status: false, message: error.details[0].message });
    //create user
    try {
      const user = await User.findOne({ where: { id: req.user.id } });
      if (!user) {
        return res.status(400).send({
          error: { status: 400, message: "User not found. Pleae register." }
        });
      } else {
        const business_detail = await BusinessDetail.findOne({
          where: { user_id: req.user.id }
        });
        if (business_detail)
          return res.status(400).send({
            status: false,
            message: "Business detail already exist for this user."
          });
      }
      const business_detail = await fillBusinessDetail(req, res);
      const result = _.omit(business_detail, ["id"]);
      return res.status(201).send({
        status: true,
        message: "successfully added business details",
        data: result
      });
    } catch (err) {
      console.log(err);
      return res
        .status(500)
        .send({ status: false, message: "Something went wrong." });
    }
  }
);
router.post(
  "/store-detail",
  verify,
  upload.fields([{ name: 'image', maxCount: 1 }, { name: 'document', maxCount: 1 }]),
  authorize(["boutique", "designer", "design_house"]),
  async (req, res) => {
    //validate data before creating user
    const { error } = validateStoreDetail(req.body);
    if (error)
      return res
        .status(400)
        .send({ status: false, message: error.details[0].message });
    //create user
    try {
      const user = await User.findOne({ where: { id: req.user.id } });
      if (!user) {
        return res.status(400).send(
          { status: false, message: "User not found. Pleae register." });
      } else {
        const store_detail = await StoreDetail.findOne({
          where: { user_id: req.user.id }
        });
        if (store_detail)
          return res.status(400).send({
            status: false,
            message: "Store details already exist for this user."
          });
      }
      const store_detail = await fillStoreDetail(req, res);
      const result = _.omit(store_detail, ["createdAt", "updatedAt", "id"]);
      return res.status(200).send({
        status: true,
        message: "successfully added store details",
        data: result
      });
    } catch (err) {
      console.log(err);
      return res
        .status(500)
        .send({ status: false, message: "Something went wrong." });
    }
  }
);
router.post(
  "/bank-detail",
  verify,
  authorize(["user", "boutique", "designer", "design_house"]),
  async (req, res) => {
    //validate data before creating user
    const { error } = validateBankDetail(req.body);
    if (error)
      return res
        .status(400)
        .send({ status: false, message: error.details[0].message });
    //create user
    const user = await User.findOne({ where: { id: req.user.id } });
    if (!user) {
      return res.status(400).send(
        { status: false, message: "User not found. Pleae register." });
    } else {
      const bank_detail = await BankDetail.findOne({
        where: { user_id: req.user.id }
      });
      if (bank_detail)
        return res.status(400).send({
          status: false,
          message: "Bank detail already exist for this user."
        });
    }
    const bank_detail = await fillBankDetail(req, res);
    const result = _.omit(bank_detail, ["createdAt", "updatedAt", "id"]);
    return res.status(200).send({
      status: true,
      message: "successfully fetched inserted bank details",
      data: result
    });
  }
);

//.................................... REGISTRATION STATUS ...............................

//get user registration status
router.get("/registration-status", verify, async (req, res) => {
  //check if user  exisits
  const user = await User.findOne({
    where: { id: req.user.id },
    include: [
      {
        model: PersonalDetail,
        attributes: ["id"]
      },
      {
        model: BankDetail,
        attributes: ["id"]
      },
      {
        model: BusinessDetail,
        attributes: ["id"]
      },
      {
        model: StoreDetail,
        attributes: ["id"]
      }
    ],
    attributes: ["id", "created_at"]
  });
  if (!user)
    return res.status(400).send(
      { status: 400, message: "User not found. Please login." });
  let status = 0; //initial status
  if (user.PersonalDetail) status += 25;
  if (user.BankDetail) status += 25;
  if (user.BusinessDetail) status += 25;
  if (user.StoreDetail) status += 25;
  return res.status(200).send({
    status: true,
    message: "successfully fetched users details",
    data: {
      "registeration-status": status + "%",
      "registration-date": user.created_at
    }
  })
})

//........................................ USER WISHLISTS .......................................

// GET user wishlist
router.get("/wishlists", verify, async (req, res) => {
  const type = req.query.type
  let product_type = req.query.product_type
  if (product_type === 'personalize') product_type = PERSONALIZE
  else if (product_type === 'readymade') product_type = READYMADE
  //validate type
  if (!["boutique", "designer", "design_house", "product", "all"].includes(type))
    return res.status(400).send({ status: false, message: "Pleae provide valid type!" })
  let usersWishList = await WishList.findAll({
    where: { user_id: req.user.id, item_type: type },
    ...paginate(+req.query.page, +req.query.per_page)
  })
  usersWishList = JSON.parse(JSON.stringify(usersWishList))
  if (!usersWishList) {
    return res.status(404).send({
      status: false,
      message: "No items in wishlist"
    })
  }
  if (type == "product") {
    let result = [];
    const options = {
      method: "GET",
      headers: {
        Authorization: req.headers.authorization
      }
    }
    for (let product of usersWishList) {
      const productDetails = await fetch(
        PRODUCT_SERVICE + "products/" + product.item_id,
        options
      );
      const response = await productDetails.json();
      if (response.data.product_type === product_type) {
        result.push({
          id: product.item_id,
          name: response.data.product_name,
          supplier_id: response.data.supplier_id,
          image: response.data.images[0]
        });
      }
      if (!req.query.product_type) {
        result.push({
          id: product.item_id,
          name: response.data.product_name,
          supplier_id: response.data.supplier_id,
          image: response.data.images[0]
        });
      }
    }
    return res
      .status(200)
      .send({ status: true, message: "wishlist data fetched", data: result });
  } else if (type === "all") {
    let usersWishList = await WishList.findAll({
      where: { user_id: req.user.id },
      ...paginate(+req.query.page, +req.query.per_page)
    })
    usersWishList = JSON.parse(JSON.stringify(usersWishList))
    let result = [];
    for (obj of usersWishList) {
      if (obj.item_type === "product") {
        let options = {
          method: "GET",
          headers: {
            Authorization: req.headers.authorization
          }
        }
        let productDetails = await fetch(
          PRODUCT_SERVICE + "products/" + obj.item_id,
          options
        );
        let response = await productDetails.json();
        result.push({
          id: obj.item_id,
          name: response.data.product_name,
          type: obj.item_type,
          supplier_id: response.data.supplier_id,
          image: response.data.images[0]
        });
      } else {
        let data = await User.findOne({
          where: { id: obj.item_id },
          include: [
            {
              model: UserRole,
              where: {
                user_id: {
                  [Op.ne]: null
                }
              },
              include: {
                model: Role,
                where: { role_type: obj.item_type }
              }
            },
            {
              model: StoreDetail,
              where: {
                user_id: {
                  [Op.ne]: null
                }
              },
              attributes: ["store_image"]
            },
            {
              model: BusinessDetail,
              where: {
                user_id: {
                  [Op.ne]: null
                }
              },
              attributes: ["company_name"]
            }],
          attributes: ["id"]
        },
        )
        if (!data) continue
        result.push({
          id: data.id,
          name: data.BusinessDetail.company_name,
          type: obj.item_type,
          image: data.StoreDetail.store_image,
        })
      }
    }
    return res.status(200).send({ status: true, message: "Fetched wishlists successfully", data: result })
  } else {
    let result = []
    for (let boutique of usersWishList) {
      const data = await User.findOne({
        where: { id: boutique.item_id },
        include: [
          {
            model: UserRole,
            where: {
              user_id: {
                [Op.ne]: null
              }
            },
            include: {
              model: Role,
              where: { role_type: type }
            }
          },
          {
            model: StoreDetail,
            where: {
              user_id: {
                [Op.ne]: null
              }
            },
            attributes: ["store_image"]
          },
          {
            model: BusinessDetail,
            where: {
              user_id: {
                [Op.ne]: null
              }
            },
            attributes: ["company_name"]
          }],
        attributes: ["id"]
      },
      )
      if (data) {
        result.push({
          id: data.id,
          name: data.BusinessDetail.company_name,
          image: data.StoreDetail.store_image
        })
      }
    }
    return res.status(200).send({ status: true, message: "Fetched wishlists successfully", data: result })
  }
})
//ADD DATA INTO WISHLIST
router.post("/wishlists", verify, authorize(["user"]), async (req, res) => {
  //check if product already exist in wishlist
  //product here referes to all the items be it boutique, designer,design-house
  const { error } = validateWishList(req.body);
  if (error)
    return res
      .status(400)
      .send({ error: { status: 400, message: error.details[0].message } });

  const product = await WishList.findOne({
    where: { user_id: req.user.id, item_id: req.body.item_id, item_type: req.body.item_type }
  });
  if (product) {
    return res
      .status(200)
      .send({ status: true, message: "already added to wishlist" });
  } else {
    const wish_list = await fillWishListDetail(req, res);
    if (req.body.type == 'product') {
      CheckedOutDesign.destroy({ where: { user_id: req.user.id, product_id: req.query.id } })
    }
    return res.status(200).send({
      status: true,
      message: "successfully added into wishlist",
      data: wish_list
    });
  }
});
//DELETE wishlist
router.delete('/wishlists', verify, async (req, res) => {
  if (!req.query.type || !req.query.id) return res.status(400).send({ status: false, message: "Please provide Item type and Item id" })
  const deletedWishlist = await WishList.destroy({ where: { user_id: req.user.id, item_type: req.query.type, item_id: req.query.id } })
  if (!deletedWishlist) return res.status(404).send({ status: false, message: "Item doesn't not exist!" })
  return res.status(200).send({ status: true, message: "Selected designs have been removed!", data: [] })
})

//......................................... USER SELECTED DESIGNS ...................................

//ADD SELECTED DESIGNS
router.post('/selected-designs', verify, async (req, res) => {
  if (!req.body.product_id) { return res.status(400).send({ status: false, message: "product_id is required!" }) }
  let design = await UserSelectedDesign.findOne({ where: { product_id: req.body.product_id, user_id: req.user.id } })
  if (design) return res.status(400).send({ status: false, message: "Product already selected!" })
  design = await UserSelectedDesign.findAll({ wehre: { user_id: req.user.id } })
  const addDesign = await UserSelectedDesign.create({ user_id: req.user.id, product_id: req.body.product_id })
  return res.status(201).send({ status: true, message: "Successfully added to my-designs!", data: addDesign })
})
//GET SELECTED DESIGNS
router.get('/selected-designs', verify, async (req, res) => {
  const designs = await UserSelectedDesign.findAll({ where: { user_id: req.user.id } })
  if (designs) return res.status(200).send({ status: true, message: "Successfully fetched my designs!", data: designs })
  return res.status(404).send({ status: true, message: "No design selected!" })
})
//DELETE USER SELECTED DESIGNS
router.delete('/selected-designs', verify, async (req, res) => {
  const deletedDesigns = await UserSelectedDesign.destroy({ where: { user_id: req.user.id } })
  return res.status(200).send({ status: true, message: "Selected designs have been removed!", data: {} })
})

//DELETE SINGLE SELECTED DESIGNS
router.delete('/selected-designs/:id', verify, async (req, res) => {
  const deletedDesign = await UserSelectedDesign.destroy({ where: { user_id: req.user.id, product_id: req.params.id } })
  if (deletedDesign) return res.status(200).send({ status: true, message: "Design has been removed!", data: [] })
  return res.status(404).send({ status: false, message: "Design does not exist!" })
})

//...................................... USER DESIGN BASKET ................................
 router.get('/design-basket/items', verify, async(req, res) => {
  let  designs = await UserSelectedDesign.findAll({ where: { user_id: req.user.id } })
  designs = JSON.parse(JSON.stringify(designs))
  console.log(designs)
  const data  = []
  for( design of designs) {
    pData = {}
    const product = await getProductById(design.product_id, req.headers.authorization)
    if(_.isEmpty(product)) continue
    pData.product_id = product.id,
    pData.supplier_id = product.supplier_id,
    pData.image = product.images[0],
    pData.product_name = product.product_name
    pData.product_description = product.description
    pData.product_code = product.product_code
    pData.category = product.category
    data.push(pData)
  }
  if (designs) return res.status(200).send({ status: true, message: "Successfully fetched my designs!", data: data })
 })
 router.delete('/design-basket/items/:id', verify, async (req, res) => {
  const deletedDesign = await UserSelectedDesign.destroy({ where: { user_id: req.user.id, product_id: req.params.id } })
  if (deletedDesign) return res.status(200).send({ status: true, message: "Design has been removed!", data: [] })
  return res.status(404).send({ status: false, message: "Design does not exist!" })
})

//...................................... USER CHECKEDOUT DESIGNS ................................

//ADD TO CHECKEDOUTDESIGNS
router.post('/checkedout-designs', verify, async (req, res) => {
  const { error } = validateCheckedOutDesign(req.body);
  if (error)
    return res
      .status(400)
      .send({ status: false, message: error.details[0].message });
  //check if product already exists
  const foundDesign = await CheckedOutDesign.findOne({
    where: { user_id: req.user.id, product_id: req.body.product_id }
  });
  if (foundDesign) {
    //update quantity if product already exists
    const currentProductQuantity = foundDesign.qty
    const updateCheckedOutDesign = await foundDesign.update({
      qty: currentProductQuantity + 1
    })
    const designApn = await Appointment.findOne({ where: { user_id: req.user.id, apn_id: req.body.apn_id } })
    if (designApn) {
      let products = designApn.product_id.split(",")
      products.splice(products.indexOf(req.body.product_id.toString()), 1);
      products = products.toString()
      if (products.length < 1) designApn.update({ product_id: products, status: COMPLETED })
      else designApn.update({ product_id: products })
    }
    return res.status(200).send({
      status: true,
      message: "Quantity updated!",
      data: {}
    });
  } else {
    //add product if doesn't exist
    const addProductToCheckedOutDesigns = await CheckedOutDesign.create({
      user_id: req.user.id,
      product_id: req.body.product_id,
      qty: req.body.qty
    })
    const designApn = await Appointment.findOne({ where: { user_id: req.user.id, apn_id: req.body.apn_id } })
    if (designApn) {
      WishList.destroy({ where: { user_id: req.user.id, item_type: "product", item_id: req.body.product_id } })
      let products = designApn.product_id.split(",")
      products.splice(products.indexOf(req.body.product_id.toString()), 1);
      products = products.toString()
      if (products.length < 1) designApn.update({ product_id: products, status: COMPLETED })
      else designApn.update({ product_id: products })
    }
    return res.status(200).send({
      status: true,
      message: "Product added to checkout desins!",
      data: addProductToCheckedOutDesigns
    });
  }
})
//GET checked out designs
router.get('/checkedout-designs', verify, async (req, res) => {
  const checkedOutDesigns = await CheckedOutDesign.findAll({
    where: { user_id: req.user.id },
    order: [["updated_at", "DESC"]]
  })
  if (!checkedOutDesigns) return res.status(404).send({ status: false, "message": "No checkout designs!" })
  const data = []
  var total_price = 0
  var total_discount = 0
  var options = {
    method: "GET",
    headers: {
      Authorization: req.headers.authorization
    }
  }
  for (let obj of checkedOutDesigns) {
    const productDetails = await fetch(
      PRODUCT_SERVICE + "products/" + obj.product_id,
      options
    )
    const response = await productDetails.json();
    total_price += response.data.default_price
    total_discount += response.data.discount
    data.push({
      product_id: response.data.product_id,
      product_name: response.data.product_name,
      supplier_id: response.data.supplier_id,
      product_image: response.data.images[0],
      price: response.data.default_price,
      net_price: response.data.net_price,
      discount: response.data.discount,
    });
  }
  return res.status(200).send({ status: true, message: "Fetched all checked out products", total_discount: total_discount, total_price: total_price, data: data })
})

//delete from checked out design
router.delete('/checkedout-designs/:id', verify, async (req, res) => {
  if (!req.params.id) return res.status(400).send({ status: false, message: "Please provide product id!" })
  const deletedCheckedoutItem = await CheckedOutDesign.destroy({ where: { user_id: req.user.id, product_id: req.params.id } })
  if (!deletedCheckedoutItem) return res.status(404).send({ status: false, message: "Product doesn't not exist!" })
  return res.status(200).send({ status: true, message: "Product has been removed from checkedout designs!", data: [] })
})
//my designs for booked appointments
router.get('/designs', verify, async (req, res) => {
  //create filter
  const filter = {}

  if (req.query.status === 'booked' || req.query.status === 'rescheduled')
    filter.status = {
      [Op.or]: [BOOKED, RESCHEDULED]
    }
  else if (req.query.status === 'cancelled')
    filter.status = CANCELLED
  else if (req.query.status === 'completed')
    filter.status = COMPLETED
  let appointments = await Appointment.findAll({
    where: {
      product_id: {
        [Op.ne]: ''
      },
      user_id: req.user.id,
      ...filter
    },
    attributes: ["apn_id", "booked_date", "product_id", "status"],
    order: [["updated_at", "DESC"]],
    ...paginate(+req.query.page, +req.query.per_page)
  })
  if (!appointments) return res.status(200).send({ status: false, message: " No design found!" })
  const data = []
  for (let appointment of appointments) {
    let product_ids = appointment.product_id.split(",")
    for (let product of product_ids) {
      const options = {
        method: "GET",
        headers: {
          Authorization: req.headers.authorization
        }
      }
      const apnProduct = await fetch(
        PRODUCT_SERVICE + "products/" + product,
        options
      )
      const response = await apnProduct.json();
      data.push({
        product_id: product,
        apn_id: appointment.apn_id,
        booked_date: appointment.booked_date,
        status: appointment.status,
        product_name: response.data.product_name,
        product_image: response.data.images[0],
      })
    }
  }
  return res.status(200).send({ status: true, message: "Successfully fetched design!", data: data })
})

//............................ USER APPOINTMENTS .....................................

//Post data into the appointments(for a given slot)
router.post("/appointments", verify, async (req, res) => {
  const { error } = validateAppointmentCreation(req.body);
  if (error)
    return res
      .status(400)
      .send({ status: 400, message: error.details[0].message });
  let selectedDesigns = await UserSelectedDesign.findAll({
    where: { user_id: req.user.id }
  })
  if (selectedDesigns.length < 1)
    return res.status(400).send({ status: false, message: "Please select at least one design!" });
  selectedDesigns = _.map(selectedDesigns, "product_id");
  selectedDesigns = selectedDesigns.toString();
  let data = {
    user_id: req.user.id,
    booked_date: req.body.booked_date,
    slot: req.body.slot,
    address: req.body.address,
    product_id: selectedDesigns,
    apn_id: "APN" + req.user.id + Math.floor(10000 + Math.random() * 90000),
    status: BOOKED
  }
  let result = await Appointment.create(data);
  return res
    .status(200)
    .send({
      status: true,
      message: "Created appointment for the selected product",
      data: [result]
    })
})
router.put("/appointments", verify, async (req, res) => {
  const appointment = await Appointment.findOne({ where: { user_id: req.user.id, apn_id: req.body.apn_id } })
  if (!appointment) return res.status(404).send({ status: false, message: "Appointment doesn't exist!" })
  if (req.body.address) {
    let updateAdr = await appointment.update({ address: req.body.address })
    if (updateAdr)
      return res
        .status(200)
        .send({
          status: true,
          message: "Updated address for the given slot",
          data: []
        });
  }
  if (req.body.booked_date) {
    let updateBookedDate = await appointment.update({ booked_date: req.body.booked_date, slot: req.body.slot, status: RESCHEDULED })
    if (updateBookedDate)
      return res
        .status(200)
        .send({ status: true, message: "Appointment has been re-scheduled successfully!", data: [] });
  }
  if (req.body.slot) {
    let updateBookedSlot = await appointment.update({ slot: req.body.slot, status: RESCHEDULED })
    if (updateBookedSlot)
      return res
        .status(200)
        .send({ status: true, message: "Appointment time slot upated successfully!", data: [] });
  }
  if (req.body.status) {
    const status = +req.body.status
    if (![CANCELLED, COMPLETED].includes(status))
      return res.status(422).send({ status: false, message: "Please provide valid status!" })
    let updateStatus = await appointment.update({ status: req.body.status })
    if (updateStatus) {
      let message = ""
      if (status == COMPLETED) message = "Appointmet has been COMPLETED!"
      if (status == CANCELLED) message = "Appointmet has CANCELLED!"
      return res
        .status(200)
        .send({ status: true, message: message, data: [] });
    }

  }
  return res.status(400).send({ status: false, message: "Please provide fileds to update!" })
})

//get data from the appointment
router.get("/appointments", verify, async (req, res) => {
  //create filter
  const filter = {}
  filter.user_id = req.user.id

  if (req.query.status === 'booked' || req.query.status === 'rescheduled')
    filter.status = {
      [Op.or]: [BOOKED, RESCHEDULED]
    }
  else if (req.query.status === 'cancelled')
    filter.status = CANCELLED
  else if (req.query.status === 'completed')
    filter.status = COMPLETED

  let appointments = await Appointment.findAll({
    where: filter,
    attributes: ["user_id", "apn_id", "booked_date", "product_id", "address", "status", "slot"],
    order: [['updated_at', 'DESC']],
    ...paginate(+req.query.page, +req.query.per_page)
  })
  if (appointments) {
    res.status(200).send({ status: true, message: "Successfully fetched appointments!", data: appointments })
  }
  else {
    res.status(200).send({ status: true, message: "No appointment were found for your id" })
  }
})
//get appointments by id
router.get("/appointments/:id", verify, async (req, res) => {
  let appointment = await Appointment.findAll({
    where: { user_id: req.user.id, apn_id: req.params.id },
    attributes: ["user_id", "apn_id", "booked_date", "product_id", "address", "status", "slot"]
  })
  if (appointment) {
    res.status(200).send({ status: true, message: "Successfully fetched the appointment!", data: appointment })
  }
  else {
    res.status(200).send({ status: true, message: "Appointment does't exist!" })
  }
})

//............................ USER MEASUREMENTS .....................................

router.post('/measurements', verify, async (req, res) => {
  const { error } = validateMeasurement(req.body)
  if (error)
    return res
      .status(400)
      .send({ status: false, message: error.details[0].message });

  let measurement = await UserMeasurement.findOne({
    where: { user_id: req.user.id },
    order: [['updated_at', 'DESC']],
    limit: 1
  })
  console.log(measurement)
  if (measurement) {
    const insertedMeasurement = await insertUserMeasurement(req, res)
    return res.status(201).send({ status: true, measurement: "Measurement added successfully!", data: insertedMeasurement })
  }
  if (!measurement) {
    const insertedMeasurement = await insertUserMeasurement(req, res)
    return res.status(201).send({ status: true, measurement: "Measurement added successfully!", data: insertedMeasurement })
  }
})
router.get('/measurements', verify, async (req, res) => {
  const measurements = await UserMeasurement.findAll({
    where: { user_id: req.user.id },
    order: [["created_at", "DESC"]]
  })
  if (!measurements) return res.status(404).send({ status: false, message: "No measurement found!" })
  return res.status(200).send({ status: true, message: "Measurements fetched successfully!", data: measurements })
})
router.get('/measurements/:id', verify, async (req, res) => {
  const measurements = await UserMeasurement.findOne({
    where: { id: req.params.id, user_id: req.user.id }
  })
  if (!measurements) return res.status(404).send({ status: false, message: "No measurement found!" })
  return res.status(200).send({ status: true, message: "Measurements fetched successfully!", data: measurements })
})
//............................ USER CART .....................................
router.post('/carts', verify, async (req, res) => {
  //validate data before creating user
  const { error } = validateCartDetail(req.body);
  if (error)
    return res
      .status(400)
      .send({ status: false, message: error.details[0].message });
  const cart = await UserCart.findOne({
    where: {
      user_id: req.user.id,
      product_id: req.body.product_id,
      product_size: req.body.product_size
    }
  })
  if (cart) return res.status(409).send({ status: false, message: "Product already added to your cart!" })
  const cartItem = await UserCart.create({
    user_id: req.user.id,
    product_id: req.body.product_id,
    product_size: req.body.product_size,
    product_quantity: req.body.product_quantity
  })
  return res.status(201).send({ status: false, message: "Product has been added to your cart!", data: cartItem})
})
router.put('/carts', verify, async (req, res) => {
  UserCart.update({
    product_quantity: req.body.product_quantity
  },
    {
      where: {
        user_id: req.user.id,
        product_id: req.body.product_id,
        product_size: req.body.product_size
      }
    })
    return res.status(200).send({ status: true, message: "Succesfully updated product quantity!", data: []})
})
router.get('/carts', verify, async ( req, res) => {
  let carts = await UserCart.findAll({
    where: {
      user_id: req.user.id
    },
    ...paginate(+req.query.page, +req.query.per_page)

  })
  carts = JSON.parse(JSON.stringify(carts))
  const cartData = {}
  cartData.total_actualPrice = 0
  cartData.total_discountPrice = 0
  cartData.total_netPrice = 0
  cartData.cartItems = []
  for(cart of carts) {
    let data = {}
    const product = await getProductById(cart.product_id, req.headers.authorization)
    if(_.isEmpty(product)) continue 
    let actual_price = (product.prices[cart.product_size]) * cart.product_quantity
    let discount_per = product.discount_per
    let total_discount = ((discount_per * actual_price) / 100 ) 
    let net_price =  _.ceil(actual_price - total_discount)

    cartData.total_actualPrice += actual_price
    cartData.total_discountPrice += total_discount
    cartData.total_netPrice += net_price

    data.cart_id = cart.id
    data.product_id = product.product_id
    data.product_name = product.product_name
    data.product_quantity = cart.product_quantity
    data.actual_price = actual_price
    data.discount_per = discount_per +'%'
    data.net_price = net_price 
    data.total_discount = total_discount
    data.size = cart.product_size
    data.deliver_by  = product.deliver_by
    data.supplier_id = product.supplier_id
    data.image = product.images[0]
    cartData.cartItems.push(data)
  }
  return res.status(200).send({ status: true, message: "Successfully fetched all cart items!", data: cartData})
})
router.delete('/carts/:id', verify, async(req, res) => {
  UserCart.destroy({
    where: {
      user_id: req.user.id,
      id: req.params.id
    }
  })
  return res.status(200).send({ status: true, message: "Successfully deleted item from cart!", data: []})
})
module.exports = router;
