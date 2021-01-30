'use strict';
module.exports = (sequelize, DataTypes) => {
  const BusinessDetail = sequelize.define('BusinessDetail', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    gstin: {
      type:DataTypes.STRING,
      allowNull: false,
      unique: {
        args: true,
        msg: 'Entered GSTIN is already in use.'
     }
      },
    company_name: DataTypes.TEXT,
    pan: DataTypes.STRING,
    primary_category: DataTypes.STRING,
    year_of_operation:DataTypes.STRING
  }, {
    hooks: {
      afterValidate: (data, options) => {
        data.gstin = data.gstin.toUpperCase()
        data.pan = data.pan.toUpperCase()
      }
    }
  });
  BusinessDetail.associate = function(models) {
    BusinessDetail.belongsTo(models.User, { foreignKey: 'user_id' })
  };
  return BusinessDetail;
};