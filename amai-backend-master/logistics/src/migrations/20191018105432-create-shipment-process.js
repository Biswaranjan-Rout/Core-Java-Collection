'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('shipmentProcesses', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      shipment_id: {
        allowNull: false,
        type: Sequelize.STRING(50)
      },
      agent_id: {
        allowNull: false,
        type: Sequelize.INTEGER.UNSIGNED
      },
      status_id: {
        allowNull: false,
        type: Sequelize.INTEGER.UNSIGNED
      },
      cs_notes: {
        allowNull: false,
        type: Sequelize.STRING(200)
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
    return queryInterface.dropTable('shipmentProcesses');
  }
};