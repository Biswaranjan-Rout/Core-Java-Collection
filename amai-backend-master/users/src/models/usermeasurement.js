'use strict';
module.exports = (sequelize, DataTypes) => {
  const UserMeasurement = sequelize.define('UserMeasurement', {
    user_id:DataTypes.INTEGER.UNSIGNED,
    hip_round: DataTypes.FLOAT,
    nape_to_waist: DataTypes.FLOAT,
    armhale_depth: DataTypes.FLOAT,
    shoulder_length: DataTypes.FLOAT,
    shoulder_pain_to_paint: DataTypes.FLOAT,
    bicep_round: DataTypes.FLOAT,
    elbow_round: DataTypes.FLOAT,
    wrist_round: DataTypes.FLOAT,
    height: DataTypes.FLOAT,
    bust_round: DataTypes.FLOAT,
    waist_round: DataTypes.FLOAT,
    high_hip_round: DataTypes.FLOAT
  }, {});
  UserMeasurement.associate = function(models) {
    // associations can be defined here
  };
  return UserMeasurement;
};