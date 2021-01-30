'use strict';
module.exports = (sequelize, DataTypes) => {
  const ProductAttribute = sequelize.define('ProductAttribute', {
    size: DataTypes.CHAR,
    quantity: DataTypes.INTEGER,
    price: DataTypes.FLOAT
  }, {});
  ProductAttribute.associate = function(models) {
    // associations can be defined here
  };
  return ProductAttribute;
};