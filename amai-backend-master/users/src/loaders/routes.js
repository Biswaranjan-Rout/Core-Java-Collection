const express = require('express');
const login = require('../routes/login');
const register = require('../routes/register');
const me = require('../routes/me');
const admin = require('../routes/admin');
const users = require('../routes/users')
const boutique = require('../routes/boutique')
const designer = require('../routes/designer')
const otp = require('../routes/otp')
const token = require('../routes/token')

const vendor = require('../routes/vendor')
const merchant = require('../routes/merchant')

module.exports = function(app) {
  app.use(express.json());
  app.use((err, req, res, next) => {
    if (err instanceof SyntaxError && err.status === 400 && 'body' in err) {
        console.error(err);
        return res.status(400).send({ status: 404, message: err.message }); // Bad request
    }
    next();
  });
  
  app.use('/api/v1/login', login);
  app.use('/api/v1/register', register);
  app.use('/api/v1/me', me);


  //Admin Routes
  app.use('/api/v1', admin)

  //Route for users
  app.use('/api/v1/users', users)

  //Route for boutique
  app.use('/api/v1/boutiques', boutique)

  //Route for designers
  app.use('/api/v1/designers', designer) 

  //Route for merchant
  app.use('/api/v1/merchants', merchant)
  
  //Route for SMS
  app.use('/api/v1/otp', otp)

  //Route for token
  app.use('/api/v1/token', token)
} 