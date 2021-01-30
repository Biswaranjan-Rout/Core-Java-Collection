const fetch = require('node-fetch')
//configs
const { 
    PRODUCT_SERVICE,
    PERSONALIZE, READYMADE 
} = require("config");



const getProductById = async function(id, token) {
    try {
        const options = {
            method: "GET",
            headers: {
              Authorization: token
            }
          }
        const product = await fetch(
            PRODUCT_SERVICE + "products/" + id,
            options
        )
        const response = await product.json()
        console.log(response)
        return response.data.status?response.data:{}
    } catch(err) {
        return {}
    }
    
}

module.exports = {
    getProductById
}