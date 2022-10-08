package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class BillAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> SrNo, ProductName, Quantity, Rate, Amount,size,color,packsize,unit;

    public BillAdapter(Context context1, ArrayList<String> srNo, ArrayList<String> productName, ArrayList<String> quantity,
                       ArrayList<String> rate, ArrayList<String> amount, ArrayList<String> color, ArrayList<String> size, ArrayList<String> packsize, ArrayList<String> unit)
    {
        this.context = context1;
        this.SrNo = srNo;
        this.ProductName = productName;
        this.Quantity = quantity;
        this.Rate = rate;
        this.Amount = amount;
        this.size = size;
        this.color = color;
        this.packsize = packsize;
        this.unit = unit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_bill, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView SrNoText = (TextView)gridView.findViewById(R.id.srNumberText);
        TextView ProductNameText = (TextView)gridView.findViewById(R.id.productNameText);
        TextView ProductQtyText = (TextView)gridView.findViewById(R.id.qtyText);
        TextView ProductRateText = (TextView)gridView.findViewById(R.id.rateText);
        TextView ProductAmountText = (TextView)gridView.findViewById(R.id.amountText);
        TextView colors = (TextView)gridView.findViewById(R.id.productcolorText);
        TextView packsizes = (TextView)gridView.findViewById(R.id.productsize);

        Log.d("","ProductName"+ProductName);
        SrNoText.setText(SrNo.get(position));
        ProductNameText.setText(ProductName.get(position)+"-("+packsize.get(position)+" "+unit.get(position)+")");
        ProductQtyText.setText(Quantity.get(position));
        ProductRateText.setText(Rate.get(position));
        ProductAmountText.setText(Amount.get(position));
        packsizes.setText(size.get(position));
        colors.setText(color.get(position));

        Log.d("fdf","packsize"+packsize.get(position));
        return gridView;
    }

    @Override
    public int getCount() {
        return ProductName.size();
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
