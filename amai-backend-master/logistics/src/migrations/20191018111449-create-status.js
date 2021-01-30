'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('statuses', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      status_code: {
        allowNull: false,
        type: Sequelize.STRING(5)
      },
      status_description: {
        allowNull: false,
        type: Sequelize.STRING(100)
      },
      public_description: {
        allowNull: false,
        type: Sequelize.STRING(100)
      },
      status_reason: {
        allowNull: false,
        type: Sequelize.STRING(100)
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
    return queryInterface.dropTable('statuses');
  }
};