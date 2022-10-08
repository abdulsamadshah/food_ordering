package com.microlan.rushhandingoffline.Activities;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.microlan.rushhandingoffline.R;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class GSTCalculations extends AppCompatActivity {

    CheckBox EnableGSTCheckBox, ExcludingGSTCheckBox, NoGSTCheckBox;
    LinearLayout GSTRateLayout;
    String GSTStat;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstcalculations);
        getSupportActionBar().setTitle("GST Calculations");

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        GSTStat = sharedPreferences.getString("GSTEnable", encrypt("False"));
        GSTStat = decrypt(GSTStat);

        EnableGSTCheckBox = (CheckBox)findViewById(R.id.enableGSTCheckBox);
        ExcludingGSTCheckBox = (CheckBox)findViewById(R.id.excludingGSTCheckBox);
        NoGSTCheckBox = (CheckBox)findViewById(R.id.noGSTCheckBox);

        if (GSTStat.equals("True")){
            EnableGSTCheckBox.setChecked(true);
            ExcludingGSTCheckBox.setChecked(false);
            NoGSTCheckBox.setChecked(false);
        } else if (GSTStat.equals("None")) {
            EnableGSTCheckBox.setChecked(false);
            ExcludingGSTCheckBox.setChecked(false);
            NoGSTCheckBox.setChecked(true);
        } else {
            NoGSTCheckBox.setChecked(false);
            EnableGSTCheckBox.setChecked(false);
            ExcludingGSTCheckBox.setChecked(true);
        }

        EnableGSTCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    NoGSTCheckBox.setChecked(false);
                    ExcludingGSTCheckBox.setChecked(false);
                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putString("GSTEnable", encrypt("True"));
                    editor.commit();
                } else {
                    //ExcludingGSTCheckBox.setChecked(true);
//                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
//                    editor.putString("GSTEnable", encrypt("False"));
//                    editor.commit();
                }
            }
        });

        ExcludingGSTCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    NoGSTCheckBox.setChecked(false);
                    EnableGSTCheckBox.setChecked(false);
                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putString("GSTEnable", encrypt("False"));
                    editor.commit();
                } else {
                    //EnableGSTCheckBox.setChecked(true);
//                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
//                    editor.putString("GSTEnable", encrypt("True"));
//                    editor.commit();
                }
            }
        });

        NoGSTCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ExcludingGSTCheckBox.setChecked(false);
                    EnableGSTCheckBox.setChecked(false);
                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                    editor.putString("GSTEnable", encrypt("None"));
                    editor.commit();
                } else {
                    //EnableGSTCheckBox.setChecked(true);
//                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
//                    editor.putString("GSTEnable", encrypt("True"));
//                    editor.commit();
                }
            }
        });

    }
}
