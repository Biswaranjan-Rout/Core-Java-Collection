'use strict';
module.exports = (sequelize, DataTypes) => {
  const shipmentAttribute = sequelize.define('shipmentAttribute', {
    name: DataTypes.STRING,
    value: DataTypes.STRING,
    shipment_id: DataTypes.STRING
  }, {});
  shipmentAttribute.associate = function(models) {
    // associations can be defined here
  };
  return shipmentAttribute;
};