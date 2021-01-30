const express = require('express');
const checksum = require('../routes/checksum')
const merchant = require('../routes/merchant')

module.exports = function(app) {
  app.use(express.json());
  app.use((err, req, res, next) => {
    if (err instanceof SyntaxError && err.status === 400 && 'body' in err) {
        console.error(err);
        return res.status(400).send({ status: false, message: err.message }); // Bad request
    }
    next();
  });
  
  app.use('/api/v1/checksum', checksum);
  app.use('/api/v1/merchant', merchant)
  
} 