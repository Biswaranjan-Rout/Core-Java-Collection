'use strict';
module.exports = (sequelize, DataTypes) => {
  const RefreshToken = sequelize.define('RefreshToken', {
    user_id: DataTypes.INTEGER.UNSIGNED,
    ref_token: DataTypes.STRING,
    issued_time: DataTypes.DATE,
    expired_time: DataTypes.DATE
  }, {});
  RefreshToken.associate = function(models) {
    // associations can be defined here
  };
  return RefreshToken;
};