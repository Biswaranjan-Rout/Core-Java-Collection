'use strict';
module.exports = (sequelize, DataTypes) => {
  const Invoice = sequelize.define('Invoice', {
    invoice_number: DataTypes.INTEGER,
    order_id: DataTypes.INTEGER,
    invoice_date: DataTypes.DATE,
    invoice_detail: DataTypes.STRING,
    invoice_status_code: DataTypes.INTEGER
  }, {});
  Invoice.associate = function(models) {
    // associations can be defined here
  };
  return Invoice;
};