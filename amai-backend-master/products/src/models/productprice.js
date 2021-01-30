'use strict';
module.exports = (sequelize, DataTypes) => {
  const ProductPrice = sequelize.define('ProductPrice', {
    s: DataTypes.FLOAT,
    m: DataTypes.FLOAT,
    l: DataTypes.FLOAT,
    xl: DataTypes.FLOAT,
    discount: DataTypes.FLOAT,
  }, {});
  ProductPrice.associate = function(models) {
    ProductPrice.belongsTo(models.Product, { foreignKey: 'product_id' })
  };
  return ProductPrice;
  };
