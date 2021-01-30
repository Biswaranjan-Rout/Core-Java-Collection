'use strict';
module.exports = {
  up: (queryInterface, Sequelize) => {
    return queryInterface.createTable('Appointments', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      user_id: {
        type: Sequelize.INTEGER.UNSIGNED,
        references: {
          model: {
            tableName: 'Users',
            key: 'id',
          }
        },
        allowNull: false
      },
      apn_id: {
        type: Sequelize.STRING(15),
        unique: true,
        allowNull: false
      },
      slot: {
        type: Sequelize.STRING(20)
      },
      product_id: {
        type: Sequelize.STRING(60),
        allowNull:false
      },
      address: {
        type: Sequelize.TEXT,
        allowNull:false
      },
      booked_date: {
        type: Sequelize.DATE,
        allowNull: false
      },
      status: {
        type: Sequelize.TINYINT,
        allowNull: false,
        comment : "1->Booked, 2->Cancelled, 3->Rescheduled, 4->resolved"
      },
      agn_name: {
        type: Sequelize.STRING(20)
      },
      agn_contact: {
        type: Sequelize.STRING(15)
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
    return queryInterface.dropTable('Appointments');
  }
};