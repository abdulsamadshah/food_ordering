package com.microlan.rushhandingoffline.DBAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microlan.rushhandingoffline.Activities.OrderProducts;
import com.microlan.rushhandingoffline.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class OrderHistoeryAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> UserName, OrderNumber, total_amount, Address, DateTime, walletAmount, discount, cashAmount, paymentMode, orderid;
    ArrayList<JSONArray> orderProducts;

    String userNameString, orderNumberString, totalAmountString, wallet_amount, discountamt, cash_amount;
    ArrayList<String> IGST, CGST, SGST, Balance;

    public OrderHistoeryAdapter(Context orderHostory, ArrayList<String> userName, ArrayList<String> orderNumber, ArrayList<String> totalAmount, ArrayList<String> address, ArrayList<String> dateTime, ArrayList<String> walletAmount, ArrayList<String> discount, ArrayList<String> cashAmount, ArrayList<String> paymentMode, ArrayList<String> orderid, ArrayList<String> IGST, ArrayList<String> CGST, ArrayList<String> SGST, ArrayList<String> balance) {

        this.context = orderHostory;
        this.UserName = userName;
        this.OrderNumber = orderNumber;
        this.total_amount = totalAmount;
        this.Address = address;
        this.DateTime = dateTime;
        this.walletAmount = walletAmount;
        this.discount = discount;
        this.cashAmount = cashAmount;
        this.paymentMode = paymentMode;
        this.orderid = orderid;
        this.IGST = IGST;
        this.CGST = CGST;
        this.SGST = SGST;
        this.Balance = balance;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_orders, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView UserNameText = (TextView) gridView.findViewById(R.id.userNameText);
        TextView OrdernumberText = (TextView) gridView.findViewById(R.id.orderIDText);
        TextView TotalAmountText = (TextView) gridView.findViewById(R.id.priceTexts);
        TextView AddressText = (TextView) gridView.findViewById(R.id.addressText);
        TextView DateTimeText = (TextView) gridView.findViewById(R.id.dateText);
        TextView walletamt = (TextView) gridView.findViewById(R.id.walletamt);
        final TextView discountamt = (TextView) gridView.findViewById(R.id.discount);
        LinearLayout order_view = (LinearLayout) gridView.findViewById(R.id.order_view);
        UserNameText.setText("User: " + UserName.get(position));
        OrdernumberText.setText("Order No: " + OrderNumber.get(position));
        discountamt.setText("Discount: " + discount.get(position));
        walletamt.setText("By Wallet " + walletAmount.get(position));
        DateTimeText.setText("Date/Time: " + DateTime.get(position));
        TotalAmountText.setText("Total: " + total_amount.get(position));
        AddressText.setText("Address: " + Address.get(position));

//
//        if(Address.get(position).equals("")||Address.get(position).equals("0"))
//        {
//            AddressText.setVisibility(View.GONE);
//        }
//        else {
//            AddressText.setText("Address: " + Address.get(position));
//
//        }
//        DateTimeText.setText("Date/Time: " + DateTime.get(position));
//
//        if(walletAmount.get(position).equals("0.00"))
//        {
//            walletamt.setVisibility(View.GONE);
//        }
//        else {
//            walletamt.setText("By Wallet "+walletAmount.get(position));
//        }
//        if(discount.get(position).equals("0.00"))
//        {
//            discountamt.setVisibility(View.GONE);
//        }
//        else {
//            discountamt.setText("Discount"+discount.get(position));
//        }
//        if(cashAmount.get(position).equals("0.00"))
//        {
//            cash.setVisibility(View.GONE);
//        }
//        else {
//            cash.setText("BY Cash  "+cashAmount.get(position));
//        }


        order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProductsOrderIntent = new Intent(context, OrderProducts.class);

//                ProductsOrderIntent.putExtra("SubTotal",total_amount.get(position));
//                ProductsOrderIntent.putExtra("balanceamt",cashAmount.get(position));
                ProductsOrderIntent.putExtra("Customername", UserName.get(position));
//                ProductsOrderIntent.putExtra("dicount", discount.get(position));
                ProductsOrderIntent.putExtra("walletbalance", walletAmount.get(position));
                ProductsOrderIntent.putExtra("orderNo", OrderNumber.get(position));
                ProductsOrderIntent.putExtra("CustomerAddress", Address.get(position));
                ProductsOrderIntent.putExtra("payment", paymentMode.get(position));
                ProductsOrderIntent.putExtra("TotalAmount", total_amount.get(position));


//                ProductsOrderIntent.putExtra("orderid",orderid.get(position));
//                ProductsOrderIntent.putExtra("IGST",IGST.get(position));
//                ProductsOrderIntent.putExtra("CGST",CGST.get(position));
//                ProductsOrderIntent.putExtra("SGST",SGST.get(position));

                ProductsOrderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ProductsOrderIntent);
            }
        });

        return gridView;
    }

    @Override
    public int getCount() {
        return UserName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}

