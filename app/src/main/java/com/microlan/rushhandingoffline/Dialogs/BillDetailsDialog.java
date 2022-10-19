package com.microlan.rushhandingoffline.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;

import com.microlan.rushhandingoffline.R;

public class BillDetailsDialog extends Dialog {

    public Activity c;
    public Dialog d;

    public BillDetailsDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bill_details_dialog);



    }

}
