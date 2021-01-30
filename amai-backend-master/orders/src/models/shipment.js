'use strict';
module.exports = (sequelize, DataTypes) => {
  const Shipment = sequelize.define('Shipment', {
    order_id: DataTypes.INTEGER,
    invoice_number: DataTypes.INTEGER,
    shipment_tracking_number: DataTypes.INTEGER,
    shipment_date: DataTypes.DATE,
    other_shipment_details: DataTypes.STRING
  }, {});
  Shipment.associate = function(models) {
    // associations can be defined here
  };
  return Shipment;
};