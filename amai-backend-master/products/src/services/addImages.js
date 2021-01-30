const ProductImage = require('../models').ProductImage
const _ = require('lodash')
const config = require('config')
const addImages = async (req, res) => {
    const data = []
    _.forEach(req.files, function(o) {
        let image_url = o.location
        data.push({product_id: req.params.id, image: image_url, variant: "red" })
    })
    
    try {
        const addedImages = await ProductImage.bulkCreate(data, { returning: true})
        console.log(addImages)
        return addedImages
    } catch (e) {
        console.log(e)
        return res.status(422).send({ error: { status: 422, "message": e } })
    }
}
module.exports = addImages