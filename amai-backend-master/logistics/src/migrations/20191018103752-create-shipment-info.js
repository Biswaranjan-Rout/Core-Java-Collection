'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('shipmentInfos', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      shipping_from_customer_id: {
        allowNull: false,
        type: Sequelize.INTEGER.UNSIGNED
      },
      delivery_customer_id: {
        allowNull: false,
        type: Sequelize.INTEGER
      },
      recieved_by_customer_id: {
        allowNull: false,
        type: Sequelize.INTEGER.UNSIGNED
      },
      pin_code: {
        allowNull: false,
        type: Sequelize.INTEGER.UNSIGNED
      },
      shipment_type: {
        allowNull: false,
        type: Sequelize.ENUM,
        values:["PP","COD"]
      },
      total_price: {
        allowNull:false,
        type: Sequelize.FLOAT,
        defualtValue:0
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
    return queryInterface.dropTable('shipmentInfos');
  }
};