'use strict';
module.exports = (sequelize, DataTypes) => {
  const Shipment_Items = sequelize.define('Shipment_Items', {
    shipment_id: DataTypes.INTEGER,
    order_item_id: DataTypes.INTEGER
  }, {});
  Shipment_Items.associate = function(models) {
    // associations can be defined here
  };
  return Shipment_Items;
};