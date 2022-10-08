package com.microlan.rushhandingoffline.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.microlan.rushhandingoffline.R;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

public class CustomFilterDialog extends Dialog {

    public Context c;
    public Dialog d;
    EditSpinner QuantityEdit;
    ArrayList<String> QuantityList;
    ArrayAdapter<String> QuantityAdapter;
    ArrayList<String> SizeList, ColorList;
    RadioGroup SizeGroup;
    RadioGroup ColorsLayout;

    public CustomFilterDialog(Context a, ArrayList<String> sizeList, ArrayList<String> colorList){
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.SizeList = sizeList;
        this.ColorList = colorList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filters_dialog);

        QuantityList = new ArrayList<String>();

    }
}
/*
public class CustomFilterDialog extends Dialog {

    public Context c;
    public Dialog d;
    EditSpinner QuantityEdit;
    ArrayList<String> QuantityList;
    ArrayAdapter<String> QuantityAdapter;
    ArrayList<String> SizeList, ColorList;
    RadioGroup SizeGroup;
    RadioGroup ColorsLayout;

    public CustomFilterDialog(Context a, ArrayList<String> sizeList, ArrayList<String> colorList){
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.SizeList = sizeList;
        this.ColorList = colorList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filters_dialog);

        QuantityList = new ArrayList<String>();

    }
}
*/
