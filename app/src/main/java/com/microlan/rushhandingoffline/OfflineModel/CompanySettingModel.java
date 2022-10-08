package com.microlan.rushhandingoffline.OfflineModel;

public class CompanySettingModel {

    String name,address,gst,state,pan,caontac,email,website,condition;
    String id;

    public CompanySettingModel(String name, String address, String gst, String state, String pan, String caontac, String email, String website, String condition) {
        this.name = name;
        this.address = address;
        this.gst = gst;
        this.state = state;
        this.pan = pan;
        this.caontac = caontac;
        this.email = email;
        this.website = website;
        this.condition = condition;
    }
    public CompanySettingModel(String id,String name, String address, String gst, String state, String pan, String caontac, String email, String website, String condition) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gst = gst;
        this.state = state;
        this.pan = pan;
        this.caontac = caontac;
        this.email = email;
        this.website = website;
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCaontac() {
        return caontac;
    }

    public void setCaontac(String caontac) {
        this.caontac = caontac;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
