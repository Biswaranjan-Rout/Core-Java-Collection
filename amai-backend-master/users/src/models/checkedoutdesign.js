'use strict';
module.exports = (sequelize, DataTypes) => {
  const CheckedOutDesign = sequelize.define('CheckedOutDesign', {
    user_id: DataTypes.INTEGER.UNSIGNED,
    product_id: DataTypes.INTEGER.UNSIGNED,
    qty: DataTypes.INTEGER.UNSIGNED
  }, {});
  CheckedOutDesign.associate = function(models) {
    // associations can be defined here
  };
  return CheckedOutDesign;
};