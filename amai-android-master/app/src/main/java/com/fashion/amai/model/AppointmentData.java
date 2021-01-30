package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentData extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<AppointmentDataDetails> data;


    public List<AppointmentDataDetails> getData() {
        return data;
    }

    public class AppointmentDataDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("supplier_id")
    @Expose
    private String supplier_id;
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

    public String getAddress() {
        return address;
    }

    public String getSupplier_id() {
        return supplier_id;
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
