package com.fashion.amai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetail {
    public class BusinessDetail {

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("gstin")
        @Expose
        private String gstin;
        @SerializedName("company_name")
        @Expose
        private String companyName;
        @SerializedName("pan")
        @Expose
        private String pan;
        @SerializedName("primary_category")
        @Expose
        private String primaryCategory;
        @SerializedName("year_of_operation")
        @Expose
        private String yearOfOperation;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getGstin() {
            return gstin;
        }

        public void setGstin(String gstin) {
            this.gstin = gstin;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPan() {
            return pan;
        }

        public void setPan(String pan) {
            this.pan = pan;
        }

        public String getPrimaryCategory() {
            return primaryCategory;
        }

        public void setPrimaryCategory(String primaryCategory) {
            this.primaryCategory = primaryCategory;
        }

        public String getYearOfOperation() {
            return yearOfOperation;
        }

        public void setYearOfOperation(String yearOfOperation) {
            this.yearOfOperation = yearOfOperation;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
    public class MyProfileDetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("PersonalDetail")
        @Expose
        private PersonalDetail personalDetail;
        @SerializedName("BankDetail")
        @Expose
        private Object bankDetail;
        @SerializedName("BusinessDetail")
        @Expose
        private BusinessDetail businessDetail;
        @SerializedName("StoreDetail")
        @Expose
        private Object storeDetail;

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

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public PersonalDetail getPersonalDetail() {
            return personalDetail;
        }

        public void setPersonalDetail(PersonalDetail personalDetail) {
            this.personalDetail = personalDetail;
        }

        public Object getBankDetail() {
            return bankDetail;
        }

        public void setBankDetail(Object bankDetail) {
            this.bankDetail = bankDetail;
        }

        public BusinessDetail getBusinessDetail() {
            return businessDetail;
        }

        public void setBusinessDetail(BusinessDetail businessDetail) {
            this.businessDetail = businessDetail;
        }

        public Object getStoreDetail() {
            return storeDetail;
        }

        public void setStoreDetail(Object storeDetail) {
            this.storeDetail = storeDetail;
        }

    }
    public class PersonalDetail {

        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("alternate_number")
        @Expose
        private String alternateNumber;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getAlternateNumber() {
            return alternateNumber;
        }

        public void setAlternateNumber(String alternateNumber) {
            this.alternateNumber = alternateNumber;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

}
