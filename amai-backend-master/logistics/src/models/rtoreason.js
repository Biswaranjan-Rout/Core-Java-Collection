'use strict';
module.exports = (sequelize, DataTypes) => {
  const rtoReason = sequelize.define('rtoReason', {
    reason: DataTypes.STRING,
    public_description: DataTypes.STRING,
    is_active: DataTypes.TINYINT
  }, {});
  rtoReason.associate = function(models) {
    // associations can be defined here
  };
  return rtoReason;
};