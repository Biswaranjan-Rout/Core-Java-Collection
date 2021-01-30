'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('BusinessDetails', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      user_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        unique:true,
        references: {
          model: {
            tableName: 'Users',
            key: 'id',
          }
        },
        allowNull: false
      },
      gstin: {
        type: Sequelize.STRING(15),
        unique:true
      },
      company_name: {
        allowNull: false,
        type: Sequelize.STRING
      },
      pan: {
        allowNull: false,
        type: Sequelize.STRING(10)
      },
      year_of_operation:{
        allowNull: false,
        type: Sequelize.STRING
      },
      primary_category: {
        allowNull: false,
        type: Sequelize.STRING
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
    return queryInterface.dropTable('BusinessDetails');
  }
};