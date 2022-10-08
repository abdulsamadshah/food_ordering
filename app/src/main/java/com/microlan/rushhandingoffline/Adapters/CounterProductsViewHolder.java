package com.microlan.rushhandingoffline.Adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

public class CounterProductsViewHolder extends RecyclerView.ViewHolder {
    public TextView ProductNameText, ProductQuanText, PlusQuanBtn, MinusBtn,productNamepacksize,packsizeprice,singlepric,deletecart;
    public ImageView ProductImageView;
    public LinearLayout SizeLayout;

    public CounterProductsViewHolder (View gridView)
    {
        super(gridView);
        ProductNameText = (TextView)gridView.findViewById(R.id.productNameText);
        ProductQuanText = (TextView)gridView.findViewById(R.id.productQuanText);
        ProductImageView = (ImageView)gridView.findViewById(R.id.productImage);
        PlusQuanBtn = (TextView)gridView.findViewById(R.id.plusBtn);
        MinusBtn = (TextView)gridView.findViewById(R.id.minusBtn);
        productNamepacksize = (TextView)gridView.findViewById(R.id.productNamepacksize);
        singlepric = (TextView)gridView.findViewById(R.id.singlepric);
        deletecart = (TextView)gridView.findViewById(R.id.deletecart);
        packsizeprice = (TextView)gridView.findViewById(R.id.packsizeprice);
    }
}
