'use strict';
module.exports = (sequelize, DataTypes) => {
  const StoreDetail = sequelize.define('StoreDetail', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    store_name: DataTypes.STRING,
    address1: DataTypes.TEXT,
    address2: DataTypes.TEXT,
    district: DataTypes.STRING,
    pin: DataTypes.INTEGER,
    store_image: DataTypes.STRING,
    document: DataTypes.STRING
  });
  StoreDetail.associate = function(models) {
    // associations can be defined here
    StoreDetail.belongsTo(models.User, { foreignKey: 'user_id' })
  };
  return StoreDetail;
};