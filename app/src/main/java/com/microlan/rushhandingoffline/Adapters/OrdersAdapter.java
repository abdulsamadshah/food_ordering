package com.microlan.rushhandingoffline.Adapters;

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

public class OrdersAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> UserName, OrderNumber, TotalAmount, Address, DateTime,walletAmount,discount,cashAmount,paymentMode;
    ArrayList<JSONArray> orderProducts;

    String userNameString,orderNumberString,totalAmountString,wallet_amount,discountamt,cash_amount;
    public OrdersAdapter(Context context1, ArrayList<String> userName, ArrayList<String> orderNumber, ArrayList<String> totalAmount,
                         ArrayList<String> address, ArrayList<String> dateTime, ArrayList<String> walletAmount, ArrayList<String> discount, ArrayList<String> cashAmount, ArrayList<JSONArray> orderProducts, String userNameString, String orderNumberString,
                         String totalAmountString, String wallet_amount, String discountamt, String cash_amount, ArrayList<String> paymentMode)
    {
        this.context = context1;
        this.UserName = userName;
        this.OrderNumber = orderNumber;
        this.TotalAmount = totalAmount;
        this.Address = address;
        this.DateTime = dateTime;
        this.walletAmount = walletAmount;
        this.discount = discount;
        this.cashAmount = cashAmount;
        this.orderProducts = orderProducts;

        this.userNameString = userNameString;
        this.orderNumberString = orderNumberString;
        this.totalAmountString = totalAmountString;
        this.discountamt = discountamt;
        this.cash_amount = cash_amount;
        this.wallet_amount = wallet_amount;
        this.paymentMode = paymentMode;





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

        TextView UserNameText = (TextView)gridView.findViewById(R.id.userNameText);
        TextView OrdernumberText = (TextView)gridView.findViewById(R.id.orderIDText);
        TextView TotalAmountText = (TextView)gridView.findViewById(R.id.priceText);
        TextView AddressText = (TextView)gridView.findViewById(R.id.addressText);
        TextView DateTimeText = (TextView)gridView.findViewById(R.id.dateText);
        TextView walletamt = (TextView)gridView.findViewById(R.id.walletamt);
        final TextView discountamt = (TextView)gridView.findViewById(R.id.discount);
        TextView cash = (TextView)gridView.findViewById(R.id.cash);
        LinearLayout order_view=(LinearLayout)gridView.findViewById(R.id.order_view);
        UserNameText.setText("User: " + UserName.get(position));
        OrdernumberText.setText("Order No: " + OrderNumber.get(position));
        TotalAmountText.setText("Total: " +TotalAmount.get(position));

        if(Address.get(position).equals("")||Address.get(position).equals("0"))
        {
            AddressText.setVisibility(View.GONE);
        }
        else {
            AddressText.setText("Address: " + Address.get(position));

        }
        DateTimeText.setText("Date/Time: " + DateTime.get(position));

        if(walletAmount.get(position).equals("0.00"))
        {
            walletamt.setVisibility(View.GONE);
        }
        else {
            walletamt.setText("By Wallet "+walletAmount.get(position));
        }
        if(discount.get(position).equals("0.00"))
        {
            discountamt.setVisibility(View.GONE);
        }
        else {
            discountamt.setText("Discount"+discount.get(position));
        }
        if(cashAmount.get(position).equals("0.00"))
        {
            cash.setVisibility(View.GONE);
        }
        else {
            cash.setText("BY Cash  "+cashAmount.get(position));
        }


        order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProductsOrderIntent = new Intent(context, OrderProducts.class);

                ProductsOrderIntent.putExtra("SubTotal",TotalAmount.get(position));
                ProductsOrderIntent.putExtra("balanceamt",cashAmount.get(position));
                ProductsOrderIntent.putExtra("dicount",discount.get(position));
                ProductsOrderIntent.putExtra("walletbalance",walletAmount.get(position));
                ProductsOrderIntent.putExtra("CustomerAddress",Address.get(position));
                ProductsOrderIntent.putExtra("orderNo",OrderNumber.get(position));
                ProductsOrderIntent.putExtra("Customername",UserName.get(position));
                ProductsOrderIntent.putExtra("payment",paymentMode.get(position));

                ProductsOrderIntent.putExtra("Products", orderProducts.get(position).toString());
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
