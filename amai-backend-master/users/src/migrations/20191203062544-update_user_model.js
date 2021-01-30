"use strict";

module.exports = {
  up: (queryInterface, Sequelize) => {
    return  Promise.all([
      queryInterface.addColumn("Users", "is_phone_verified", {
        type: Sequelize.BOOLEAN
      }),
      queryInterface.addColumn("Users", "is_email_verified", {
        type: Sequelize.BOOLEAN
      }),
      queryInterface.addColumn("Users", "is_approved", {
        type: Sequelize.BOOLEAN
      })
    ]);
  },

  down: (queryInterface, Sequelize) => {
    /*
      Add reverting commands here.
      Return a promise to correctly handle asynchronicity.

      Example:
      return queryInterface.dropTable('users');
    */
  }
};
