'use strict';
module.exports = (sequelize, DataTypes) => {
  const shipmentProcessHistory = sequelize.define('shipmentProcessHistory', {
    shipment_id: DataTypes.STRING,
    agent_id: DataTypes.INTEGER,
    status_id: DataTypes.INTEGER,
    remark: DataTypes.STRING
  }, {});
  shipmentProcessHistory.associate = function(models) {
    // associations can be defined here
  };
  return shipmentProcessHistory;
};