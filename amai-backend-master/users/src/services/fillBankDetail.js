const BD = require('../models').BankDetail

const fillBankDetail = async (req, res) => {

    try{
        const result = await BD.create({
            user_id: req.user.id,
            name: req.body.name,
            account_no: req.body.account_no,
            ifsc: req.body.ifsc,
        })
        return result.dataValues
    } catch (e) {
       throw(e)
    }
}
module.exports = fillBankDetail