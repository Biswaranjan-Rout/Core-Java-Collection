package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MerchantData extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<MerchantStoreData> data = null;

    public List<MerchantStoreData> getData() {
        return data;
    }

    public void setData(List<MerchantStoreData> data) {
        this.data = data;
    }
}
