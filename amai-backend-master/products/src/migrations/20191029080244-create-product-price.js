'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('ProductPrices', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      product_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        unique:true,
        references: {
          model: {
            tableName: 'Products',
            key: 'id',
          }
        },
        allowNull: false
      },
      s: {
        type: Sequelize.INTEGER.UNSIGNED
      },
      m: {
        type: Sequelize.INTEGER.UNSIGNED
      },
      l: {
        type: Sequelize.INTEGER.UNSIGNED
      },
      xl: {
        type: Sequelize.INTEGER.UNSIGNED
      },
      discount:{
        allowNull: false,
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
    return queryInterface.dropTable('ProductPrices');
  }
};