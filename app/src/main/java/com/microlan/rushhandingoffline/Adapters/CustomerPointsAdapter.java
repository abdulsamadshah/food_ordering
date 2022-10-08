package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class CustomerPointsAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> CustomerID, CustomerName, CustomerPoints;

    public CustomerPointsAdapter (Context context1, ArrayList<String> customerID,
                                  ArrayList<String> customerName, ArrayList<String> customerPoints)
    {
        this.context = context1;
        this.CustomerID = customerID;
        this.CustomerName = customerName;
        this.CustomerPoints = customerPoints;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_customer_points, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView CustomerNameText = (TextView)gridView.findViewById(R.id.customerNameText);
        TextView CustomerPointsText = (TextView)gridView.findViewById(R.id.customerPointsText);

        CustomerNameText.setText(CustomerName.get(position));
        CustomerPointsText.setText(CustomerPoints.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return CustomerName.size();
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
