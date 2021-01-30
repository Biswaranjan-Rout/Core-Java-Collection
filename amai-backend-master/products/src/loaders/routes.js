const express = require('express');
const products = require('../routes/products');
const vendor = require('../routes/vendor');
const merchants = require('../routes/merchants');
const admin = require('../routes/admin')

module.exports = function(app) {
  app.use(express.json());
  app.use((err, req, res, next) => {
    if (err instanceof SyntaxError && err.status === 400 && 'body' in err) {
        console.error(err);
        return res.status(400).send({ status: 404, message: err.message }); // Bad request
    }
    next();
  });
  
  //Routes for Products
  app.use('/api/v1/products', products);
  
  //Routes for user's products
  app.use('/api/v1/user', vendor);

  //Routes for merchant's products
  app.use('/api/v1/merchants',merchants);

  //Product Management routes for admin
  app.use('/api/v1/product-management',admin);
} 