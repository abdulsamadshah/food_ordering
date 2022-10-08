package com.microlan.rushhandingoffline.OfflineModel;

public class OrderDataDetailsModel {

    String cart_id,product_id,product_name,image_name,price,qty,pack_size,prices,product_size,pack_unit,product_color;
    String unit_total,flag;
    String user_id,order_id,order_number;

    public OrderDataDetailsModel(String cart_id, String product_id, String product_name, String image_name, String price, String qty, String pack_size, String product_size, String pack_unit, String product_color, String unit_total, String user_id, String order_id, String order_number,String flag) {
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.image_name = image_name;
        this.price = price;
        this.qty = qty;
        this.pack_size = pack_size;
        this.product_size = product_size;
        this.pack_unit = pack_unit;
        this.product_color = product_color;
        this.unit_total = unit_total;
        this.user_id = user_id;
        this.order_id = order_id;
        this.order_number = order_number;
        this.flag = flag;
    }

    public OrderDataDetailsModel(String user_id, String flag)
    {
        this.user_id = user_id;
        this.flag = flag;

    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
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

    public void setUnit_total(String unit_total) {
        this.unit_total = unit_total;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit_total() {
        return unit_total;
    }

}
