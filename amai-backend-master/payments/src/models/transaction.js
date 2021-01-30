'use strict';
module.exports = (sequelize, DataTypes) => {
  const Transaction = sequelize.define('Transaction', {
    order_number: DataTypes.STRING,
    txn_id: DataTypes.STRING,
    bank_txn_id: DataTypes.STRING,
    txn_amount: DataTypes.FLOAT,
    txn_status: DataTypes.STRING,
    txn_type: DataTypes.STRING,
    txn_date: DataTypes.DATE,
    buyer_id: DataTypes.INTEGER.UNSIGNED,
    supplier_id: DataTypes.INTEGER.UNSIGNED,
    gateway: DataTypes.STRING,
    res_code: DataTypes.STRING,
    res_msg: DataTypes.STRING,
    bank_name: DataTypes.STRING,
    pay_mode: DataTypes.STRING,
    refund_amt: DataTypes.FLOAT
  }, {});
  Transaction.associate = function(models) {
    // associations can be defined here
  };
  return Transaction;
};