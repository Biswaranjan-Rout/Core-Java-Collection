package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStoreDetail {
    public class ResponseObject {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address1")
        @Expose
        private Object address1;
        @SerializedName("address2")
        @Expose
        private Object address2;
        @SerializedName("store_image")
        @Expose
        private Object storeImage;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAddress1() {
            return address1;
        }

        public void setAddress1(Object address1) {
            this.address1 = address1;
        }

        public Object getAddress2() {
            return address2;
        }

        public void setAddress2(Object address2) {
            this.address2 = address2;
        }

        public Object getStoreImage() {
            return storeImage;
        }

        public void setStoreImage(Object storeImage) {
            this.storeImage = storeImage;
        }

    }
}
