package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MyDesignsModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data{
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("apn_id")
        @Expose
        private String apnId;
        @SerializedName("booked_date")
        @Expose
        private Date bookedDate;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_image")
        @Expose
        private String productImage;

        public String getProductId() {
            return productId;
        }

        public String getApnId() {
            return apnId;
        }

        public Date getBookedDate() {
            return bookedDate;
        }

        public Integer getStatus() {
            return status;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductImage() {
            return productImage;
        }
    }
}
