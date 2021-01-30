const WL = require("../models").WishList;

const fillWishListDetail = async (req, res) => {
  
    const result = await WL.create({
      user_id: req.user.id,
      item_type: req.body.item_type,
      item_id: req.body.item_id
    });
    return result.dataValues;
};

module.exports = fillWishListDetail;
