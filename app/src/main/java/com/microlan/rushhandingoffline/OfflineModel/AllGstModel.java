package com.microlan.rushhandingoffline.OfflineModel;

import com.google.gson.annotations.SerializedName;

public class AllGstModel {


    private String gstSetId;
    private String gstType;
    private String hsnCode;
    private String description;
    private String sGST;
    private String cGST;
    private String iGST;
    private String cESS,gstid;

    public AllGstModel(String gstSetId, String gstType, String hsnCode, String description, String sGST, String cGST, String iGST, String cESS) {
        this.gstSetId = gstSetId;
        this.gstType = gstType;
        this.hsnCode = hsnCode;
        this.description = description;
        this.sGST = sGST;
        this.cGST = cGST;
        this.iGST = iGST;
        this.cESS = cESS;
    }

    public AllGstModel(String gstid, String gstSetId, String gstType, String hsnCode, String description, String sGST, String cGST, String iGST, String cESS) {
        this.gstSetId = gstSetId;
        this.gstType = gstType;
        this.hsnCode = hsnCode;
        this.description = description;
        this.sGST = sGST;
        this.cGST = cGST;
        this.iGST = iGST;
        this.cESS = cESS;
        this.gstid = gstid;
    }

    public String getGstid() {
        return gstid;
    }

    public void setGstid(String gstid) {
        this.gstid = gstid;
    }

    public String getGstSetId() {
        return gstSetId;
    }

    public void setGstSetId(String gstSetId) {
        this.gstSetId = gstSetId;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getsGST() {
        return sGST;
    }

    public void setsGST(String sGST) {
        this.sGST = sGST;
    }

    public String getcGST() {
        return cGST;
    }

    public void setcGST(String cGST) {
        this.cGST = cGST;
    }

    public String getiGST() {
        return iGST;
    }

    public void setiGST(String iGST) {
        this.iGST = iGST;
    }

    public String getcESS() {
        return cESS;
    }

    public void setcESS(String cESS) {
        this.cESS = cESS;
    }
}
