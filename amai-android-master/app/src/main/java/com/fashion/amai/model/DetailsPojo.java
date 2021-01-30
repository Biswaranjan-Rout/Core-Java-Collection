package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsPojo {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public class Data {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("store_image")
        @Expose
        private String storeImage;
        @SerializedName("company_name")
        @Expose
        private String ownerName;
        @SerializedName("rating")
        @Expose
        private String rating;
        @SerializedName("decription")
        @Expose
        private String decription;
        @SerializedName("is_wishlisted")
        @Expose
        private boolean is_wishlisted;

        public boolean isIs_wishlisted() {
            return is_wishlisted;
        }

        public Integer getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public String getStoreImage() {
            return storeImage;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public String getRating() {
            return rating;
        }

        public String getDecription() {
            return decription;
        }
    }


}
