package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class CustomersAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> CustomerNames, CustomerLocation, CustomerNumber;

    public CustomersAdapter (Context context1, ArrayList<String> customerNames, ArrayList<String> customerLocation, ArrayList<String> customerNumber)
    {
        this.context = context1;
        this.CustomerNames = customerNames;
        this.CustomerNumber = customerNumber;
        this.CustomerLocation = customerLocation;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_customers, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView CustomerNameText = (TextView)gridView.findViewById(R.id.customerNameText);
        TextView CustomerLocationText = (TextView)gridView.findViewById(R.id.customerLocationText);
        ImageView CustomerCallImage = (ImageView) gridView.findViewById(R.id.customerCallIcon);
        ImageView CustomerMessageImage = (ImageView)gridView.findViewById(R.id.customerMessageIcon);

        CustomerNameText.setText(CustomerNames.get(position));
        CustomerLocationText.setText(CustomerLocation.get(position));
        CustomerCallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + CustomerNumber.get(position)));
                context.startActivity(intent);
            }
        });

        CustomerMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsms = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + CustomerNumber.get(position) ) );
                intentsms.putExtra("sms_body", "" );
                context.startActivity(intentsms);
            }
        });

        return gridView;
    }

    @Override
    public int getCount() {
        return CustomerNames.size();
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
