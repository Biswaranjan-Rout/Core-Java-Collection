const express = require("express");
const router = express.Router();
const _ = require("lodash");
const Op = require("sequelize").Op;

//models
const User = require("../models").User;
const Role = require("../models").Role;
const PersonalDetail = require("../models").PersonalDetail;
const BusinessDetail = require("../models").BusinessDetail;
const StoreDetail = require("../models").StoreDetail;
const BankDetail = require("../models").BankDetail;
const UserRole = require("../models").UserRole;
const Appointment = require("../models").Appointment;

//services
const verify = require("../services/verifyToken");
const {
  validateRoleCreation,
  validateAdminUpdateAppointment,
  validateMerchantRejection
} = require("../services/validator");
const createRole = require("../services/createRole");
const paginate = require("../services/paginate");
const sendMail = require("../services/sendMail")

//middleware
const authorize = require("../middleware/authorize");

//configs
const { ACTIVE, INACTIVE, APPROVED, REJECTED, BOOKED, RESCHEDULED, CANCELLED, COMPLETED} = require('config')

//Add new role
router.post(
  "/role-management/roles",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    //validate data before creating user
    const { error } = validateRoleCreation(req.body);
    if (error)
      return res
        .status(400)
        .send({status: false , message: error.details[0].message });
    //check if user  exisits
    const user = await User.findOne({ where: { id: req.user.id } });
    if (!user)
      return res
        .status(400)
        .send({status: false, message: "Access denied!" });
    //check if role exists
    const role = await Role.findOne({
      where: { role_type: req.body.role_type }
    });
    if (role)
      return res
        .status(400)
        .send({  status: false, message: "Role already exists!" });
    const createdRole = await createRole(req, res);
    const result = _.omit(createdRole, ["id"]);
    return res.status(201).send({
      status: true,
      message: "successfully added role",
      data: result
    });
  }
);

//Get all roles
router.get(
  "/role-management/roles",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    //check if user  exisits
    const user = await User.findOne({ where: { id: req.user.id } });
    if (!user)
      return res
        .status(401)
        .send({ status: false, message: "Access denied!" });
    //get all roles
    const roles = await Role.findAll();
    if (roles) {
      return res.status(201).send({
        status: true,
        message: "successfully fetched all roles",
        data: roles
      });
    } else
      return res
        .status(404)
        .send({status: false, message: "Role not found!" });
  }
);

//Get a role by ID
router.get(
  "/role-management/roles/:id",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    //check if user  exisits
    const user = await User.findOne({ where: { id: req.user.id } });
    if (!user)
      return res
        .status(400)
        .send({status: false, message: "Access denied!" });
    //get all roles
    const role = await Role.findOne({ where: { id: req.params.id } });
    if (role) {
      return res.status(200).send({
        status: true,
        message: "successfully fetched role",
        data: role
      });
    } else
      return res
        .status(404)
        .send({status: false, message: "Role not found!" });
  }
);
//Update a role by ID
router.put(
  "/role-management/roles/:id",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    //check if user  exisits
    const user = await User.findOne({ where: { id: req.user.id } });
    if (!user)
      return res
        .status(400)
        .send({status: false, message: "Access denied!" });
    //find role and update
    const role = await Role.update(
      { role_type: req.body.role_type, is_active: req.body.is_active },
      { where: { id: req.params.id } }
    );
    return res
      .status(200)
      .send({ status: true, message: "Updated Successfully!", data: role });
  }
);

router.get(
  "/merchant-management/merchants/:id",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    const user_id = req.params.id;
    //get the details of that particular merchant
    const merchant = await User.findOne({
      where: { id: user_id },
      include: [
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
    if (!merchant)
      return res.status(400).send({
        error: { status: 400, message: "Merchant details not found!" }
      });
    return res.status(200).send({
      status: true,
      message: "Successfully fetched merchant details",
      result: merchant
    });
  }
);

//GET MERCHANT DETAILS BY is_approved=true/false
router.get(
  "/merchant-management/merchants",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    const isApproved = req.query.is_approved == "true" ? 1 : 0;
    let merchants = await User.findAll({
      where: { is_approved: isApproved, is_active : ACTIVE},
      include: [
        {
          model: UserRole,
          include: {
            model: Role,
            where: { role_type: { [Op.notIn]: ["user", "admin"] } }
          }
        },
        {
          model: PersonalDetail,
          where: {
            user_id: {
              [Op.ne]: null
            }
          }
        },
        {
          model: StoreDetail,
          where: {
            user_id: {
              [Op.ne]: null
            }
          }
        },
        {
          model: BusinessDetail,
          where: {
            user_id: {
              [Op.ne]: null
            }
          }
        },
        {
          model: BankDetail,
          where: {
            user_id: {
              [Op.ne]: null
            }
          }
        }
      ],
      attributes: { exclude: ["password", "createdAt", "updatedAt"] },
      ...paginate(+req.query.page, +req.query.per_page)
    });
    merchants = JSON.parse(JSON.stringify(merchants));
    if (!merchants) {
      return res.status(404).send({
        status: 400,
        message: "No merchant found!"
      });
    } else {
      let data = _.map(merchants, function(merchant) {
        return {
          merchant_id: merchant.id,
          merchant_type: _.startCase(merchant.UserRole.Role.role_type),
          registration_date: merchant.created_at,
          company_name: merchant.BusinessDetail.company_name || "NA",
          store_name: merchant.StoreDetail.store_name || "NA"
        };
      });
      return res.status(200).send({
        status: true,
        message: "Successfully fetched merchats detail!",
        result: data
      });
    }
  }
);

//APPROVE A MERCHANT AS A VERIFIED MERCHANT
router.put(
  "/merchant-management/merchants/:id/approve",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    let merchant = await User.findOne({
      where:{id: req.params.id},
      include: [
        {
          model: UserRole,
          include: {
            model: Role,
            where: { role_type: { [Op.notIn]: ["user", "admin"] } }
          }
        }
      ],
      attributes:["id","email"]
    })
    if(!merchant) return res.status(404).send({status:false, message: "Merchant not found!"})

     merchant.update(
      {
        is_active: ACTIVE,
        is_approved: APPROVED
      }
    )
    let msg = "You application as a Vendor at AMAI has been approved!"
      sendMail(merchant.email, "Application Approved!", msg)
    return res
        .status(200)
        .send({ status: true, message: "Merchant registration Approved!", data:[] });
  }
);
//APPROVE A MERCHANT AS A VERIFIED MERCHANT
router.post(
  "/merchant-management/merchants/reject",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    const { error } = validateMerchantRejection(req.body);
    if (error)
    return res
      .status(400)
      .send({status: false, message: error.details[0].message });
    let merchant = await User.findOne({
      where:{id: req.body.id},
      include: [
        {
          model: UserRole,
          include: {
            model: Role,
            where: { role_type: { [Op.notIn]: ["user", "admin"] } }
          }
        }
      ],
      attributes:["id","email"]
    })
    if(!merchant) return res.status(404).send({status:false, message: "Merchant not found!"})

     merchant.update(
      {
        is_active: INACTIVE
      }
    )
      sendMail(merchant.email, "Application Pending", req.body.body)
      
    return res
        .status(200)
        .send({ status: true, message: "Merchant registration rejected!", data:[] });
  }
);

//GET APPOINTMENT BASED ON STATUS
router.get(
  "/appointment-management",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    const Status = +req.query.status;
    if (!Status) {
       let getAppointments = await Appointment.findAll({
         order:[['booked_date', 'DESC']]
      });
      if (getAppointments.length > 0) {
        getAppointments = JSON.parse(JSON.stringify(getAppointments));
        res.status(200).send({
          status: true,
          message: "fetched all data for the specified status of apppointment",
          data: getAppointments
        });
      }
    }
    let getAppointments = await Appointment.findAll({
      where: { status: Status },
       order:[['booked_date', 'DESC']]
    });
    if (getAppointments.length > 0) {
      getAppointments = JSON.parse(JSON.stringify(getAppointments));
      res.status(200).send({
        status: true,
        message: "fetched all data for the specified status of apppointment",
        data: getAppointments
      });
    } else {
      return res.status(404).send({
        status: 404,
        message: "No appointment were found for the given status"
      });
    }
  }
)

router.put(
  "/appointment-management",
  verify,
  authorize(["admin"]),
  async (req, res) => {
    const { error } = validateAdminUpdateAppointment(req.body);
    if (error)
      return res
        .status(400)
        .send({ error: { status: 400, message: error.details[0].message } });
    let agentName = req.body.agn_name;
    let agentContact = req.body.agn_contact;
    let appointmentId = req.body.apn_id;
    let status = req.body.status;
    if (status && ![CANCELED, RESOLVED, RESCHEDULED].includes(status))
    return res.status(422).send({ status: false, message: "Action not allowed!" })
    const appointment = await Appointment.findOne({
      where: { apn_id: appointmentId }
    });
    if (!appointment)
      return res
        .status(404)
        .send({ status: false, message: "Appointment not found!" });

    const updatedApn = await appointment.update({
      agn_name: agentName,
      agn_contact: agentContact,
      status: status
    });
    if (updatedApn) {
      return res.status(200).send({
        status: true,
        message:
          "Updated Succesfullly!"
      });
    } else {
      return res.status(404).send({
        status: 404,
        message: "Appointment doesn't exist!"
      });
    }
  }
);
module.exports = router;
