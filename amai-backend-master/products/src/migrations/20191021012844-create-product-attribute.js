'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('ProductAttributes', {
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
      size: {
        type: Sequelize.CHAR(1)
      },
      quantity: {
        type: Sequelize.INTEGER.UNSIGNED
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
    return queryInterface.dropTable('ProductAttributes');
  }
};