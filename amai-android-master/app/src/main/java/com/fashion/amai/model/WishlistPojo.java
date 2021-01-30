package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistPojo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private WishlistData data;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public WishlistData getData() {
        return data;
    }

    public class WishlistData{
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("item_type")
        @Expose
        private String itemType;
        @SerializedName("item_id")
        @Expose
        private Integer itemId;
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

        public String getItemType() {
            return itemType;
        }

        public Integer getItemId() {
            return itemId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }

}
