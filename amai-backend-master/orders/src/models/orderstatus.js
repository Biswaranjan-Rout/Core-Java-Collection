'use strict';
module.exports = (sequelize, DataTypes) => {
  const OrderStatus = sequelize.define('OrderStatus', {
    order_status_code: DataTypes.TINYINT,
    status: DataTypes.STRING
  }, {});
  OrderStatus.associate = function(models) {
    // associations can be defined here
  
  };
  return OrderStatus;
};