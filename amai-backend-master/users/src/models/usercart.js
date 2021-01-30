'use strict';
module.exports = (sequelize, DataTypes) => {
  const UserCart = sequelize.define('UserCart', {
    user_id: DataTypes.INTEGER.UNSIGNED,
    product_id: DataTypes.INTEGER.UNSIGNED,
    product_quantity: DataTypes.TINYINT.UNSIGNED,
    product_size: DataTypes.STRING
  }, {});
  UserCart.associate = function(models) {
    // associations can be defined here
  };
  return UserCart;
};