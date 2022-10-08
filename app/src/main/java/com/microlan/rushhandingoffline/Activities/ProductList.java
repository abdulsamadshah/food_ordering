package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

public class ProductList extends AppCompatActivity {

    ImageView addproduct;
    TextView view2,view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSupportActionBar().setTitle("Product List");

        addproduct=findViewById(R.id.addproduct);
        view2=findViewById(R.id.view2);
        view1=findViewById(R.id.view1);


        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddProductDesc.class);
                startActivity(intent);

            }
        });
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddProductDesc.class);
                startActivity(intent);

            }
        });
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddProductList.class);
                startActivity(intent);
            }
        });
    }
}