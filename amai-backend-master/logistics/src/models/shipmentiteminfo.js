'use strict';
module.exports = (sequelize, DataTypes) => {
  const shipmentItemInfo = sequelize.define('shipmentItemInfo', {
    shipment_id: DataTypes.INTEGER,
    order_id: DataTypes.INTEGER,
    item_price: DataTypes.DOUBLE,
    item_type: DataTypes.STRING,
    item_description: DataTypes.STRING,
    invoice_id: DataTypes.INTEGER,
    total_tax: DataTypes.DOUBLE,
    item_contents: DataTypes.STRING
  }, {});
  shipmentItemInfo.associate = function(models) {
    // associations can be defined here
  };
  return shipmentItemInfo;
};