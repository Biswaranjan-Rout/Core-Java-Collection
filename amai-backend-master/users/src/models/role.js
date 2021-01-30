'use strict';
module.exports = (sequelize, DataTypes) => {
  const Role = sequelize.define('Role', {
    role_type: DataTypes.STRING,
    is_active: DataTypes.TINYINT
  }, {});
  Role.associate = function(models) {
    Role.hasOne(models.UserRole , { foreignKey: 'role_id' })
  };
  return Role;
};