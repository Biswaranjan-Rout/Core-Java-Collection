const Joi = require("@hapi/joi")
            .extend(require('@hapi/joi-date'))

//Registeration validation
const validateRegistration = (data) => {
  const schema = Joi.object({
    user_name: Joi.string()
      .required()
      .min(3)
      .max(30),  
    email : Joi.string()
      .email()
      .required(),
    contact_number : Joi.string()
      .min(10)
      .max(15)
      .required(),
    role:Joi.string(),
    password:Joi.string()
        .required()
  });

  return schema.validate(data)
}

//login validation
const validateLogin = (data) => {
    const schema = Joi.object({
      user_name: Joi.string()
        .required(),
      password: Joi.string()
        .required(),
      repeat_password: Joi.ref('password')
    });
  
    return schema.validate(data)
  };

//Personal Detail validator 
const validatePersonalDetail = (data) => {
  const schema = Joi.object({
    first_name: Joi.string()
      .min(3)
      .max(30),
    last_name: Joi.string()
      .min(3)
      .max(30),
    alternate_number:Joi.string()
      .min(10)
      .max(15),
    dob:Joi.date()
      .format(['YYYY-MM-DD'])
      .min('1-1-1970')
      .max('1-1-2011'),
    gender:Joi.string()
      .valid('male','female','other'),
    image:Joi.any()
  });
  return schema.validate(data)
};

//Business Detail validator 
const validateBusinessDetail = (data) => {
  const schema = Joi.object({
    gstin: Joi.string()
      .alphanum()
      .length(15),
    company_name: Joi.string()
      .required(),
    pan:Joi.string()
      .length(10)
      .alphanum()
      .required(),
    year_of_operation:Joi.string()
      .required(),
    primary_category:Joi.string()
      .required()
  });
  return schema.validate(data)
};

//Store Detail validation
const validateStoreDetail = (data) => {
  const schema = Joi.object({
   store_name: Joi.string()
      .required(),
    address1: Joi.string()
      .required(),
    address2: Joi.string()
      .required(),
    district:Joi.string()
      .required(),
    pin:Joi.string()
      .length(6)
      .required()
  });
  return schema.validate(data)
};

//Bank Detail Validation
const validateBankDetail = (data) => {
  const schema = Joi.object({
    name: Joi.string()
      .required()
      .min(3)
      .max(30),
    account_no: Joi.string()
      .required(),
    ifsc:Joi.string()
      .alphanum()
  });
  return schema.validate(data)
};

//Role detail validation
const validateRoleCreation = (data) => {
  const schema = Joi.object({
    role_type: Joi.string()
      .required()
      .min(3)
      .max(30),
    is_active: Joi.number()
      .valid(1,0)
      .required()
  });
  return schema.validate(data)
};

//Appointment detail validation
const validateAppointmentCreation = (data) => {
  const schema = Joi.object({
    booked_date:Joi.date()
      .format(['YYYY/MM/DD', 'DD-MM-YYYY'])
      .min('1-1-2019')
      .max('1-1-2021'),
    slot:Joi.string().required(),
    address: Joi.string()
    
  });
  return schema.validate(data)
};
//WishList detail validation
const validateWishList = (data)=>{
  const schema = Joi.object({
    item_type: Joi.string().required(),
    item_id: Joi.number().required()
  });
  return schema.validate(data)
}

//Validate checkedout designs
const validateCheckedOutDesign = (data)=>{
  const schema = Joi.object({
    apn_id: Joi.string().required(),
    product_id: Joi.number().required(),
    qty: Joi.number().required()
  });
  return schema.validate(data)
}
//validate appointment-management update agent name and agent contact
const validateAdminUpdateAppointment = (data)=>{
  const schema = Joi.object({
    apn_id: Joi.string().required(),
    agn_name: Joi.string(),
    agn_contact: Joi.string(),
    status:Joi.number(),
  })
  return schema.validate(data)
}

//validate User Measurement
const validateMeasurement = (data)=>{
  const schema = Joi.object({
    hip_round:Joi.number(),
    nape_to_waist: Joi.number(),
    armhale_depth: Joi.number(),
    shoulder_length:Joi.number(),
    shoulder_pain_to_paint: Joi.number(),
    bicep_round:Joi.number(),
    elbow_round: Joi.number(),
    wrist_round: Joi.number(),
    height: Joi.number(),
    bust_round: Joi.number(),
    waist_round:Joi.number(),
    high_hip_round:Joi.number()
  })
  return schema.validate(data)
}
//validate appointment-management update agent name and agent contact
const validateMerchantRejection = (data)=>{
  const schema = Joi.object({
    id:Joi.number(),
    body: Joi.string()
      .required(),
  })
  return schema.validate(data)
}
//validate cart item details
const validateCartDetail = (data) => {
  const schema = Joi.object({
    product_id: Joi.number()
      .min(1)
      .required()
      .strict(),
    product_quantity: Joi.number()
      .min(1)
      .strict()
      .required(),
    product_size: Joi.string()
      .valid('s','l','m','xl')
      .required()
  })
  return schema.validate(data)
}
module.exports = {
  validateRegistration,
  validateLogin,
  validatePersonalDetail,
  validateBusinessDetail,
  validateStoreDetail,
  validateBankDetail,
  validateRoleCreation,
  validateAppointmentCreation,
  validateWishList,
  validateCheckedOutDesign,
  validateAdminUpdateAppointment,
  validateMeasurement,
  validateMerchantRejection,
  validateCartDetail
}

