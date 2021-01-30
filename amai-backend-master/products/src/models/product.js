'use strict';
module.exports = (sequelize, DataTypes) => {
  const Product = sequelize.define('Product', {
    product_name: DataTypes.STRING,
    product_description:DataTypes.STRING,
    product_code: DataTypes.STRING,
    category: DataTypes.STRING,
    material_detail: DataTypes.STRING,
    supplier_id:DataTypes.INTEGER.UNSIGNED,
    is_deleted: DataTypes.TINYINT.UNSIGNED,
    sleeve: DataTypes.STRING,
    ideal_for:{
      type:DataTypes.ENUM,
      values: ['men', 'women', 'all']
    },
    suitable_for: DataTypes.STRING,
    pattern: DataTypes.STRING,
    type: DataTypes.STRING,
    pack_of: DataTypes.TINYINT.UNSIGNED,
    occasion:DataTypes.STRING,
    fabric: DataTypes.STRING,
    status:DataTypes.TINYINT.UNSIGNED,
    product_type: DataTypes.TINYINT.UNSIGNED,
    is_approved: DataTypes.TINYINT.UNSIGNED,
    deliver_by: DataTypes.STRING

  }, {});
  Product.associate = function(models) {
   Product.hasOne(models.ProductQuantity, { foreignKey: 'product_id' })
   Product.hasOne(models.ProductPrice, { foreignKey: 'product_id' })
   Product.hasMany(models.ProductImage, { foreignKey: 'product_id' }),
   Product.hasOne(models.ProductQCStatus, { foreignKey:'product_id'})
  };
  return Product;
};