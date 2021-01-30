'use strict';
module.exports = (sequelize, DataTypes) => {
  const Supplier = sequelize.define('Supplier', {
    supplier_name: DataTypes.STRING,
    contact_number: DataTypes.STRING
  }, {});
  Supplier.associate = function(models) {
    // associations can be defined here
  };
  return Supplier;
};