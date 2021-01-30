
'use strict'

class CustomError extends Error {
    constructor(message = 'Something went wrong!', status) {
        super(message)
  
      this.name = 'CustomError';
      // Custom debugging information
      this.message = message;
      this.status = status;
    }
  }
  
  module.exports = CustomError