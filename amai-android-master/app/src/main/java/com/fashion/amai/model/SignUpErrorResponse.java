package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpErrorResponse {
        @SerializedName("error")
        @Expose
        private ResponseBody error;

        public ResponseBody getError() {
            return error;
        }

    public class ResponseBody {
        @SerializedName("status")
        @Expose
        private boolean status;
        @SerializedName("message")
        @Expose
        private String message;

        public boolean isStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

    }

}
