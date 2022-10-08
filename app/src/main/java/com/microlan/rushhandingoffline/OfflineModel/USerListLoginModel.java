package com.microlan.rushhandingoffline.OfflineModel;

public class USerListLoginModel {

    String op_user_id,user_id,user_name,full_name,first_name,last_name,email_address,password,mobile_number,role_id,map_with,map_with_id,aadhar_no,profile_pic,pan_no,phone_no,address;

  //  USerListLoginModel recordingItem = new USerListLoginModel( user_id,first_name,email_address,password,mobile_number,role_id,map_with,map_with_id,profile_pic,aadhar_no,pan_no,phone_no,address);
//  USerListLoginModel recordingItem = new USerListLoginModel( op_user_id,user_id,first_name,email_address,password,mobile_number,role_id,map_with,map_with_id,profile_pic,aadhar_no,pan_no,phone_no,address);
    public USerListLoginModel(String op_user_id,String user_id, String user_name, String email_address, String password, String mobile_number, String role_id, String map_with, String map_with_id, String profile_pic,String aadhar_no,String pan_no,String phone_no ,String address) {
        this.op_user_id = op_user_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.email_address = email_address;
        this.password = password;
        this.mobile_number = mobile_number;
        this.role_id = role_id;
        this.map_with = map_with;
        this.map_with_id = map_with_id;
        this.profile_pic = profile_pic;
        this.aadhar_no = aadhar_no;
        this.pan_no = pan_no;
        this.phone_no = phone_no;
        this.address = address;
    }

    public String getOp_user_id() {
        return op_user_id;
    }

    public void setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getMap_with() {
        return map_with;
    }

    public void setMap_with(String map_with) {
        this.map_with = map_with;
    }

    public String getMap_with_id() {
        return map_with_id;
    }

    public void setMap_with_id(String map_with_id) {
        this.map_with_id = map_with_id;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
