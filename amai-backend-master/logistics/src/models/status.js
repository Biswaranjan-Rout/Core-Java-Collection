'use strict';
module.exports = (sequelize, DataTypes) => {
  const status = sequelize.define('status', {
    status_code: DataTypes.STRING,
    status_description: DataTypes.STRING,
    public_description: DataTypes.STRING,
    status_reason: DataTypes.STRING
  }, {});
  status.associate = function(models) {
    // associations can be defined here
  };
  return status;
};