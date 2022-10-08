package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class DashboardOrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> OrderId, OrderNumber, OrderAmount;

    public DashboardOrderAdapter (Context context1, ArrayList<String> orderId, ArrayList<String> orderNumber, ArrayList<String> orderAmount)
    {
        this.context = context1;
        this.OrderId = orderId;
        this.OrderNumber = orderNumber;
        this.OrderAmount = orderAmount;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_bills, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView OrderNumberText = (TextView)gridView.findViewById(R.id.orderNumberText);
        TextView OrderAmountText = (TextView)gridView.findViewById(R.id.orderAmountText);

        OrderNumberText.setText(OrderNumber.get(position));
        OrderAmountText.setText(OrderAmount.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return OrderNumber.size();
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
