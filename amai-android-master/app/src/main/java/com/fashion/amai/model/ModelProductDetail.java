package com.fashion.amai.model;

public class ModelProductDetail {
    private String  image;
    private String productContent;
    private String productDes;


    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String  image) {
        this.image = image;
    }

    public String getProductContent() {
        return productContent;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent;
    }

    public ModelProductDetail(String  image, String productContent , String productDes) {
        this.image = image;
        this.productContent = productContent;
        this.productDes = productDes;
    }


}
