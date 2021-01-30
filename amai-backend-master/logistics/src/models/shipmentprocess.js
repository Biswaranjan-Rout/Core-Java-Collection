'use strict';
module.exports = (sequelize, DataTypes) => {
  const shipmentProcess = sequelize.define('shipmentProcess', {
    shipment_id: DataTypes.STRING,
    agent_id: DataTypes.INTEGER,
    status_id: DataTypes.INTEGER,
    cs_notes: DataTypes.STRING
  }, {});
  shipmentProcess.associate = function(models) {
    // associations can be defined here
  };
  return shipmentProcess;
};