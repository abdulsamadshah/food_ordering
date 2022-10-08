package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.microlan.rushhandingoffline.DB.OrderDataDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper;
import com.microlan.rushhandingoffline.DBAdapter.OrderHistoeryAdapter;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;
import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class OrderHostory extends AppCompatActivity {

    OrderDataDBHelper dborderdata;
    OrderDataDetailDBHelper dborderdetails;

    ArrayList<OrderDataModel> arrayListorderdata;
    ArrayList<OrderDataDetailsModel> arrayListorderfetails;

    String Intro = "", UserID, session_id;
    SharedPreferences sharedPreferences;
    ArrayList<String> UserName, OrderNumber, Orderid,TotalAmount, Address, DateTime, DateList,WalletAmount,Discount,CashAmount,address_id,PaymentMode;
    OrderHistoeryAdapter ordersAdapter;
    GridView OrdersGrid;
    ArrayList<String> IGST,CGST,SGST,Balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_hostory);

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        getSupportActionBar().setTitle("Offline");

        dborderdata = new OrderDataDBHelper(getApplicationContext());
        dborderdetails = new OrderDataDetailDBHelper(getApplicationContext());
        OrdersGrid = (GridView)findViewById(R.id.ordersGrid);

        arrayListorderdata = dborderdata.getorderdata();
      //  arrayListorderdata = dborderdata.getorderdata( UserID,"0");

        UserName = new ArrayList<String>();
        OrderNumber = new ArrayList<String>();
        TotalAmount = new ArrayList<String>();
        Address = new ArrayList<String>();
        DateTime = new ArrayList<String>();
        DateList = new ArrayList<String>();
        WalletAmount = new ArrayList<String>();
        Discount = new ArrayList<String>();
        CashAmount = new ArrayList<String>();
        address_id = new ArrayList<String>();
        PaymentMode = new ArrayList<String>();
        Orderid = new ArrayList<String>();
        IGST = new ArrayList<String>();
        CGST = new ArrayList<String>();
        SGST = new ArrayList<String>();
        Balance = new ArrayList<String>();



        orderlist();
    }

    public void orderlist() {

        UserName.clear();
        OrderNumber.clear();
        TotalAmount.clear();
        DateTime.clear();
        Address.clear();
        WalletAmount.clear();
        Discount.clear();
        CashAmount.clear();
        address_id.clear();
        PaymentMode.clear();
        Orderid.clear();
        IGST.clear();
        CGST.clear();
        SGST.clear();
        Balance.clear();

        Log.d("","arrayListorderdata"+arrayListorderdata.size());

        {
            for (int i = 0; i < arrayListorderdata.size(); i++) {

                String userNameString = arrayListorderdata.get(i).getCc_user_name();
                String orderNumberString = arrayListorderdata.get(i).getOrder_number();
                String totalAmountString = arrayListorderdata.get(i).getTotal_amount();
                String address = arrayListorderdata.get(i).getAddress_id();
                String dateTimeString = arrayListorderdata.get(i).getOrder_date_time();
                String payment_method = arrayListorderdata.get(i).getPayment_method();
                String wallet_amount = arrayListorderdata.get(i).getWallet_amount();
                String discount = arrayListorderdata.get(i).getDiscount();
                String cash_amount = arrayListorderdata.get(i).getCash_amount();
                String orderid = arrayListorderdata.get(i).getOrderid();
                String flag = arrayListorderdata.get(i).getFlag();
                String igst = arrayListorderdata.get(i).getIgst();
                String cgst = arrayListorderdata.get(i).getCgst();
                String sgst = arrayListorderdata.get(i).getSgst();
                String balance = arrayListorderdata.get(i).getBalance();

                UserName.add(userNameString);
                OrderNumber.add(orderNumberString);
                TotalAmount.add(totalAmountString);
                Address.add(address);
                DateTime.add(dateTimeString);
                PaymentMode.add(payment_method);

                WalletAmount.add(wallet_amount);
                Discount.add(discount);
                CashAmount.add(cash_amount);
                Orderid.add(orderid);
                IGST.add(orderid);
                Orderid.add(orderid);
                IGST.add(igst);
                CGST.add(cgst);
                SGST.add(sgst);
                Balance.add(balance);


            }

            ordersAdapter = new OrderHistoeryAdapter(getApplicationContext(),UserName, OrderNumber, TotalAmount, Address, DateTime,WalletAmount,Discount,CashAmount,PaymentMode,Orderid,IGST,CGST,SGST,Balance);
            ordersAdapter.notifyDataSetChanged();
            OrdersGrid.setAdapter(ordersAdapter);

        }
    }

}