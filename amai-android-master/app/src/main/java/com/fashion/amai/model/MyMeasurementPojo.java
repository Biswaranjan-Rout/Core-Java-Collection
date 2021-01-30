package com.fashion.amai.model;

public class MyMeasurementPojo {
    private String size;
    private String waist;

    public String getSize() {
        return size;
    }

    public String getWaist() {
        return waist;
    }

    public MyMeasurementPojo(String size, String waist) {
        this.size = size;
        this.waist = waist;
    }
}
