package com.microlan.rushhandingoffline.Activities;

import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.microlan.rushhandingoffline.Adapters.ProductAdapter;
import com.microlan.rushhandingoffline.Dialogs.CustomDialogAddClass;
import com.microlan.rushhandingoffline.Dialogs.CustomDialogClass;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperProductQty;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Views.ExpandableHeightGridView;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

public class StockList extends AppCompatActivity {

    ExpandableHeightGridView ProductsGrid;
    ArrayList<String> ProductName, Gst, PackSize, Price, Quantity, Unit, QuantityAvailable;
    ProductAdapter productAdapter;
    DatabaseHelper databaseHelper;
    DatabaseHelperProductQty databaseHelperProductQty;
    EditText AddStockText;
    CustomDialogClass customDialogClass;
    CustomDialogAddClass customDialogAddClass;
    boolean AddData = false, AddProduct = false;
    String CalculatedGST = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);
        getSupportActionBar().setTitle("Stock List");

        customDialogClass = new CustomDialogClass(StockList.this);
        customDialogAddClass = new CustomDialogAddClass(StockList.this, "0");
        databaseHelper = new DatabaseHelper(StockList.this);
        databaseHelperProductQty = new DatabaseHelperProductQty(StockList.this);

        AddStockText = (EditText)findViewById(R.id.addStockText);
        ProductsGrid = (ExpandableHeightGridView)findViewById(R.id.productsGrid);

        ProductName = new ArrayList<String>();
        Gst = new ArrayList<String>();
        PackSize = new ArrayList<String>();
        Price = new ArrayList<String>();
        Quantity = new ArrayList<String>();
        Unit = new ArrayList<String>();
        QuantityAvailable = new ArrayList<String>();

        try {
            Cursor data = databaseHelper.getData();
            while (data.moveToNext()) {
                ProductName.add(data.getString(2));
                Price.add(data.getString(3));

                PackSize.add("200 grams");
                //Quantity.add("0");
                Unit.add("5");
            }

            Cursor data1 = databaseHelperProductQty.getData();
            while (data1.moveToNext()){
                Quantity.add(data1.getString(3));
                Gst.add(data1.getString(1));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        productAdapter = new ProductAdapter(StockList.this, ProductName, Gst, PackSize, Price, Quantity, Unit);
        productAdapter.notifyDataSetChanged();
        ProductsGrid.setAdapter(productAdapter);
        ProductsGrid.setExpanded(true);

        ProductsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                customDialogAddClass.show();
                ImageView SubtractBtn = (ImageView)customDialogAddClass.findViewById(R.id.subtractQuantBtn);
                ImageView AddBtn = (ImageView)customDialogAddClass.findViewById(R.id.addQuanBtn);
                Button SaveQuanBtn = (Button)customDialogAddClass.findViewById(R.id.addQunatity);
                final EditText QuantityText = (EditText) customDialogAddClass.findViewById(R.id.quantityText);
                final Float TempAvailableQuan = Float.valueOf(Quantity.get(position));

                SubtractBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ProductName.get(position).contains("Unit")){
                            Float TempQuan = Float.valueOf(QuantityText.getText().toString());
                            if (TempQuan == 0) {

                            } else {
                                TempQuan = TempQuan - 0.1f;
                                QuantityText.setText(String.valueOf(TempQuan));
                            }
                        } else {
                            int TempQuan = Integer.valueOf(QuantityText.getText().toString());
                            if (TempQuan == 0) {

                            } else {
                                TempQuan = TempQuan - 1;
                                QuantityText.setText(String.valueOf(TempQuan));
                            }
                        }
                    }
                });

                AddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ProductName.get(position).contains("Unit")){
                            Float TempQuan = Float.valueOf(QuantityText.getText().toString());
                            TempQuan = TempQuan + 0.1f;
                            QuantityText.setText(String.valueOf(TempQuan));
                        } else {
                            int TempQuan = Integer.valueOf(QuantityText.getText().toString());
                            TempQuan = TempQuan + 1;
                            QuantityText.setText(String.valueOf(TempQuan));
                        }
                    }
                });

                SaveQuanBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseHelperProductQty.UpdateProduct(ProductName.get(position), String.valueOf(TempAvailableQuan + Float.valueOf(QuantityText.getText().toString())));
                        customDialogAddClass.dismiss();
                        Quantity.set(position, String.valueOf(TempAvailableQuan + Float.valueOf(QuantityText.getText().toString())));
                        productAdapter = new ProductAdapter(StockList.this, ProductName, Gst, PackSize, Price, Quantity, Unit);
                        productAdapter.notifyDataSetChanged();
                        ProductsGrid.setAdapter(productAdapter);
                        ProductsGrid.setExpanded(true);
                    }
                });

            }
        });

        AddStockText.requestFocus();

        AddStockText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogClass.show();

                ArrayList<String> GSTList = new ArrayList<String>();
                ArrayAdapter<String> GSTADapter = new ArrayAdapter<String>(StockList.this, android.R.layout.simple_spinner_dropdown_item, GSTList);

                GSTList.add("5%");
                GSTList.add("12%");
                GSTList.add("18%");
                GSTList.add("28%");

                final EditText BarcodeNumberEdit = (EditText)customDialogClass.findViewById(R.id.barcodeNumberEdit);
                final EditText ProductNameEdit = (EditText)customDialogClass.findViewById(R.id.productIDEdit);
                final EditText ProductPriceEdit = (EditText)customDialogClass.findViewById(R.id.productPriceEdit);
                final EditText ProductQuantityEdit = (EditText)customDialogClass.findViewById(R.id.quantityEdit);
                final EditSpinner ProductGSTEdit = (EditSpinner)customDialogClass.findViewById(R.id.productGSTEdit);
                final Button SaveProductBtn = (Button)customDialogClass.findViewById(R.id.saveProductBtn);
                final ImageView BarcodeScanImage = (ImageView)customDialogClass.findViewById(R.id.barcodeScanImage);

                ProductGSTEdit.setAdapter(GSTADapter);

                BarcodeScanImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent barcodeIntent = new Intent(StockList.this, BarcodeScan.class);
                        startActivityForResult(barcodeIntent, 1);
                    }
                });

                SaveProductBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (BarcodeNumberEdit.getText().toString().isEmpty()) {
                            Toast.makeText(StockList.this, "Please Enter Barcode Number.", Toast.LENGTH_SHORT).show();
                        } else if (ProductNameEdit.getText().toString().isEmpty()) {
                            Toast.makeText(StockList.this, "Please Enter Product Name.", Toast.LENGTH_SHORT).show();
                        } else if (ProductPriceEdit.getText().toString().isEmpty()) {
                            Toast.makeText(StockList.this, "Please Enter Product Price.", Toast.LENGTH_SHORT).show();
                        } else if (ProductQuantityEdit.getText().toString().isEmpty()) {
                            Toast.makeText(StockList.this, "Please Enter Product Quantity.", Toast.LENGTH_SHORT).show();
                        } else if (ProductGSTEdit.getText().toString().isEmpty()) {
                            Toast.makeText(StockList.this, "Please Select Product GST.", Toast.LENGTH_SHORT).show();
                        } else if (ProductName.contains(ProductNameEdit.getText().toString())){
                            Toast.makeText(StockList.this, "Sorry The Product Already Exist.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (ProductGSTEdit.getText().toString().equals("5%")){
                                Gst.add(String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.05)));
                            } else if (ProductGSTEdit.getText().toString().equals("12%")){
                                Gst.add(String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.12)));
                            } else if (ProductGSTEdit.getText().toString().equals("18%")){
                                Gst.add(String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.18)));
                            } else if (ProductGSTEdit.getText().toString().equals("28%")){
                                Gst.add(String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.28)));
                            } else {

                            }
                            ProductName.add(ProductNameEdit.getText().toString());
                            Price.add(ProductPriceEdit.getText().toString());
                            //Gst.add("Rs. 5");
                            PackSize.add("200 grams");
                            Quantity.add(ProductQuantityEdit.getText().toString());
                            Unit.add("5");

                            if (ProductGSTEdit.getText().toString().equals("5%")){
                                CalculatedGST = (String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.05)));
                            } else if (ProductGSTEdit.getText().toString().equals("12%")){
                                CalculatedGST = (String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.12)));
                            } else if (ProductGSTEdit.getText().toString().equals("18%")){
                                CalculatedGST = (String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.18)));
                            } else if (ProductGSTEdit.getText().toString().equals("28%")){
                                CalculatedGST = (String.valueOf((Float.valueOf(ProductPriceEdit.getText().toString()) * 0.28)));
                            } else {

                            }

                            AddData = databaseHelper.addData(BarcodeNumberEdit.getText().toString(), ProductNameEdit.getText().toString(), ProductPriceEdit.getText().toString());
                            AddProduct = databaseHelperProductQty.addData(CalculatedGST, ProductNameEdit.getText().toString(), ProductQuantityEdit.getText().toString());

                            customDialogClass.dismiss();

                            productAdapter = new ProductAdapter(StockList.this, ProductName, Gst, PackSize, Price, Quantity, Unit);
                            productAdapter.notifyDataSetChanged();
                            ProductsGrid.setAdapter(productAdapter);
                            ProductsGrid.setExpanded(true);
                        }
                    }
                });
            }
        });
    }
}
