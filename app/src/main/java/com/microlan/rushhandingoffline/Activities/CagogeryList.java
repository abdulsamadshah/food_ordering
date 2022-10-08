package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.microlan.rushhandingoffline.R;

public class CagogeryList extends AppCompatActivity {

    LinearLayout lay_add_cat,list_catogery;
    String Flag;

    ImageView addproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cagogery_list);

        getSupportActionBar().setTitle("Categroy");
        addproduct=findViewById(R.id.addcatoger);
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Add_Category.class);
                startActivity(intent);
            }
        });
    }
}