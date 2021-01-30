const Joi = require("@hapi/joi")

//validate product details
const validateProductCreation = (data) => {
  const schema = Joi.object({
    product_name: Joi.string()
      .required(),  
    product_description: Joi.string()
      .required(),
    product_type: Joi.number()
      .min(1)
      .max(2)
      .required(),
    product_code: Joi.string()
      .required(),
    category: Joi.string()
      .required(),
    material_detail:Joi.string()
        .required(),
    sleeve:Joi.string(),
    ideal_for:Joi.string()
        .valid('men','women','all'),
    suitable_for:Joi.string(),
    pattern:Joi.string(),
    type:Joi.string(),
    pack_of:Joi.number(),
    occasion:Joi.string(),
    fabric:Joi.string(),
    deliver_by: Joi.string()
  });
  return schema.validate(data)
}

//validate product quantity details
const validateProductQuantity = (data) => {
  const schema = Joi.object({
    s: Joi.number()
      .required(),  
    m: Joi.number()
      .required(),
    l: Joi.number()
      .required(),
    xl:Joi.number()
        .required(),
  });
  return schema.validate(data)
}
//validate product price details
const validateProductPrice = (data) => {
  const schema = Joi.object({
    s: Joi.number()
      .required(),  
    m: Joi.number()
      .required(),
    l: Joi.number()
      .required(),
    xl:Joi.number()
        .required(),
    discount: Joi.number()
      .required()
  });
  return schema.validate(data)
}



module.exports.validateProductCreation = validateProductCreation
module.exports.validateProductQuantity = validateProductQuantity
module.exports.validateProductPrice = validateProductPrice
