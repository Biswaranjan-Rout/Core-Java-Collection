package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class BookAppointment {
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
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("apn_id")
        @Expose
        private String apnId;
        @SerializedName("booked_date")
        @Expose
        private Date bookedDate;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("slot")
        @Expose
        private String slot;
        @SerializedName("status")
        @Expose
        private Integer status;

        public String getSlot() {
            return slot;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getApnId() {
            return apnId;
        }

        public Date getBookedDate() {
            return bookedDate;
        }

        public String getProductId() {
            return productId;
        }

        public String getAddress() {
            return address;
        }

        public Integer getStatus() {
            return status;
        }
    }
}
