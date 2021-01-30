'use strict';
module.exports = (sequelize, DataTypes) => {
  const InvoiceStatus = sequelize.define('InvoiceStatus', {
    invoice_status_code: DataTypes.TINYINT,
    status: DataTypes.STRING
  }, {});
  InvoiceStatus.associate = function(models) {
    // associations can be defined here
  };
  return InvoiceStatus;
};