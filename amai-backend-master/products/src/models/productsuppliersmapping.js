'use strict';
module.exports = (sequelize, DataTypes) => {
  const ProductSuppliersMapping = sequelize.define('ProductSuppliersMapping', {
    product_id: DataTypes.INTEGER,
    supplier_id: DataTypes.INTEGER
  }, {});
  ProductSuppliersMapping.associate = function(models) {
    // associations can be defined here
  };
  return ProductSuppliersMapping;
};