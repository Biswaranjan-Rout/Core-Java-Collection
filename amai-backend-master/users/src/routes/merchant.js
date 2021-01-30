const express = require("express");
const router = express.Router();
const _ = require("lodash");
const Op = require("sequelize").Op;

//models
const User = require("../models").User;
const Role = require("../models").Role;
const StoreDetail = require("../models").StoreDetail;
const UserRole = require("../models").UserRole;
const BusinessDetail = require("../models").BusinessDetail;
const BankDetail = require("../models").BankDetail;
const WishList = require("../models").WishList;

//services
const verify = require("../services/verifyToken");
const paginate = require("../services/paginate")

//middleware
const authorize = require("../middleware/authorize");

//Get merchant store details
router.get("/", verify, async (req, res) => {
  if (
    !["boutique", "designer", "design_house"].includes(
      req.query.type.trim().toLowerCase()
    )
  ) {
    return res
      .status(404)
      .send({
        error: { status: 400, message: "Please provide valid merchant type." }
      });
  }

  const merchants = await User.findAll({
    where: { "$UserRole.Role.role_type$": req.query.type },
    include: [
      {
        model: UserRole,
        include: {
          model: Role
        }
      },
      {
        model: BusinessDetail,
        where: {
          user_id:{
            [Op.ne]: null
          }
        }
      },
      {
        model: StoreDetail,
        where: {
          user_id:{
            [Op.ne]: null
          }
        }
      }
    ],
    raw: true,
    ...paginate(+req.query.page, +req.query.per_page)
  });
  if (merchants.length > 0) {
    const wishlists = await WishList.findAll({ where: { user_id: req.user.id, item_type: req.query.type}, attributes:["item_id"]})
    const wishlisted_merchants = _.map(wishlists, "item_id");
    const result = _.map(merchants, function(obj){
      let data = {
      id: obj.id,
      name: obj["BusinessDetail.company_name"],
      address1: obj["StoreDetail.address1"],
      address2: obj["StoreDetail.address2"],
      store_image: obj["StoreDetail.store_image"]
      }
      if (wishlisted_merchants.includes(obj.id)) {
        return _.extend({is_wishlisted: true }, data);
      }
      return _.extend({is_wishlisted: false }, data);
    });
    return res.status(200).send({
      status: true,
      message: "successfully fetched merchants store details",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({status: false, message: "Resource not found!"});
  }
});
//Get  boutique details by id
router.get("/:id", verify, async (req, res) => {
  const merchant = await User.findOne({
    where: {id: req.params.id },
    include: [
      {
        model: StoreDetail,
        attributes:['address1','address2','store_image']
      },
      {
        model: BusinessDetail,
        attributes:['company_name']
      }
    ],
    attributes:['id'],
    raw: true
  });
  if (merchant) {
    const wishlists = await WishList.findAll({
      where: { user_id: req.user.id, item_type: { [Op.ne] : "product"}},
      attributes:["item_id"]
    })
    const wishlisted_merchants = _.map(wishlists, "item_id");
    const is_wishlisted = wishlisted_merchants.includes(merchant.id)?true:false
    const result = {
      id: merchant.id,
      address:
      merchant["StoreDetail.address1"] +
        " " +
      merchant["StoreDetail.address2"],
      store_image: merchant["StoreDetail.store_image"],
      company_name: merchant["BusinessDetail.company_name"],
      rating: "4.5",
      is_wishlisted: is_wishlisted,
      decription: "We sell high quality women wear."
    };
    return res.status(200).send({
      status: true,
      message: "Successfully fetched merchant deatils!",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({status: false, message: "Resource not found!" });
  }
});
module.exports = router;