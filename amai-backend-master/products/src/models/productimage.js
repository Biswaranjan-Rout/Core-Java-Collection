'use strict';
module.exports = (sequelize, DataTypes) => {
  const ProductImage = sequelize.define('ProductImage', {
    image: DataTypes.STRING,
    variant: DataTypes.STRING
  }, {});
  ProductImage.associate = function(models) {
    // associations can be defined here
    ProductImage.belongsTo(models.Product, { foreignKey: 'product_id' })
  };
  return ProductImage;
};