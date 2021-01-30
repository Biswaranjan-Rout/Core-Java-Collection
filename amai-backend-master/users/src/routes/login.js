const express = require("express");
const router = express.Router();
const _ = require("lodash");
const User = require("../models").User;
const PersonalDetail = require("../models").PersonalDetail;
const { validateLogin } = require("../services/validator");
const login = require("../services/login");
const Op = require("sequelize").Op

//Login User
router.post("/", async (req, res) => {
  //validaate data before logging in  user
  const { error } = validateLogin(req.body);
  if (error) return res.status(400).send({status: false, message: error.details[0].message});
  //check if user  exisits
  const user = await User.findOne({ 
    where:{
    [Op.or]: [{user_name: req.body.user_name},{email: req.body.user_name}, {contact_number: req.body.user_name}]
    },
    include:[{
      model: PersonalDetail,
      attributes:["profile_image"]
    }]
  });
  if (!user)
    return res
      .status(400)
      .send({ status: false, message: "User not found!"  });
  const loginResult = await login(req, user);
  if(!loginResult.status) return res.status(400).send(loginResult)
  loginResult.data.profile_image = user.PersonalDetail.profile_image
  res.set("Authorization", "Bearer " + loginResult.data.token);
  return res.status(200).send(loginResult)
});
module.exports = router;
