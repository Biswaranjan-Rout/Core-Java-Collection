package com.fashion.amai.model;

public class AddToCartProduct {
    private String product_name;
    private String quantity;
    private String size;
    private int price;
    private int product_id;
    private String product_url;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public AddToCartProduct() {
    }

    public AddToCartProduct(String product_name, String quantity, String size, int price, int product_id, String product_url) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.product_id = product_id;
        this.product_url = product_url;
    }
}
