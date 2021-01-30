'use strict';
const bcrypt = require('bcryptjs')
module.exports = (sequelize, DataTypes) => {
  const User = sequelize.define('User', {
    user_name: DataTypes.STRING,
    contact_number:DataTypes.STRING,
    email:DataTypes.STRING,
    is_active:DataTypes.TINYINT,
    password: DataTypes.STRING,
    is_email_verified: DataTypes.TINYINT,
    is_phone_verified: DataTypes.TINYINT,
    is_approved: DataTypes.TINYINT
  },{
      hooks: {
        beforeCreate: (user, options) => {
          if(user.password) { user.password = bcrypt.hashSync(user.password, 10) }
        },
        beforeUpdate: (user, options) => {
          if(user.password) { user.password = bcrypt.hashSync(user.password, 10) }
        }
      }
   });
  User.associate = function (models) {
    User.hasOne(models.PersonalDetail, { foreignKey: 'user_id' })
    User.hasOne(models.BusinessDetail, { foreignKey: 'user_id' })
    User.hasOne(models.StoreDetail, { foreignKey: 'user_id' })
    User.hasOne(models.BankDetail, { foreignKey: 'user_id' })
    User.hasOne(models.RefreshToken, { foreignKey: 'user_id' })
    User.hasOne(models.UserRole , { foreignKey: 'user_id' })
    User.hasMany(models.WishList, {foreignKey: 'user_id'})
    User.hasMany(models.Appointment, {foreignKey: 'user_id'})
    // User.belongsToMany(models.Role, { through: models.UserRole, foreignKey: 'user_id' })
  };
  return User;
};