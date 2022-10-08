package com.microlan.rushhandingoffline.OfflineModel;

public class OrderDataModel {

    String user_id,order_number,total_amount,payment_method,address_id,order_source,cc_user_name,order_date_time,wallet_amount,discount,discount_type,cash_amount,cust_id,balance;
    String orderid,flag;
    String Igst,Cgst,Sgst;

    public OrderDataModel(String user_id, String order_number, String total_amount, String payment_method, String address_id, String order_source, String cc_user_name, String order_date_time, String wallet_amount, String discount, String discount_type, String cash_amount,String cust_id,String balance,String flag,String Igst,String Cgst,String Sgst) {
        this.user_id = user_id;
        this.order_number = order_number;
        this.total_amount = total_amount;
        this.payment_method = payment_method;
        this.address_id = address_id;
        this.order_source = order_source;
        this.cc_user_name = cc_user_name;
        this.order_date_time = order_date_time;
        this.wallet_amount = wallet_amount;
        this.discount = discount;
        this.discount_type = discount_type;
        this.cash_amount = cash_amount;
        this.cust_id = cust_id;
        this.balance = balance;
        this.flag = flag;
        this.Igst = Igst;
        this.Cgst = Cgst;
        this.Sgst = Sgst;

    }
    public OrderDataModel(String orderid,String user_id, String order_number, String total_amount, String payment_method, String address_id, String order_source, String cc_user_name, String order_date_time, String wallet_amount, String discount, String discount_type, String cash_amount,String cust_id,String balance,String flag,String Igst,String Cgst,String Sgst) {
        this.orderid = orderid;
        this.user_id = user_id;
        this.order_number = order_number;
        this.total_amount = total_amount;
        this.payment_method = payment_method;
        this.address_id = address_id;
        this.order_source = order_source;
        this.cc_user_name = cc_user_name;
        this.order_date_time = order_date_time;
        this.wallet_amount = wallet_amount;
        this.discount = discount;
        this.discount_type = discount_type;
        this.cash_amount = cash_amount;
        this.cust_id = cust_id;
        this.balance = balance;
        this.flag = flag;
        this.Igst = Igst;
        this.Cgst = Cgst;
        this.Sgst = Sgst;
    }

    public OrderDataModel(String user_id, String flag)
    {
        this.user_id = user_id;
        this.order_number = order_number;
        this.flag = flag;

    }

    public String getIgst() {
        return Igst;
    }

    public void setIgst(String igst) {
        Igst = igst;
    }

    public String getCgst() {
        return Cgst;
    }

    public void setCgst(String cgst) {
        Cgst = cgst;
    }

    public String getSgst() {
        return Sgst;
    }

    public void setSgst(String sgst) {
        Sgst = sgst;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getCc_user_name() {
        return cc_user_name;
    }

    public void setCc_user_name(String cc_user_name) {
        this.cc_user_name = cc_user_name;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public void setOrder_date_time(String order_date_time) {
        this.order_date_time = order_date_time;
    }

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getCash_amount() {
        return cash_amount;
    }

    public void setCash_amount(String cash_amount) {
        this.cash_amount = cash_amount;
    }
}
