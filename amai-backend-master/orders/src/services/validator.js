const Joi = require("@hapi/joi").extend(require('@hapi/joi-date'))

//Registeration validation
const validateOrderCreate = (data) => {
  const schema = Joi.object({
    product_id : Joi.number()
      .required()
      .strict(),
    product_quantity : Joi.number()
      .required()
      .strict(),
    order_detail:Joi.string()
      .required(),
    order_type:Joi.number()
      .min(1)
      .max(2)
      .required()
      .strict(),
    product_quantity: Joi.number()
      .min(1)
      .max(10)
      .required()
      .strict(),
    product_size: Joi.string()
      .valid('s','m','l','xl')
      .required()
  });
  return schema.validate(data)
}



module.exports = {
  validateOrderCreate,

}