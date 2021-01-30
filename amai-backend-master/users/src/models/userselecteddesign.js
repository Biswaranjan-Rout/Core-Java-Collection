'use strict';
module.exports = (sequelize, DataTypes) => {
  const UserSelectedDesign = sequelize.define('UserSelectedDesign', {
    user_id: DataTypes.INTEGER.UNSIGNED,
    product_id:DataTypes.INTEGER.UNSIGNED
  }, {});
  UserSelectedDesign.associate = function(models) {
    // associations can be defined here
  };
  return UserSelectedDesign;
};