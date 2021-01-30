'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Products', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      product_name: {
        allowNull: false,
        type: Sequelize.STRING
      },
      product_type:{
        type: Sequelize.TINYINT(1).UNSIGNED,
        allowNull: false,
        comment:"1 -> personalize, 2-> readymade"
      },
      product_description: {
        allowNull: false,
        type: Sequelize.STRING
      },
      product_code: {
        allowNull: false,
        type: Sequelize.STRING(50)
      },
      supplier_id:{
        allowNull:false,
        type:Sequelize.INTEGER.UNSIGNED
      },
      category:{
        type: Sequelize.STRING
      },
      material_detail:{
        type: Sequelize.TEXT,
      },
      is_deleted:{
        type: Sequelize.TINYINT(1),
        defaultValue:0
      },
      sleeve:{
        type: Sequelize.STRING
      },
      ideal_for:{
        type:Sequelize.ENUM,
        values: ['men', 'women', 'all']
      },
      suitable_for:{
        type: Sequelize.STRING
      },
      pattern:{
        type: Sequelize.STRING
      },
      type:{
        type: Sequelize.STRING
      },
      pack_of:{
        type: Sequelize.TINYINT(1).UNSIGNED
      },
      occasion:{
        type: Sequelize.STRING
      },
      status:{
        type: Sequelize.TINYINT(1).UNSIGNED,
        defaultValue:0,
        comment:"0 -> Inactive , 1-> active, 2-> out_of_stock"
      },
      is_approved:{
        type: Sequelize.TINYINT(1).UNSIGNED,
        defaultValue:0,
        comment: "1 -> aprroved, 0 -> unapproved"
      },
      fabric:{
        type: Sequelize.STRING
      },
      deliver_by: {
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
    return queryInterface.dropTable('Products');
  }
};