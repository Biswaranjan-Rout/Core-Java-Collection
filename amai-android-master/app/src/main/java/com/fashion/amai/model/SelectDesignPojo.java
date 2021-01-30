package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectDesignPojo extends BaseModel {

    @SerializedName("data")
    @Expose
    private DesignData data;

    public DesignData getData() {
        return data;
    }

    public class DesignData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("product_id")
        @Expose
        private String product_id;
        @SerializedName("updated_at")
        @Expose
        private String updated_at;
        @SerializedName("created_at")
        @Expose
        private String created_at;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }
    }



}
