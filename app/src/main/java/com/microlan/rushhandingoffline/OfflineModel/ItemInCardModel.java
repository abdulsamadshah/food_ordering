package com.microlan.rushhandingoffline.OfflineModel;

public class ItemInCardModel {

    String cart_id,product_id,product_name,image_name,price,pack_size,prices,product_size,pack_unit,product_color;
    Float unit_total;
    String user_id,code;
    int qty;
    public ItemInCardModel(String product_id, String product_name, String image_name, String price, int qty, Float unit_total, String pack_size, String prices, String product_size, String pack_unit, String product_color, String userID,String code) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.image_name = image_name;
        this.price = price;
        this.qty = qty;
        this.unit_total = unit_total;
        this.pack_size = pack_size;
        this.prices = prices;
        this.product_size = product_size;
        this.pack_unit = pack_unit;
        this.product_color = product_color;
        this.user_id = userID;
        this.code = code;
    }
    public ItemInCardModel(String cart_id, String product_id, String product_name, String image_name, String price, int qty, Float unit_total, String pack_size, String prices, String product_size, String pack_unit, String product_color, String userID,String code) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.image_name = image_name;
        this.price = price;
        this.qty = qty;
        this.unit_total = unit_total;
        this.pack_size = pack_size;
        this.prices = prices;
        this.product_size = product_size;
        this.pack_unit = pack_unit;
        this.product_color = product_color;
        this.user_id = userID;
        this.code = code;

    }
    public ItemInCardModel(String cart_id, int qty, Float unit_total) {
        this.cart_id = cart_id;
        this.qty = qty;
        this.unit_total = unit_total;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Float getUnit_total() {
        return unit_total;
    }

    public void setUnit_total(Float unit_total) {
        this.unit_total = unit_total;
    }

    public String getPack_size() {
        return pack_size;
    }

    public void setPack_size(String pack_size) {
        this.pack_size = pack_size;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public String getPack_unit() {
        return pack_unit;
    }

    public void setPack_unit(String pack_unit) {
        this.pack_unit = pack_unit;
    }

    public String getProduct_color() {
        return product_color;
    }

    public void setProduct_color(String product_color) {
        this.product_color = product_color;
    }
}
