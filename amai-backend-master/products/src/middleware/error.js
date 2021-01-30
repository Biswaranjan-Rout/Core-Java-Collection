
const logger = require('../services/logger')
const customError = require('../services/customError')
const db = require('sequelize')

module.exports = function (err, req, res, next) {

  // Do logging and user-friendly error message display
  logger.log({
    level: 'error',
    message: err.message,
    stack: err.stack
  })

  //catch thrown custom errors
  if (err instanceof customError) {
    return res.status(err.status).send({status: false, message: err.message });
  }
  //catch all sequelize errors
  if (err instanceof db.ConnectionRefusedError) {
    return res.status(500).send({status: false, message: "Could not connect to database!"})
  } else if(err instanceof db.ConnectionTimedOutError){
    return res.status(500).send({status: false, message: "Connection timed out!"})
  } else if(err instanceof db.DatabaseError) {
    return res.status(400).send({status: false, message: err.message })
  } else if(err instanceof db.ForeignKeyConstraintError) {
    return res.status(500).send({status: false, message : "Failed to insert data!"})
  } else if(err instanceof db.UniqueConstraintError) {
   return res.status(400).send({status: false, message: err.errors[0].message})
  }
  console.log(err)
  //catch  all errors
  return res.status(500).send({status: false, message: "Something went wrong!"});
}

