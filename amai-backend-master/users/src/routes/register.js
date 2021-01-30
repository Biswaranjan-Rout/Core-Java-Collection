const express = require("express");
const router = express.Router();
const _ = require("lodash");
const User = require("../models").User;
const register = require("../services/register");
const { validateRegistration } = require("../services/validator");
const Op = require("sequelize").Op

router.post("/", async (req, res) => {
  //validate data before creating user
  const { error } = validateRegistration(req.body);
  if (error)
    return res
      .status(400)
      .send({status: false, message: error.details[0].message });
  //create user
  //check if user  exisits
  const user = await User.findOne({ where:{
    [Op.or]: [{user_name: req.body.user_name},{email: req.body.user_name}, {contact_number: req.body.user_name}]
  }});
  if (user)
    return res
      .status(400)
      .send({ status: false, message: "User already exists!"  });
  const registerResult = await register(req, res);
  const result = _.omit(registerResult, ["password"]);
  // return res.status(201).send(result)
  return res.status(200).send({
    status: true,
    message: "Successfully registered!",
    data: result
  });
});

module.exports = router;
