package com.microlan.rushhandingoffline.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.Window;

import com.microlan.rushhandingoffline.R;


public class AddQuentity extends Dialog {
    public AddQuentity(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addquentity);


    }

}
