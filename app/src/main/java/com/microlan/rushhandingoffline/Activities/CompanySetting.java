package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

public class CompanySetting extends AppCompatActivity {

    LinearLayout productsetting,catogerysetting,gst_setting,settingmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_setting);
        getSupportActionBar().setTitle(" Setting");

        gst_setting=findViewById(R.id.gst_setting);

        productsetting=findViewById(R.id.productsetting);
        catogerysetting=findViewById(R.id.catogerysetting);
        settingmenu=findViewById(R.id.settingmenu);


        gst_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GSTTABLE.class);
                startActivity(intent);
            }
        });
        settingmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SettingCompany.class);
                startActivity(intent);
            }
        });
        productsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductList.class);
                startActivity(intent);
            }
        });
        catogerysetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CagogeryList.class);
                intent.putExtra("Flag","1");
                startActivity(intent);
            }
        });


    }
}