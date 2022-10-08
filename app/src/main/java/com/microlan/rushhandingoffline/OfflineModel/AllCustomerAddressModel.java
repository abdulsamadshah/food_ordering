package com.microlan.rushhandingoffline.OfflineModel;

import com.google.gson.annotations.SerializedName;

public class AllCustomerAddressModel {
    private String townCity,customerId;

    private String pincode;

    private String landmarkNearestArea;

    private String fullName;

    private String emailAddress;

    private String addressType;

    private String address2;

    private String address1;

    private String state;

    private String mobileNumber;

    private String stateCode,addressid,flag,uniqeid;
//                AllCustomerAddressModel recordingItem  = new AllCustomerAddressModel(address_id,city,customer_id,pincode,area,name,email, type, address2,address1,state,mobile,code);

    public AllCustomerAddressModel(String townCity, String customerId, String pincode, String landmarkNearestArea, String fullName, String emailAddress, String addressType, String address2, String address1, String state, String mobileNumber, String stateCode,String flag,String uniqeid) {
        this.townCity = townCity;
        this.customerId = customerId;
        this.pincode = pincode;
        this.landmarkNearestArea = landmarkNearestArea;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.addressType = addressType;
        this.address2 = address2;
        this.address1 = address1;
        this.state = state;
        this.mobileNumber = mobileNumber;
        this.stateCode = stateCode;
        this.addressid = addressid;
        this.flag = flag;
        this.uniqeid = uniqeid;
    }
/*
    public AllCustomerAddressModel(String addressid,String townCity, String customerId, String pincode, String landmarkNearestArea, String fullName, String emailAddress, String addressType, String address2, String address1, String state, String mobileNumber, String stateCode,String flag) {
        this.townCity = townCity;
        this.customerId = customerId;
        this.pincode = pincode;
        this.landmarkNearestArea = landmarkNearestArea;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.addressType = addressType;
        this.address2 = address2;
        this.address1 = address1;
        this.state = state;
        this.mobileNumber = mobileNumber;
        this.stateCode = stateCode;
        this.addressid = addressid;
        this.flag = flag;
    }
*/

    public String getUniqeid() {
        return uniqeid;
    }

    public void setUniqeid(String uniqeid) {
        this.uniqeid = uniqeid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmarkNearestArea() {
        return landmarkNearestArea;
    }

    public void setLandmarkNearestArea(String landmarkNearestArea) {
        this.landmarkNearestArea = landmarkNearestArea;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
