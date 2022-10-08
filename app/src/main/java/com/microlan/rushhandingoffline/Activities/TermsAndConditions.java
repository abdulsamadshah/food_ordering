package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.microlan.rushhandingoffline.R;

public class TermsAndConditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        getSupportActionBar().setTitle("Terms & Conditions");
    }
}
