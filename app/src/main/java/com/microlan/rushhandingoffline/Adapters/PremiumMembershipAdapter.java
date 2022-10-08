package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class PremiumMembershipAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> MonthsList, AmountList;

    public PremiumMembershipAdapter (Context context1, ArrayList<String> monthsList, ArrayList<String> amountList)
    {
        this.context = context1;
        this.AmountList = amountList;
        this.MonthsList = monthsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_premium_membership, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView MonthsText = (TextView)gridView.findViewById(R.id.monthsText);
        TextView AmountText = (TextView)gridView.findViewById(R.id.amountText);

        MonthsText.setText(MonthsList.get(position));
        AmountText.setText(AmountList.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return MonthsList.size();
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
