
const multer = require('multer')
const aws = require('aws-sdk');
const multerS3 = require('multer-s3');
const config = require('config')
const customError = require('../services/customError')
const crypto = require('crypto')

aws.config.update({
	secretAccessKey: config.get("aws_secret_access"),
	accessKeyId: config.get("aws_access_key")
});

const s3 = new aws.S3()

  
//accept only jpeg and png files
const fileFilter = (req, file, cb) => {
	// path.extension(file.originalname) !== '.pdf'
	if (file.mimetype === 'image/jpeg' || file.mimetype === 'image/png' || file.mimetype === 'application/pdf') {
		cb(null, true)
	} else {
		cb(new customError('Please upload a JPEG, PNG or PDF file!', 400), false);
	}
  }

const upload = multer({
	fileFilter,
	limits: {
		fileSize: 1024 * 1024 *5
	},
	storage: multerS3({
	  s3:s3,
	  bucket: config.get("bucket"),
	  acl: 'public-read',
	  key: function (req, file, cb) {
		crypto.pseudoRandomBytes(16, function (err, raw) {
		  cb(null, raw.toString('hex') + Date.now() + (file.originalname).toLocaleLowerCase());
		});
	  }
	})
  })
  
module.exports  = upload