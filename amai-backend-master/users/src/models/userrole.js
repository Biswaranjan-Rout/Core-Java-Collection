'use strict';
module.exports = (sequelize, DataTypes) => {
  const UserRole = sequelize.define('UserRole', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    role_id:DataTypes.INTEGER.UNSIGNED,
  }, {});
  UserRole.associate = function(models) {
    UserRole.belongsTo(models.User, { foreignKey: 'user_id'})
    UserRole.belongsTo(models.Role, { foreignKey: 'role_id'})
  };
  return UserRole;
};