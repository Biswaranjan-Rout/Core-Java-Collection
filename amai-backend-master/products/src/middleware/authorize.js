module.exports = function authorize(roles) {
  return async function(req, res, next) {
    if (!req.user.role || !roles.includes(req.user.role)) {
      return res.status(403).send({status:false, message:'Access denied.'});
    }
    next();
  }
}