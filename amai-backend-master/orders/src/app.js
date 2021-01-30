const express = require('express');
const app = express();
const  cors = require('cors')
//error handlers
const error = require('./middleware/error')
require('express-async-errors')
require('dotenv').config()


app.use(cors())


require('./loaders/routes')(app);
require('./loaders/config')();

//Handle 404 not found error
app.use(function(req, res, next) {
    return res.status(404).send({status:false, message: 'Url '+ req.url +' Not found.'});
});

//catch any error 
app.use(error)

module.exports = app
