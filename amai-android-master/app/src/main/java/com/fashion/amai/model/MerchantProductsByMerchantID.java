package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchantProductsByMerchantID {

    public class Response{

            @SerializedName("status")
            @Expose
            private Boolean status;
            @SerializedName("message")
            @Expose
            private String message;
            @SerializedName("data")
            @Expose
            private List<Datum> data = null;

            public Boolean getStatus() {
                return status;
            }

            public void setStatus(Boolean status) {
                this.status = status;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public List<Datum> getData() {
                return data;
            }

            public void setData(List<Datum> data) {
                this.data = data;
            }

    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("net_price")
        @Expose
        private String netPrice;
        @SerializedName("cover_image")
        @Expose
        private String coverImage;
        @SerializedName("supplier_id")
        @Expose
        private String supplier_id;
        @SerializedName("is_wishlisted")
        @Expose
        private boolean is_wishlisted;
        @SerializedName("is_selected")
        @Expose
        private boolean is_selected;

        public String getSupplier_id() {
            return supplier_id;
        }

        public boolean isIs_wishlisted() {
            return is_wishlisted;
        }

        public boolean isIs_selected() {
            return is_selected;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getNetPrice() {
            return netPrice;
        }

        public void setNetPrice(String netPrice) {
            this.netPrice = netPrice;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }



    }
}
