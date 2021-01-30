'use strict';
module.exports = (sequelize, DataTypes) => {
  const banks = sequelize.define('banks', {
    bank_name: DataTypes.STRING,
    code: DataTypes.STRING,
    updater: DataTypes.STRING
  }, {});
  banks.associate = function(models) {
    // associations can be defined here
  };
  return banks;
};