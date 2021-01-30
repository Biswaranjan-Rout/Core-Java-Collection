package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    public class Response {
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

    public class Data {

        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("refresh_token")
        @Expose
        private String refresh_token;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("is_approved")
        @Expose
        private int is_approved;
        @SerializedName("profile_image")
        @Expose
        private String profile_image;

        public String getRefresh_token() {
            return refresh_token;
        }

        public int getIs_approved() {
            return is_approved;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

    }
}
