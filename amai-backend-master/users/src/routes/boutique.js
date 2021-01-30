const express = require("express");
const router = express.Router();
const _ = require("lodash");
const op = require("sequelize").Op;
//models
const User = require("../models").User;
const Role = require("../models").Role;
const StoreDetail = require("../models").StoreDetail;
const BankDetail = require("../models").BankDetail;
const UserRole = require("../models").UserRole;
const BusinessDetail = require("../models").BusinessDetail;
const PersonalDetail = require("../models").PersonalDetail;

//services
const verify = require("../services/verifyToken");

//middleware
const authorize = require("../middleware/authorize");

//Get all boutiques details
router.get("/", verify, async (req, res) => {
  const boutiques = await User.findAll({
    where: { "$UserRole.Role.role_type$": "boutique" },
    include: [
      {
        model: UserRole,
        include: {
          model: Role
        }
      },
      {
        model: PersonalDetail
      },
      {
        model: StoreDetail
      },
      {
        model: BankDetail
      },
      {
        model: BusinessDetail
      }
    ]
  });
  if (boutiques) {
    const result = _.map(boutiques, function(o) {
      return _.omit(o.dataValues, ["password", "UserRole"]);
    });
    return res.status(200).send({
      status: true,
      message: "successfully fetched all boutique details",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

//Get boutiques store details
router.get("/boutique-detail", verify, async (req, res) => {
  const boutiques = await User.findAll({
    where: { "$UserRole.Role.role_type$": "boutique" },
    include: [
      {
        model: UserRole,
        include: {
          model: Role
        }
      },
      {
        model: PersonalDetail
      },
      {
        model: StoreDetail
      }
    ],
    raw: true
  });
  if (boutiques) {
    const result = _.map(boutiques, obj => ({
      id: obj.id,
      address1: obj["StoreDetail.address1"],
      address2: obj["StoreDetail.address2"],
      store_image: obj["StoreDetail.store_image"]
    }));
    return res.status(200).send({
      status: true,
      message: "successfully fetched all boutique store details",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

//Get  boutique details by id
router.get("/:id", verify, async (req, res) => {
  const boutique = await User.findOne({
    where: { "$UserRole.Role.role_type$": "boutique", id: req.params.id },
    include: [
      {
        model: UserRole,
        include: {
          model: Role
        }
      },
      {
        model: PersonalDetail
      },
      {
        model: StoreDetail
      }
    ],
    raw: true
  });
  if (boutique) {
    const result = {
      id: boutique.id,
      address:
        boutique["StoreDetail.address1"] +
        " " +
        boutique["StoreDetail.address2"],
      store_image: boutique["StoreDetail.store_image"],
      owner_name: "Rajesh",
      rating: "4.5",
      decription: "We sell high quality women wear."
    };
    return res.status(200).send({
      status: true,
      message: "successfully fetched all boutique details by id",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

module.exports = router;
