const winston = require('winston')
const logform = require('logform')

//custom format for winston log to store stack trace
const winstonConsoleFormat = logform.format.combine(
  logform.format.printf(info => ` ${info.level}: ${info.message}: ${info.stack}`)
); 
const logger = winston.createLogger({
    level: 'info',
    format: winston.format.json(),
    transports: [
      //
      // - Write to all logs with level `info` and below to `combined.log` 
      // - Write all logs error (and below) to `error.log`.
      //
      new winston.transports.File({ filename: 'error.log', level: 'error',format: winstonConsoleFormat }),
      new winston.transports.File({ filename: 'combined.log' }),
    ]
  });
   
 
 

  module.exports = logger