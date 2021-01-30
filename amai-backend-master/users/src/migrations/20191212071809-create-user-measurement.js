'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('UserMeasurements', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      user_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        references: {
          model: {
            tableName: 'Users',
            key: 'id',
          }
        },
        allowNull: false
      },
      hip_round: {
        type: Sequelize.FLOAT
      },
      nape_to_waist: {
        type: Sequelize.FLOAT
      },
      armhale_depth: {
        type: Sequelize.FLOAT
      },
      shoulder_length: {
        type: Sequelize.FLOAT
      },
      shoulder_pain_to_paint: {
        type: Sequelize.FLOAT
      },
      bicep_round: {
        type: Sequelize.FLOAT
      },
      elbow_round: {
        type: Sequelize.FLOAT
      },
      wrist_round: {
        type: Sequelize.FLOAT
      },
      height: {
        type: Sequelize.FLOAT
      },
      bust_round: {
        type: Sequelize.FLOAT
      },
      waist_round: {
        type: Sequelize.FLOAT
      },
      high_hip_round: {
        type: Sequelize.FLOAT
      },
      created_at: {
        type: 'TIMESTAMP',
        defaultValue: Sequelize.literal('CURRENT_TIMESTAMP'),
        allowNull: false
      },
      updated_at: {
        type: 'TIMESTAMP',
        defaultValue: Sequelize.literal('CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP'),
        allowNull: false
      }
    });
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.dropTable('UserMeasurements');
  }
};