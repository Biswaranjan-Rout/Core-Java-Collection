'use strict';
module.exports = (sequelize, DataTypes) => {
  const ProductQCStatus = sequelize.define('ProductQCStatus', {
    status: DataTypes.TINYINT.UNSIGNED,
    supplier_id: DataTypes.INTEGER.UNSIGNED,
    description:DataTypes.STRING
  }, {});
  ProductQCStatus.associate = function(models) {
    ProductQCStatus.belongsTo(models.Product, { foreignKey: 'product_id'})
  };
  return ProductQCStatus;
};