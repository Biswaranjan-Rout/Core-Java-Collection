package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnSelectPojo {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Login.Data data;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Login.Data getData() {
        return data;
    }
}
