'use strict';
module.exports = (sequelize, DataTypes) => {
  const shipmentInfo = sequelize.define('shipmentInfo', {
    shipping_from_customer_id: DataTypes.INTEGER,
    delivery_customer_id: DataTypes.INTEGER,
    recieved_by_customer_id: DataTypes.INTEGER,
    pin_code: DataTypes.INTEGER,
    shipment_type: DataTypes.ENUM,
    total_price: DataTypes.FLOAT
  }, {});
  shipmentInfo.associate = function(models) {
    // associations can be defined here
  };
  return shipmentInfo;
};