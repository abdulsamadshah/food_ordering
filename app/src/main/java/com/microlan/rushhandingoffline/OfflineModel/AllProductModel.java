package com.microlan.rushhandingoffline.OfflineModel;

public class AllProductModel {

    String product_id,product_name,image_name,price,gst,stock_qty,pack_size1,price1,unit_name,description,terms_conditions;
    String catogeryid,hsn_code;
    public AllProductModel(String catogeryid,String product_id, String product_name, String image_name, String price,  String pack_size1, String price1, String unit_name, String terms_conditions, String description,String hsn_code,String stock_qty) {
        this.catogeryid = catogeryid;
        this.product_id = product_id;
        this.product_name = product_name;
        this.image_name = image_name;
        this.price = price;
        this.stock_qty = stock_qty;
        this.pack_size1 = pack_size1;
        this.price1 = price1;
        this.unit_name = unit_name;
        this.description = description;
        this.terms_conditions = terms_conditions;
        this.hsn_code = hsn_code;
    }


    public String getHsn_code() {
        return hsn_code;
    }

    public void setHsn_code(String hsn_code) {
        this.hsn_code = hsn_code;
    }

    public String getCatogeryid() {
        return catogeryid;
    }

    public void setCatogeryid(String catogeryid) {
        this.catogeryid = catogeryid;
    }

    public String getPack_size1() {
        return pack_size1;
    }

    public void setPack_size1(String pack_size1) {
        this.pack_size1 = pack_size1;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(String terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }
}
