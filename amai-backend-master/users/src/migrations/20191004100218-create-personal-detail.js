'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('PersonalDetails', {
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
      first_name: {
        type: Sequelize.STRING
      },
      last_name: {
        type: Sequelize.STRING,
      },
      contact_number: {
        allowNull: false,
        unique:true,
        type: Sequelize.STRING(15)
      },
      alternate_number: {
        type: Sequelize.STRING(15),
        unique:true
      },
      dob:{
        type: Sequelize.DATE,
      },
      gender:{
        type:Sequelize.ENUM,
        values: ['male', 'female', 'other']
      },
      email:{
        type:Sequelize.STRING,
      },
      profile_image:{
        type:Sequelize.STRING,
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
    return queryInterface.dropTable('PersonalDetails');
  }
};