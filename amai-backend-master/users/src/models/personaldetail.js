'use strict';
module.exports = (sequelize, DataTypes) => {
  const PersonalDetail = sequelize.define('PersonalDetail', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    first_name: DataTypes.STRING,
    last_name: DataTypes.STRING,
    contact_number: DataTypes.STRING,
    alternate_number: DataTypes.STRING,
    dob:DataTypes.DATE,
    gender:{
      type:DataTypes.ENUM,
      values: ['male', 'female', 'other']
    },
    email:DataTypes.STRING,
    profile_image:DataTypes.STRING
  });
  PersonalDetail.associate = function(models) {
    PersonalDetail.belongsTo(models.User, { foreignKey: 'user_id' })
  };
  return PersonalDetail;
};