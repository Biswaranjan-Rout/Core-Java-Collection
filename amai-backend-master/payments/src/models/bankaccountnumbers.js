'use strict';
module.exports = (sequelize, DataTypes) => {
  const bankAccountNumbers = sequelize.define('bankAccountNumbers', {
    account_number: DataTypes.STRING,
    enabled: DataTypes.TINYINT,
    updater: DataTypes.STRING,
    bank_id: DataTypes.INTEGER
  }, {});
  bankAccountNumbers.associate = function(models) {
    // associations can be defined here
  };
  return bankAccountNumbers;
};