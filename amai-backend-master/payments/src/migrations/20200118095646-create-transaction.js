'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Transactions', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      txn_id:{
        type: Sequelize.STRING(50)
      },
      bank_txn_id: {
        type: Sequelize.STRING(50)
      },
      order_number: {
        type: Sequelize.STRING(50),
        allowNull:false
      },
      txn_amount:{
        allowNull:false,
        type:Sequelize.FLOAT,
        defaultValue:0
      },
      txn_status: {
        allowNull:false,
        type:Sequelize.STRING(50)
      },
      txn_type: {
        type:Sequelize.STRING(50)
      },
      txn_date: {
        type:Sequelize.DATE,
        allowNull:false
      },
      buyer_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull:false
      },
      supplier_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        allowNull:false
      },
      gateway:{
        type:Sequelize.STRING(50)
      },
      res_code:{
        type:Sequelize.STRING(10)
      },
      res_msg: {
        type:Sequelize.TEXT
      },
      bank_name: {
        type:Sequelize.STRING(100)
      },
      pay_mode:{
        type:Sequelize.STRING(50)
      },
      refund_amt: {
        allowNull:false,
        type:Sequelize.FLOAT,
        defaultValue:0
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
    return queryInterface.dropTable('Transactions');
  }
};