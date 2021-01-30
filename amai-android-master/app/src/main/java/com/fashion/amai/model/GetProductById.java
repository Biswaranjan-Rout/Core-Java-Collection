package com.fashion.amai.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductById {
    public class ResponseObject {

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

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    }

    public class Prices {

        @SerializedName("s")
        @Expose
        private Integer s;
        @SerializedName("m")
        @Expose
        private Integer m;
        @SerializedName("l")
        @Expose
        private Integer l;
        @SerializedName("xl")
        @Expose
        private Integer xl;

        public Integer getS() {
            return s;
        }

        public void setS(Integer s) {
            this.s = s;
        }

        public Integer getM() {
            return m;
        }

        public void setM(Integer m) {
            this.m = m;
        }

        public Integer getL() {
            return l;
        }

        public void setL(Integer l) {
            this.l = l;
        }

        public Integer getXl() {
            return xl;
        }

        public void setXl(Integer xl) {
            this.xl = xl;
        }

    }

    public class Data {


        @SerializedName("product_id") @Expose private String productId;
        @SerializedName("product_name") @Expose private String productName;
        @SerializedName("description") @Expose private String description;
        @SerializedName("category") @Expose private String category;
        @SerializedName("material_detail") @Expose private String materialDetail;
        @SerializedName("sleeve") @Expose private String sleeve;
        @SerializedName("ideal_for") @Expose private String ideal_for;
        @SerializedName("suitable_for") @Expose private String suitable_for;
        @SerializedName("pattern") @Expose private String pattern;
        @SerializedName("type") @Expose private String type;
        @SerializedName("pack_of") @Expose private String pack_of;
        @SerializedName("occasion") @Expose private String occasion;
        @SerializedName("fabric") @Expose private String fabric;
        @SerializedName("is_wishlisted") @Expose private boolean is_wishlisted;
        @SerializedName("is_selected") @Expose private boolean is_selected;

        public boolean isIs_wishlisted() {
            return is_wishlisted;
        }

        public boolean isIs_selected() {
            return is_selected;
        }

        @SerializedName("default_size")
        @Expose
        private String defaultSize;
        @SerializedName("default_price")
        @Expose
        private Integer defaultPrice;
        @SerializedName("discount")
        @Expose
        private Double discount;
        @SerializedName("net_price")
        @Expose
        private Integer netPrice;


        @SerializedName("prices")
        @Expose
        private JsonObject prices;
        @SerializedName("size_quantity")
        @Expose
        private JsonObject availableSizes;


        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("supplier_id")
        @Expose
        private Integer supplierId;

        public JsonObject getPrices() {
            return prices;
        }

        public JsonObject getAvailableSizes() {
            return availableSizes;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getMaterialDetail() {
            return materialDetail;
        }

        public void setMaterialDetail(String materialDetail) {
            this.materialDetail = materialDetail;
        }

        public String getDefaultSize() {
            return defaultSize;
        }

        public void setDefaultSize(String defaultSize) {
            this.defaultSize = defaultSize;
        }

        public Integer getDefaultPrice() {
            return defaultPrice;
        }

        public void setDefaultPrice(Integer defaultPrice) {
            this.defaultPrice = defaultPrice;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }

        public Integer getNetPrice() {
            return netPrice;
        }

        public void setNetPrice(Integer netPrice) {
            this.netPrice = netPrice;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public Integer getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(Integer supplierId) {
            this.supplierId = supplierId;
        }

        public String getProductId() {
            return productId;
        }


        public String getSleeve() {
            return sleeve;
        }

        public String getIdeal_for() {
            return ideal_for;
        }

        public String getSuitable_for() {
            return suitable_for;
        }

        public String getPattern() {
            return pattern;
        }

        public String getType() {
            return type;
        }

        public String getPack_of() {
            return pack_of;
        }

        public String getOccasion() {
            return occasion;
        }

        public String getFabric() {
            return fabric;
        }
    }

}