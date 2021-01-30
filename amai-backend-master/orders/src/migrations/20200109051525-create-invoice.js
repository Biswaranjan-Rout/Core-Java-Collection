'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Invoices', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      order_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull: false,
        references: {
          model: {
            tableName: "Orders",
            key: "id"
          }
        }
      },
      invoice_date: {
        type: Sequelize.DATE,
        allowNull:false
      },
      invoice_number:{
        type: Sequelize.STRING(16)
      },
      invoice_detail: {
        type: Sequelize.STRING,
        allowNull:false
      },
      invoice_status_code: {
        type: Sequelize.TINYINT.UNSIGNED,
        allowNull: false,
        references: {
          model: {
            tableName: "InvoiceStatuses",
            key: "id"
          }
        }
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
    return queryInterface.dropTable('Invoices');
  }
};