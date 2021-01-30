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

//Get all designers details
router.get("/", verify, async (req, res) => {
  const designers = await User.findAll({
    where: { "$UserRole.Role.role_type$": "designer" },
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
  if (designers) {
    const result = _.map(designers, function(o) {
      return _.omit(o.dataValues, ["password", "UserRole"]);
    });
    return res.status(200).send({
      status: true,
      message: "successfully fetched all designer details",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

//Get designers store details
router.get("/designer-detail", verify, async (req, res) => {
  const designers = await User.findAll({
    where: { "$UserRole.Role.role_type$": "designer" },
    include: [
      {
        model: UserRole,
        include: {
          model: Role
        }
      },
      {
        model: StoreDetail
      },
      {
        model: PersonalDetail
      }
    ],
    raw: true
  });
  if (designers) {
    const result = _.map(designers, obj => ({
      id: obj.id,
      name:
        obj["PersonalDetail.first_name"] +
        " " +
        obj["PersonalDetail.last_name"],
      address1: obj["StoreDetail.address1"],
      address2: obj["StoreDetail.address2"],
      store_image: obj["StoreDetail.store_image"]
    }));
    return res.status(200).send({
      status: true,
      message: "successfully fetched all designer store details",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

//Get  designer details by id
router.get("/:id", verify, async (req, res) => {
  const designer = await User.findOne({
    where: { "$UserRole.Role.role_type$": "designer", id: req.params.id },
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
  if (designer) {
    const result = {
      id: designer.id,
      name:
        designer["PersonalDetail.first_name"] +
        " " +
        designer["PersonalDetail.last_name"],
      address:
        designer["StoreDetail.address1"] +
        " " +
        designer["StoreDetail.address2"],
      store_image: designer["StoreDetail.store_image"],
      owner_name: "Rajesh",
      rating: "4.5",
      decription: "We sell high quality women wear."
    };
    return res.status(200).send({
      status: true,
      message: "successfully fetched designer details by id",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

module.exports = router;
