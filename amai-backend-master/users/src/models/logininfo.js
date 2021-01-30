'use strict';
module.exports = (sequelize, DataTypes) => {
  const loginInfo = sequelize.define('loginInfo', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    mob_login_id: DataTypes.STRING,
    login_status:{
      type: DataTypes.ENUM,
      values:['logged_in','logged_out']
    },
    client_type:{
      type: DataTypes.ENUM,
      values:['mobile_app','web_app']
    },
    client_version: DataTypes.STRING,
    session_id: DataTypes.STRING
  }, {});
  loginInfo.associate = function(models) {
    // associations can be defined here
  };
  return loginInfo;
};