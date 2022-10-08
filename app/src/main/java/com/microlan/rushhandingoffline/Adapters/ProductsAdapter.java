package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class ProductsAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> ItemSrBarcode, ItemSrNumber, ItemName, ItemQuantity, Quan;

    public ProductsAdapter (Context context1, ArrayList<String> itemSrBarcode,ArrayList<String> itemSrNumber, ArrayList<String> itemName, ArrayList<String> itemQuantity, ArrayList<String> quan)
    {
        this.context = context1;
        this.ItemSrBarcode = itemSrBarcode;
        this.ItemSrNumber = itemSrNumber;
        this.ItemName = itemName;
        this.ItemQuantity = itemQuantity;
        this.Quan = quan;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_products, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView ProductSRNumberText = (TextView)gridView.findViewById(R.id.itemNumberText);
        TextView ProductNameText = (TextView)gridView.findViewById(R.id.itemNameText);
        TextView ProductQuantityText = (TextView)gridView.findViewById(R.id.itemQuantityText);
        TextView ProductQuanText = (TextView)gridView.findViewById(R.id.quantityText);
        ImageView CancelImageView = (ImageView)gridView.findViewById(R.id.cancelImage);

        ProductSRNumberText.setText(ItemSrNumber.get(position));
        ProductNameText.setText(ItemName.get(position));
        ProductQuantityText.setText(ItemQuantity.get(position));
        ProductQuanText.setText(Quan.get(position));

        CancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemSrBarcode.remove(position);
                ItemSrNumber.remove(position);
                ItemName.remove(position);
                ItemQuantity.remove(position);
                Quan.remove(position);
                notifyDataSetChanged();
              //  ((BarcodeScan)context).RemoveProduct();
            }
        });

        return gridView;
    }

    @Override
    public int getCount() {
        return ItemName.size();
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
