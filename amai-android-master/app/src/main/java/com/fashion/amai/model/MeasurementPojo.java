package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasurementPojo {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("measurement")
    @Expose
    private String measurement;
    @SerializedName("data")
    @Expose
    private Data data;


    public Boolean getStatus() {
        return status;
    }

    public String getMeasurement() {
        return measurement;
    }

    public Data getData() {
        return data;
    }

    public class Data{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("hip_round")
        @Expose
        private String hipRound;
        @SerializedName("nape_to_waist")
        @Expose
        private String napeToWaist;
        @SerializedName("armhale_depth")
        @Expose
        private String armhaleDepth;
        @SerializedName("shoulder_length")
        @Expose
        private String shoulderLength;
        @SerializedName("shoulder_pain_to_paint")
        @Expose
        private String shoulderPainToPaint;
        @SerializedName("bicep_round")
        @Expose
        private String bicepRound;
        @SerializedName("wrist_round")
        @Expose
        private String wristRound;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("bust_round")
        @Expose
        private String bustRound;
        @SerializedName("waist_round")
        @Expose
        private String waistRound;
        @SerializedName("high_hip_round")
        @Expose
        private String highHipRound;

        @SerializedName("elbow_round")
        @Expose
        private String elbowround;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getId() {
            return id;
        }

        public String getElbowround() {
            return elbowround;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getHipRound() {
            return hipRound;
        }

        public String getNapeToWaist() {
            return napeToWaist;
        }

        public String getArmhaleDepth() {
            return armhaleDepth;
        }

        public String getShoulderLength() {
            return shoulderLength;
        }

        public String getShoulderPainToPaint() {
            return shoulderPainToPaint;
        }

        public String getBicepRound() {
            return bicepRound;
        }

        public String getWristRound() {
            return wristRound;
        }

        public String getHeight() {
            return height;
        }

        public String getBustRound() {
            return bustRound;
        }

        public String getWaistRound() {
            return waistRound;
        }

        public String getHighHipRound() {
            return highHipRound;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }
}
