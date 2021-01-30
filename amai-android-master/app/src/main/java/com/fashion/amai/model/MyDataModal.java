package com.fashion.amai.model;

public class MyDataModal {
    private String imageUrl;
    private String itemName;
    private String appointmentStatus;
    private String date, timeSlot, amount, offAmount, offPercent, quantity;

    public MyDataModal(String imageUrl, String itemName, String appointmentStatus, String date, String timeSlot) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.appointmentStatus = appointmentStatus;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public MyDataModal(String imageUrl, String itemName, String amount, String offAmount, String offPercent, String quantity) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.amount = amount;
        this.offAmount = offAmount;
        this.offPercent = offPercent;
        this.quantity = quantity;
    }

    public MyDataModal(String imageUrl, String itemName, String date, String offPercent) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.date = date;
        this.offPercent = offPercent;
    }

    public MyDataModal(String imageUrl, String itemName) {
        this.imageUrl = imageUrl;
        this.offPercent = offPercent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public String getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getAmount() {
        return amount;
    }

    public String getOffAmount() {
        return offAmount;
    }

    public String getOffPercent() {
        return offPercent;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setOffAmount(String offAmount) {
        this.offAmount = offAmount;
    }

    public void setOffPercent(String offPercent) {
        this.offPercent = offPercent;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
