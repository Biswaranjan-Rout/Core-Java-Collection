package com.fashion.amai.model;

public class TimeModel {
    private String timeValue;
    private int valueState;

    public TimeModel(String dateValue, int valueState) {
        this.timeValue = dateValue;
        this.valueState = valueState;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public int getValueState() {
        return valueState;
    }

    public void setValueState(int valueState) {
        this.valueState = valueState;
    }
}
