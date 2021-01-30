'use strict';

module.exports = {
  up: (queryInterface, Sequelize) => {
      return queryInterface.bulkInsert('Roles', [{
       id: 1,
       role_type: 'user',
       is_active: 1,
       created_at: new Date(),
       updated_at: new Date()
      },{
        id: 2,
       role_type: 'admin',
       is_active: 1,
       created_at: new Date(),
       updated_at: new Date()
      },{
        id: 3,
       role_type: 'boutique',
       is_active: 1,
       created_at: new Date(),
       updated_at: new Date()
      },{
        id: 4,
       role_type: 'designer',
       is_active: 1,
       created_at: new Date(),
       updated_at: new Date()
      },{
        id: 5,
       role_type: 'design-house',
       is_active: 1,
       created_at: new Date(),
       updated_at: new Date()
      }], {});
  },

  down: (queryInterface, Sequelize) => {
    /*
      Add reverting commands here.
      Return a promise to correctly handle asynchronicity.

      Example:
      return queryInterface.bulkDelete('People', null, {});
    */
  }
};
