//Check if the limit and offset exist
module.exports = async function(req,res,next){
        if(!req.query.limit || !req.query.offset){
            return res
                    .status(400)
                    .send({error: { status:400, message:'Limit or offset missing.'}});

        }
        else{
            req.paginator = {
                limit: parseInt(req.query.limit), 
                offset: parseInt(req.query.offset)
            }
            next()
        }
        
    }
