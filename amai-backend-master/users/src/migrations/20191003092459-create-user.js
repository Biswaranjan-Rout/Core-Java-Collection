'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Users', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER.UNSIGNED
      },
      user_name: {
        allowNull: false,
        unique:true,
        type: Sequelize.STRING
      },
      email:{
        allowNull: false,
        unique:true,
        type: Sequelize.STRING(60)
      },
      contact_number:{
        allowNull: false,
        unique:true,
        type: Sequelize.STRING(15)
      },
      password: {
        allowNull: false,
        type: Sequelize.STRING
      },

      is_active: {
        allowNull: false,
        type: Sequelize.TINYINT(1),
        defaultValue:1,
        comment:'active=>1, inactive=>0'
      },
      created_at: {
        type: 'TIMESTAMP',
        defaultValue: Sequelize.literal('CURRENT_TIMESTAMP'),
        allowNull: false
      },
      is_email_verified: {
        type: Sequelize.BOOLEAN,
        defaultValue: false
      },
      is_phone_verified: {
        type: Sequelize.BOOLEAN,
        defaultValue: false
      },
      is_approved: {
        type: Sequelize.BOOLEAN,
        defaultValue: false
      },
      updated_at: {
        type: 'TIMESTAMP',
        defaultValue: Sequelize.literal('CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP'),
        allowNull: false
      }
    });
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.dropTable('Users');
  }
};