package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.microlan.rushhandingoffline.DB.AllGstDBHelper;
import com.microlan.rushhandingoffline.DB.CompanySettingDBHelper;
import com.microlan.rushhandingoffline.OfflineModel.AllCustomerAddressModel;
import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.CompanySettingModel;
import com.microlan.rushhandingoffline.OfflineModel.USerListLoginModel;
import com.microlan.rushhandingoffline.R;

import java.util.ArrayList;

public class SettingCompany extends AppCompatActivity {

    Button submit;
    EditText name,address,gst,state,pin,contact,email,website,condition;
    CompanySettingDBHelper dbcompanyHelpers;
    ArrayList<CompanySettingModel> arraycompanyitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_company);
        getSupportActionBar().setTitle("Company Setting");

        dbcompanyHelpers = new CompanySettingDBHelper(getApplicationContext());

        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        gst=findViewById(R.id.gst);
        state=findViewById(R.id.state);
        pin=findViewById(R.id.pin);
        contact=findViewById(R.id.contact);
        email=findViewById(R.id.email);
        website=findViewById(R.id.website);
        condition=findViewById(R.id.condition);
        submit=findViewById(R.id.submit);

        getsetting();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arraycompanyitem==null || arraycompanyitem.isEmpty() || arraycompanyitem.size()==0) {
                    CompanySettingModel recordingItem = new CompanySettingModel(name.getText().toString(),address.getText().toString(),gst.getText().toString(),state.getText().toString(),pin.getText().toString(),contact.getText().toString(),email.getText().toString(),website.getText().toString(),condition.getText().toString());
                    dbcompanyHelpers.addSetting(recordingItem);


                }
                else {
                    String id=arraycompanyitem.get(0).getId();
                    CompanySettingModel recordingItem = new CompanySettingModel(id,name.getText().toString(),address.getText().toString(),gst.getText().toString(),state.getText().toString(),pin.getText().toString(),contact.getText().toString(),email.getText().toString(),website.getText().toString(),condition.getText().toString());
                    dbcompanyHelpers.updateSetting(recordingItem);

                }


            }
        });

    }

    private void getsetting() {

        arraycompanyitem = dbcompanyHelpers.getsettingdetails();

        Log.d("","arraycompanyitem"+arraycompanyitem.size());
        if(arraycompanyitem==null || arraycompanyitem.isEmpty() || arraycompanyitem.size()==0) {

         }
        else {
                for(int i=0;i<arraycompanyitem.size();i++)
                {
                    String names=arraycompanyitem.get(0).getName();
                    String addresss=arraycompanyitem.get(0).getAddress();
                    String caontac=arraycompanyitem.get(0).getCaontac();
                    String emails=arraycompanyitem.get(0).getEmail();
                    String gsts=arraycompanyitem.get(0).getGst();
                    String pan=arraycompanyitem.get(0).getPan();
                    String states=arraycompanyitem.get(0).getState();
                    String websites=arraycompanyitem.get(0).getWebsite();
                    String conditions=arraycompanyitem.get(0).getCondition();

                    name.setText(names);
                    address.setText(addresss);
                    gst.setText(gsts);
                    state.setText(states);
                    contact.setText(caontac);
                    email.setText(emails);
                    website.setText(websites);
                    condition.setText(conditions);
                    pin.setText(pan);

                }


                /*        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        gst=findViewById(R.id.gst);
        state=findViewById(R.id.state);
        pin=findViewById(R.id.pin);
        contact=findViewById(R.id.contact);
        email=findViewById(R.id.email);
        website=findViewById(R.id.website);
        condition=findViewById(R.id.condition);
*/
        }


    }
}