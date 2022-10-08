package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microlan.rushhandingoffline.Activities.OfflineCounter;
import com.microlan.rushhandingoffline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> CategoryID, CategoryName, CategoryImage,imageString;


    public CategoryAdapter(Context context1, ArrayList<String> categoryID, ArrayList<String> categoryName, ArrayList<String> categoryImage, String userID){
        this.context = context1;
        this.CategoryID = categoryID;
        this.CategoryName = categoryName;
        this.CategoryImage = categoryImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grid_item_categories, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        /*if(imageString.size()==0)
        {
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(imageString.get(position), 0, imageString.size());
            holder.CategoryImageView.setImageBitmap(bitmapImage);

        }
        else {
        */    Picasso.get().load(CategoryImage.get(position)).into(holder.CategoryImageView);

        //}
        holder.CategoryNameText.setText(CategoryName.get(position));

        holder.categorieslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               ((OfflineCounter)context).GetCatProducts(CategoryID.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return CategoryID.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView CategoryNameText;
        ImageView CategoryImageView;
        LinearLayout categorieslay;
        public MyViewHolder(@NonNull View gridView) {
            super(gridView);

             CategoryNameText = (TextView)gridView.findViewById(R.id.categoryNameText);
             CategoryImageView = (ImageView)gridView.findViewById(R.id.categoryImage);
            categorieslay=gridView.findViewById(R.id.categorieslay);
        }
    }
    }


