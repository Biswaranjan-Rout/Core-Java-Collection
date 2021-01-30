'use strict';
module.exports = (sequelize, DataTypes) => {
  const Appointment = sequelize.define('Appointment', {
    user_id: DataTypes.INTEGER.UNSIGNED,
    apn_id: DataTypes.STRING,
    slot: DataTypes.STRING,
    product_id: DataTypes.STRING,
    address: DataTypes.TEXT,
    booked_date: DataTypes.DATE,
    status: DataTypes.TINYINT,
    agn_name: DataTypes.STRING,
    agn_contact: DataTypes.STRING
  }, {});
  Appointment.associate = function(models) {
    // associations can be defined here
  };
  return Appointment;
};