package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivitySchedulePojo {
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

    private class Data{
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("slot")
        @Expose
        private String slot;
        @SerializedName("booked_date")
        @Expose
        private String bookedDate;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("apn_id")
        @Expose
        private String apnId;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getId() {
            return id;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getAddress() {
            return address;
        }

        public String getSlot() {
            return slot;
        }

        public String getBookedDate() {
            return bookedDate;
        }

        public String getProductId() {
            return productId;
        }

        public String getApnId() {
            return apnId;
        }

        public Integer getStatus() {
            return status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }
}
