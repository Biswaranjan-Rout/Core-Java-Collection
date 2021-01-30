const express = require("express");
const router = express.Router();
const _ = require("lodash");
const op = require("sequelize").Op;

//models
const User = require("../models").User;
const Role = require("../models").Role;
const StoreDetail = require("../models").StoreDetail;
const UserRole = require("../models").UserRole;
const BusinessDetail = require("../models").BusinessDetail;

//services
const verify = require("../services/verifyToken");

//middleware
const limitOffsetChecker = require("../middleware/limitOffsetChecker");

//config
const config = require("config");

//GET ALL VENDOR DETAILS
router.get("/detail", verify, limitOffsetChecker, async (req, res) => {
  const vendors = await User.findAll({
    // if user sends negative number then default value will be assigned to the paginator i.e 5,1
    limit: req.paginator.limit || 5,
    offset: req.paginator.offset || 1,
    include: [
      {
        model: UserRole,
        attributes: ["id"],
        include: {
          model: Role,
          where: {
            role_type: { [op.not]: [config.get("ADMIN"), config.get("USER")] }
          },
          attributes: ["id"]
        }
      },
      {
        model: StoreDetail,
        attributes: ["address1", "address2", "store_image"]
      },
      {
        model: BusinessDetail,
        attributes: ["company_name"]
      }
    ],
    attributes: ["id"],
    raw: true
  });
  if (vendors) {
    const result = _.map(vendors, obj => ({
      id: obj.id,
      name: obj["BusinessDetail.company_name"],
      address1: obj["StoreDetail.address1"],
      address2: obj["StoreDetail.address2"],
      store_image: obj["StoreDetail.store_image"]
    }));
    return res.status(200).send({
      status: true,
      message: "successfully fetched all vendors details",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

// GET VENDOR BY ID
router.get("/detail/:id", verify, async (req, res) => {
  const vendor = await User.findOne({
    where: { id: req.params.id },
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
        model: BusinessDetail,
        attributes: ["company_name"]
      }
    ],
    raw: true
  });
  if (
    vendor &&
    vendor["Role.RoleType"] !== config.get("ADMIN") &&
    vendor["Role.RoleType"] !== config.get("USER")
  ) {
    const result = {
      id: vendor.id,
      name: vendor["BusinessDetail.company_name"],
      address:
        vendor["StoreDetail.address1"] + " " + vendor["StoreDetail.address2"],
      store_image: vendor["StoreDetail.store_image"],
      owner_name: "Test_owner",
      rating: "4.5",
      decription: "Some description of the vendor."
    };
    return res.status(200).send({
      status: true,
      message: "successfully fetched vendor by id",
      data: result
    });
  } else {
    return res
      .status(404)
      .send({ error: { status: 404, message: "Resource not found!" } });
  }
});

module.exports = router;
