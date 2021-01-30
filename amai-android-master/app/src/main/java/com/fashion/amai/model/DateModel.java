package com.fashion.amai.model;

public class DateModel {
    private String dateValue;
    private int valueState;

    public DateModel(String dateValue, int valueState) {
        this.dateValue = dateValue;
        this.valueState = valueState;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public int getValueState() {
        return valueState;
    }

    public void setValueState(int valueState) {
        this.valueState = valueState;
    }



}
