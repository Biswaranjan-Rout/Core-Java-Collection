'use strict';
module.exports = (sequelize, DataTypes) => {
  const ProductQuantity = sequelize.define('ProductQuantity', {
    product_id: DataTypes.INTEGER.UNSIGNED,
    s: DataTypes.INTEGER,
    m: DataTypes.INTEGER,
    l: DataTypes.INTEGER,
    xl: DataTypes.INTEGER
  }, {});
  ProductQuantity.associate = function(models) {
    ProductQuantity.belongsTo(models.Product, { foreignKey: 'product_id' })
  };
  return ProductQuantity;
};