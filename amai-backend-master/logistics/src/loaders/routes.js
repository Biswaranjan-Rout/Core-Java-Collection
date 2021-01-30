const express = require('express');
const login = require('../routes/login');
const register = require('../routes/register');
const me = require('../routes/me');
const admin = require('../routes/admin');
const error = require('../middleware/error');

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
  app.use('/api/v1/admin', admin)
} 