'use strict';
module.exports = (sequelize, DataTypes) => {
  const BankDetail = sequelize.define('BankDetail', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    name: DataTypes.STRING,
    account_no: DataTypes.STRING,
    ifsc: DataTypes.STRING
  }, {
    hooks: {
      afterValidate: (bankdetail, options) => {
        bankdetail.name = bankdetail.name.toUpperCase()
        bankdetail.ifsc = bankdetail.ifsc.toUpperCase()
      }
    }
  });
  BankDetail.associate = function(models) {
    // associations can be defined here
    BankDetail.belongsTo(models.User, { foreignKey: 'user_id' })

  };
  return BankDetail;
};