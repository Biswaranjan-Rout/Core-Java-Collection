'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Orders', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      buyer_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull:false
      },
      supplier_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull:false
      },
      order_number:{
        type: Sequelize.STRING(20)
      },
      order_status_code: {
        type: Sequelize.TINYINT.UNSIGNED,
        allowNull: false,
        references: {
          model: {
            tableName: "OrderStatuses",
            key: "id"
          }
        }
      },
      order_detail: {
        type: Sequelize.TEXT
      },
      order_date: {
        type: Sequelize.DATE,
        allowNull:false
      },
      order_type: {
        type: Sequelize.TINYINT.UNSIGNED,
        allowNull:false,
        comment: "1=>persnalize, 2=> readymade"
      },
      product_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull:false
      },
      product_quantity:{
        type: Sequelize.TINYINT.UNSIGNED,
        allowNull:false
      },
      product_price:{
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull:false
      },
      product_discount_per:{
        type: Sequelize.FLOAT,
        allowNull:false
      },
      product_size: {
        type: Sequelize.STRING(3),
        allowNull:false
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
    return queryInterface.dropTable('Orders');
  }
};