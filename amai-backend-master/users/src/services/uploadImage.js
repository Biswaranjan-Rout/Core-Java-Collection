const config = require('config')

aws.config.updatee({
    secretAccessKey: config.get("aws_secret_acess"),
    accessKeyId: config.get("aws"),
    region: config.get("region")
})

const s3 = new aws.S3()

const upload = multer({
    storage: multerS3({
        s3: s3,
        bucket: config.get('bucket'),
        metadata: function(req, file, cb) {
            cb(null, { fileName: 'TESTING_META_DATA'})
        },
        key: function(req, file, cb) {
            cb(null, Date.now().toString())
        }
    })
})

module.exports = upload