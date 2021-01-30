'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('StoreDetails', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      user_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        unique:true,
        references: {
          model: {
            tableName: 'Users',
            key: 'id',
          }
        },
        allowNull: false
      },
      store_name:{
        allowNull:false,
        type: Sequelize.STRING
      },
      address1: {
        allowNull: false,
        type: Sequelize.TEXT
      },
      address2: {
        allowNull: false,
        type: Sequelize.TEXT
      },
      district: {
        allowNull: false,
        type: Sequelize.STRING
      },
      pin: {
        allowNull: false,
        type: Sequelize.INTEGER(6).UNSIGNED
      },
      store_image:{
        allowNull: false,
        type: Sequelize.STRING
      },
      document:{
        type: Sequelize.STRING
      },
      created_at: {
        type: 'TIMESTAMP',
        defaultValue: Sequelize.literal('CURRENT_TIMESTAMP'),
        allowNull: false,
      },
      updated_at: {
        type: 'TIMESTAMP',
        defaultValue: Sequelize.literal('CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP'),
        allowNull: false
      }
    });
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.dropTable('StoreDetails');
  }
};