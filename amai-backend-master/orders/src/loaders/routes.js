const express = require('express');
const orders = require('../routes/orders')
const merchant = require('../routes/merchant')
const user = require('../routes/user')


module.exports = function(app) {
  app.use(express.json());
  app.use((err, req, res, next) => {
    if (err instanceof SyntaxError && err.status === 400 && 'body' in err) {
        console.error(err);
        return res.status(400).send({ status: false, message: err.message }); // Bad request
    }
    next();
  });

  app.use('/api/v1/orders', orders)
  app.use('/api/v1/merchant', merchant)
  app.use('/api/v1/user', user)
} 