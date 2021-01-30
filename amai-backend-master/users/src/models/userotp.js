'use strict';
module.exports = (sequelize, DataTypes) => {
  const UserOTP = sequelize.define('UserOTP', {
    email: DataTypes.STRING,
    OTP: DataTypes.INTEGER.UNSIGNED,
    is_verified: DataTypes.BOOLEAN,
    expiry: DataTypes.DATE
  }, {});
  UserOTP.associate = function(models) {
    // associations can be defined here
  };
  return UserOTP;
};