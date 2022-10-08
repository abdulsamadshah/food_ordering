package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> ProductName, Gst, PackSize, Price, Quantity, Unit;

    public ProductAdapter (Context context1, ArrayList<String> productName, ArrayList<String> gst, ArrayList<String> packSize, ArrayList<String> price,
                           ArrayList<String> quantity, ArrayList<String> unit)
    {
        this.context = context1;
        this.ProductName = productName;
        this.Gst = gst;
        this.PackSize = packSize;
        this.Price = price;
        this.Quantity = quantity;
        this.Unit = unit;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_product, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        LinearLayout BackgroundMainLayput = (LinearLayout)gridView.findViewById(R.id.backGroundMain);
        TextView ProductNameText = (TextView)gridView.findViewById(R.id.productNameText);
        TextView GstText = (TextView)gridView.findViewById(R.id.gstText);
        TextView PackSizeText = (TextView)gridView.findViewById(R.id.packSizeText);
        TextView PriceText = (TextView)gridView.findViewById(R.id.priceText);
        TextView QuantityText = (TextView)gridView.findViewById(R.id.qtyText);
        TextView UnitText = (TextView)gridView.findViewById(R.id.unitText);

        if (Quantity.get(position).equals("0")){
            BackgroundMainLayput.setBackgroundColor(Color.parseColor("#66551a8b"));
        } else {
            BackgroundMainLayput.setBackgroundColor(Color.parseColor("#aa270149"));
        }

        ProductNameText.setText(ProductName.get(position));
        GstText.setText("Gst: " + Gst.get(position));
        PackSizeText.setText(PackSize.get(position));
        PriceText.setText("₹"+Price.get(position));
        QuantityText.setText("Q."+Quantity.get(position));
        UnitText.setText(Unit.get(position));

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
