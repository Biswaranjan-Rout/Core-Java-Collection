'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('shipmentItemInfos', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      shipment_id: {
        allowNull: false,
        type: Sequelize.INTEGER.UNSIGNED,
        unique:true
      },
      order_id: {
        allowNull: false,
        type: Sequelize.string(100),
        unique:true
      },
      item_price: {
        allowNull: false,
        type: Sequelize.DOUBLE
      },
      item_type: {
        allowNull: false,
        type: Sequelize.STRING(1000)
      },
      item_description: {
        type: Sequelize.STRING
      },
      invoice_id: {
        allowNull: false,
        type: Sequelize.string(80)
      },
      total_tax: {
        allowNull: false,
        type: Sequelize.DOUBLE,
        defaultValue:0,
      },
      item_contents: {
        type: Sequelize.STRING(1000)
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
    return queryInterface.dropTable('shipmentItemInfos');
  }
};