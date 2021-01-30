const BD = require('../models').BusinessDetail

const fillBusinessDetail = async (req,res) => {

    try{
        const result = await BD.create({
            user_id: req.user.id,
            gstin: req.body.gstin,
            pan: req.body.pan,
            company_name: req.body.company_name,
            primary_category: req.body.primary_category,
            year_of_operation: req.body.year_of_operation
        })
        return result.dataValues
    } catch (e) {
        console.log(e)
        return res.status(422).send({status: false, "message": e.errors[0].message})
    }
}
   
    module.exports = fillBusinessDetail