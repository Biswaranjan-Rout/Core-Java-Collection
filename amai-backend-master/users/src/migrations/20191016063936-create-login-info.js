'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('LoginInfos', {
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
      mob_login_id: {
        allowNull:false,
        type: Sequelize.STRING(50)
      },
      login_status: {
        allowNull:false,
        type: Sequelize.ENUM,
        values:['logged_in','logged_out']
      },
      client_type: {
        allowNull:false,
        type: Sequelize.ENUM,
        values:['mobile_app','web_app']
      },
      client_version: {
        type: Sequelize.STRING(20)
      },
      session_id: {
        type: Sequelize.STRING(20)
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE
      }
    });
  },
  down: (queryInterface, Sequelize) => {
    return queryInterface.dropTable('LoginInfos');
  }
};