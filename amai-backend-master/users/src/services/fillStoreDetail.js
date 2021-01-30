const SD = require('../models').StoreDetail
const config = require('config')

const fillStoreDetail = async (req, res) => {
        const result = await SD.create({
            user_id: req.user.id,
            store_name: req.body.store_name,
            address1: req.body.address1,
            address2: req.body.address2,
            district:req.body.district,
            pin: req.body.pin,
            store_image: req.files.image[0].location,
            document: req.files.document[0].location,
        })
        return result.dataValues
    
}
module.exports = fillStoreDetail