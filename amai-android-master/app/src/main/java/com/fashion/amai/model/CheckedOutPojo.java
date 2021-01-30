package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckedOutPojo {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_discount")
    @Expose
    private Double totalDiscount;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data{
        @SerializedName("product_id")
        @Expose
        private int product_id;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("supplier_id")
        @Expose
        private Integer supplierId;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("net_price")
        @Expose
        private Integer netPrice;
        @SerializedName("discount")
        @Expose
        private Double discount;

        public String getProductName() {
            return productName;
        }

        public Integer getSupplierId() {
            return supplierId;
        }

        public String getProductImage() {
            return productImage;
        }

        public Integer getPrice() {
            return price;
        }

        public Integer getNetPrice() {
            return netPrice;
        }

        public int getProduct_id() {
            return product_id;
        }

        public Double getDiscount() {
            return discount;
        }
    }
}
