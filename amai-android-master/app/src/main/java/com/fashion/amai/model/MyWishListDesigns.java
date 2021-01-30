package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyWishListDesigns {
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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String productName;
        @SerializedName("supplier_id")
        @Expose
        private Integer supplierId;
        @SerializedName("image")
        @Expose
        private String productImage;
        @SerializedName("type")
        @Expose
        private String type;

        public String getType() {
            return type;
        }

        public Integer getId() {
            return id;
        }

        public String getProductName() {
            return productName;
        }

        public Integer getSupplierId() {
            return supplierId;
        }

        public String getProductImage() {
            return productImage;
        }
    }
}
