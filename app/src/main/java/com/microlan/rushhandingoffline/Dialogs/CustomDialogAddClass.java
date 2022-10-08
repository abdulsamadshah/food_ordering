package com.microlan.rushhandingoffline.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.microlan.rushhandingoffline.R;

public class CustomDialogAddClass extends Dialog {
    public Activity c;
    public Dialog d;
    public String Quantity;

    public CustomDialogAddClass (Activity a, String quantity) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.Quantity = quantity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_quantity_dialog);

        TextView QuantityText = (TextView)findViewById(R.id.quantityText);
        QuantityText.setText(Quantity);
    }
}
