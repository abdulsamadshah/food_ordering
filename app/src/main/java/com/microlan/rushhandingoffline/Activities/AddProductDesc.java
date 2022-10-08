package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.microlan.rushhandingoffline.Activities.AddProductList;
import com.microlan.rushhandingoffline.R;

public class AddProductDesc extends AppCompatActivity {

    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_desc);
        getSupportActionBar().setTitle("Product Description");

        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AddProductList.class);
                startActivity(intent);
                finish();

            }
        });
    }
}