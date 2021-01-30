'use strict';
module.exports = (sequelize, DataTypes) => {
  const Order = sequelize.define('Order', {
    buyer_id: DataTypes.INTEGER.UNSIGNED,
    supplier_id: DataTypes.INTEGER.UNSIGNED,
    order_status_code: DataTypes.TINYINT.UNSIGNED,
    order_detail: DataTypes.TEXT,
    order_date: DataTypes.DATE,
    order_number: DataTypes.STRING(20),
    order_type: DataTypes.TINYINT.UNSIGNED,
    product_id: DataTypes.INTEGER.UNSIGNED,
    product_quantity:  DataTypes.TINYINT.UNSIGNED,
    product_price: DataTypes.INTEGER.UNSIGNED,
    product_discount_per: DataTypes.FLOAT,
    product_size : DataTypes.STRING(3)
  }, {
    hooks:{
      afterCreate:  async function(order, options) {
      await order.update(
        {
          order_number : 'OD' + Date.now() + order.id + order.product_id + order.buyer_id
        })
      }
    }
   });
  Order.associate = function(models) {
    // associations can be defined here
    Order.belongsTo(models.OrderStatus, {foreignKey: "order_status_code"})
  };
  return Order;
};