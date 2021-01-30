'use strict';
module.exports = (sequelize, DataTypes) => {
  const WishList = sequelize.define('WishList', {
    user_id: DataTypes.INTEGER,
    item_type: DataTypes.STRING,
    item_id: DataTypes.INTEGER
  }, {});
  WishList.associate = function(models) {
    // associations can be defined here
  };
  return WishList;
};