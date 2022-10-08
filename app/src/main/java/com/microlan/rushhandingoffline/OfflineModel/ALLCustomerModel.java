package com.microlan.rushhandingoffline.OfflineModel;

public class ALLCustomerModel {

    String customername, customernumber, address1, address2, city, state, pincode,customerid,customeremail,code,flag,Uniqeid;

    public ALLCustomerModel(String customername, String customernumber,String customeremail, String address1, String address2, String city, String state, String pincode,String code,String flag,String Uniqeid) {
        this.customername = customername;
        this.customernumber = customernumber;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.customeremail = customeremail;
        this.code = code;
        this.flag = flag;
        this.Uniqeid = Uniqeid;
    }
    public ALLCustomerModel(String customerid, String customername,String customernumber,String customeremail, String address1, String address2, String city, String state, String pincode,String code,String flag,String Uniqeid) {
        this.customerid = customerid;
        this.customername = customername;
        this.customernumber = customernumber;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.customeremail = customeremail;
        this.code = code;
        this.flag = flag;
        this.Uniqeid = Uniqeid;
    }

    public String getUniqeid() {
        return Uniqeid;
    }

    public void setUniqeid(String uniqeid) {
        Uniqeid = uniqeid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCustomeremail() {
        return customeremail;
    }

    public void setCustomeremail(String customeremail) {
        this.customeremail = customeremail;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}