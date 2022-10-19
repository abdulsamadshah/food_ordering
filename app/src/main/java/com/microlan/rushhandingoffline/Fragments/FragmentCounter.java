package com.microlan.rushhandingoffline.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.microlan.rushhandingoffline.Activities.BarcodeScan;
import com.microlan.rushhandingoffline.Activities.BillPage;
import com.microlan.rushhandingoffline.Adapters.ProductAdapter;
import com.microlan.rushhandingoffline.Dialogs.CustomDialogAddClass;
import com.microlan.rushhandingoffline.Dialogs.CustomDialogAddCustomer;
import com.microlan.rushhandingoffline.Dialogs.CustomDialogClass;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomer;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomerPoints;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperProductQty;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Views.ExpandableHeightGridView;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class FragmentCounter extends Fragment {

    ExpandableHeightGridView ProductsGrid;
    ArrayList<String> ProductName, Gst, PackSize, Price, Quantity, Unit, QuantityAvailable;
    ProductAdapter productAdapter;
    ImageView BarcodeScanImage, ScanBarcodeImage;
    TextView BarcodeNumberText;
    AutoCompleteTextView SelectCustomerEdit;
    ArrayList<String> CustomersList;
    ArrayAdapter<String> CustomersAdapter;
    LinearLayout AddCustomerLayout;
    TextView AddProductText;
    EditText BarcodeNumberEdit, ProductNameEdit, ProductPriceEdit, ProductQuantityEdit;
    TextView AddNewCutomerText, PointsText;
    LinearLayout ManualProductAddLayout;
    int IsClickedManual = 0;
    CustomDialogClass customDialogClass;
    DatabaseHelper databaseHelper;
    CustomDialogAddClass customDialogAddClass;
    Boolean AddData = false, AddProduct = false, AddPoints = false;
    CustomDialogAddCustomer customDialogAddCustomer;
    Button SubmitBtn;
    DatabaseHelperCustomer databaseHelperCustomer;
    EditText SubTotalEdit, TotalAmountEdit, DiscountEdit;
    DatabaseHelperProductQty databaseHelperProductQty;
    DatabaseHelperCustomerPoints databaseHelperCustomerPoints;
    String PointsValue;
    SharedPreferences sharedPreferences;
    Float CustomerPoints = 0.0f;
    ArrayList<String> TransactionList;
    ArrayAdapter<String> TransactionAdapter;
    EditSpinner TransactionModeSelect;
    float TempTotalAmount = 0;
    String CalculatedGST = "", State;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counters, container, false);

//        AddNewCutomerText = (TextView)rootView.findViewById(R.id.addNewCustomerText);
        ProductsGrid = (ExpandableHeightGridView)rootView.findViewById(R.id.productsGrid);
        BarcodeScanImage = (ImageView)rootView.findViewById(R.id.barcodeScanImage);
        BarcodeNumberText = (TextView)rootView.findViewById(R.id.barcodeNumberEdit);
        PointsText = (TextView)rootView.findViewById(R.id.pointsText);
        SelectCustomerEdit = (AutoCompleteTextView)rootView.findViewById(R.id.selectCustomerEdit);
        AddCustomerLayout = (LinearLayout)rootView.findViewById(R.id.addNewCustomerLayout);
        ManualProductAddLayout = (LinearLayout)rootView.findViewById(R.id.manualProductAdd);
        ScanBarcodeImage = (ImageView)rootView.findViewById(R.id.scanBarcodeImage);
        TransactionModeSelect = (EditSpinner)rootView.findViewById(R.id.transactionMode);
        SubmitBtn = (Button)rootView.findViewById(R.id.submitBtn);

        customDialogClass = new CustomDialogClass(getActivity());

        sharedPreferences = getContext().getSharedPreferences("myPref", MODE_PRIVATE);
        PointsValue = sharedPreferences.getString("point_value", "0");
        State = sharedPreferences.getString("State", encrypt("None"));
        State = decrypt(State);

        AddProductText = (TextView)rootView.findViewById(R.id.addProductText);

        BarcodeNumberEdit = (EditText)rootView.findViewById(R.id.barcodeNumberEdit);
        ProductNameEdit = (EditText) rootView.findViewById(R.id.productIDEdit);
        ProductPriceEdit = (EditText)rootView.findViewById(R.id.productPriceEdit);
        ProductQuantityEdit = (EditText)rootView.findViewById(R.id.quantityEdit);
        DiscountEdit = (EditText)rootView.findViewById(R.id.discountEdit);

        SubTotalEdit = (EditText)rootView.findViewById(R.id.subTotalEdit);
        TotalAmountEdit = (EditText)rootView.findViewById(R.id.totalAmountEdit);

        AddCustomerLayout.setVisibility(View.GONE);

        databaseHelper = new DatabaseHelper(getContext());
        databaseHelperCustomer = new DatabaseHelperCustomer(getContext());
        databaseHelperProductQty = new DatabaseHelperProductQty(getContext());
        databaseHelperCustomerPoints = new DatabaseHelperCustomerPoints(getContext());

        CustomersList = new ArrayList<String>();
        TransactionList = new ArrayList<String>();

        TransactionList.add("Cash");
        TransactionList.add("Debit Card");
        TransactionList.add("Credit Card");
        TransactionList.add("Net Banking");

        TransactionAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, TransactionList);
        TransactionModeSelect.setAdapter(TransactionAdapter);

        try {
            Cursor dataCustomer = databaseHelperCustomer.getData();
            while (dataCustomer.moveToNext()) {
                CustomersList.add(dataCustomer.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

//        CustomersList.add("+ Add New Customer");
//        CustomersList.add("Aman");
//        CustomersList.add("Manoj");
//        CustomersList.add("Sushant");
//        CustomersList.add("Piyush");

        CustomersAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, CustomersList);
        SelectCustomerEdit.setAdapter(CustomersAdapter);

        SelectCustomerEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor dataCustomer = databaseHelperCustomerPoints.getCustomerData(SelectCustomerEdit.getText().toString());
//                while (dataCustomer.moveToNext()){
//                    CustomerPoints = Float.valueOf(dataCustomer.getString(3));
//                }

                String TempCustomerPoints = sharedPreferences.getString("points("+SelectCustomerEdit.getText().toString()+")", encrypt("0"));
                TempCustomerPoints = decrypt(TempCustomerPoints);
                CustomerPoints = Float.valueOf(TempCustomerPoints);

                //Toast.makeText(getContext(), String.valueOf(CustomerPoints), Toast.LENGTH_SHORT).show();
                PointsText.setText("Points Available = "+String.valueOf(Math.round(CustomerPoints)));
            }
        });

        DiscountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (DiscountEdit.getText().toString().isEmpty()){
                    TotalAmountEdit.setText(String.valueOf(TempTotalAmount));
                } else {
                    Float TempPoints = CustomerPoints - Float.valueOf(DiscountEdit.getText().toString());
                    Float TempTotal = Float.valueOf(TotalAmountEdit.getText().toString()) - Float.valueOf(DiscountEdit.getText().toString());
                    if (TempTotal <= 0.0f || TempPoints < 0.0f){
                        DiscountEdit.setText("");
                        DiscountEdit.setSelection(0);
                        Toast.makeText(getContext(), "Sorry You Do Not Have Enough Points.", Toast.LENGTH_SHORT).show();
                        TotalAmountEdit.setText(String.valueOf(TempTotalAmount));
                    } else {
                        TotalAmountEdit.setText(String.valueOf(TempTotal));
                    }
                }
            }
        });

        AddNewCutomerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCustomerEdit.setText("");
                //AddCustomerLayout.setVisibility(View.VISIBLE);
                customDialogAddCustomer = new CustomDialogAddCustomer(getActivity());
                customDialogAddCustomer.show();

                ArrayList<String> StatesList = new ArrayList<String>();
                ArrayAdapter<String> StateAdapter;

                StatesList.add("Jammu & Kashmir");
                StatesList.add("Himachal Pradesh");
                StatesList.add("Punjab");
                StatesList.add("Chandigarh");
                StatesList.add("Uttarakhand");
                StatesList.add("Haryana");
                StatesList.add("Delhi");
                StatesList.add("Rajasthan");
                StatesList.add("Uttar Pradesh");
                StatesList.add("Bihar");
                StatesList.add("Sikkim");
                StatesList.add("Arunachal Pradesh");
                StatesList.add("Nagaland");
                StatesList.add("Manipur");
                StatesList.add("Mizoram");
                StatesList.add("Tripura");
                StatesList.add("Meghalaya");
                StatesList.add("Assam");

                StatesList.add("West Bengal");
                StatesList.add("Jharkhand");
                StatesList.add("Orissa");
                StatesList.add("Chhattisgarh");
                StatesList.add("Madhya Pradesh");
                StatesList.add("Gujarat");
                StatesList.add("Daman & Diu");
                StatesList.add("Dadra & Nagar Haveli");
                StatesList.add("Maharashtra");
                StatesList.add("Andhra Pradesh");
                StatesList.add("Karnataka");
                StatesList.add("Goa");
                StatesList.add("Lakshadweep");
                StatesList.add("Kerala");
                StatesList.add("Tamil Nadu");
                StatesList.add("Puducherry");
                StatesList.add("Andaman & Nicobar Islands");
                StatesList.add("Telengana");
                StatesList.add("Andrapradesh(New)");

                StateAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, StatesList);

                final EditText CustomerNameEdit = (EditText)customDialogAddCustomer.findViewById(R.id.customerNameEdit);
                final EditText CustomerMobileEdit = (EditText)customDialogAddCustomer.findViewById(R.id.customerMobileEdit);
                final EditText CustomerAddressEdit = (EditText)customDialogAddCustomer.findViewById(R.id.customerAddressEdit);
                final EditSpinner CustomerStateEdit = (EditSpinner)customDialogAddCustomer.findViewById(R.id.customerStateEdit);

                CustomerStateEdit.setAdapter(StateAdapter);

                Button SaveButton = (Button)customDialogAddCustomer.findViewById(R.id.saveCustomerBtn);
                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CustomerNameEdit.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Please Enter Customer Name.", Toast.LENGTH_SHORT).show();
                        } else if (CustomerMobileEdit.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Please Enter Customer Mobile.", Toast.LENGTH_SHORT).show();
                        } else if (CustomerAddressEdit.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Please Enter Customer Address.", Toast.LENGTH_SHORT).show();
                        } else if (CustomerStateEdit.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Please Select Customer State.", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean AddCustomer = false;
                            AddCustomer = databaseHelperCustomer.addData(CustomerNameEdit.getText().toString(), CustomerMobileEdit.getText().toString(), CustomerStateEdit.getText().toString());
                            if (AddCustomer){
                                Toast.makeText(getContext(), "Customer Added Successfully.", Toast.LENGTH_SHORT).show();
                                try {
                                    Cursor dataCustomer = databaseHelperCustomer.getData();
                                    while (dataCustomer.moveToNext()) {
                                        CustomersList.add(dataCustomer.getString(1));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                CustomersAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, CustomersList);
                                CustomersAdapter.notifyDataSetChanged();
                                SelectCustomerEdit.setAdapter(CustomersAdapter);

                            } else {
                                Toast.makeText(getContext(), "Failed To Add Customer.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        customDialogAddCustomer.dismiss();
                    }
                });
            }
        });

        SelectCustomerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (SelectCustomerEdit.getText().toString().length() > 0){
                    AddCustomerLayout.setVisibility(View.GONE);
                }
            }
        });

        ScanBarcodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcodeIntent = new Intent(getContext(), BarcodeScan.class);
                startActivityForResult(barcodeIntent, 1);
            }
        });

        ProductName = new ArrayList<String>();
        Gst = new ArrayList<String>();
        PackSize = new ArrayList<String>();
        Price = new ArrayList<String>();
        Quantity = new ArrayList<String>();
        Unit = new ArrayList<String>();
        QuantityAvailable = new ArrayList<String>();

        try {
            Cursor data1 = databaseHelperProductQty.getData();
            while (data1.moveToNext()) {
                QuantityAvailable.add(data1.getString(3));
                Gst.add(data1.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        BarcodeScanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectCustomerEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Select Customer First.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent barcodeIntent = new Intent(getContext(), BarcodeScan.class);
                    startActivityForResult(barcodeIntent, 1);
                }
            }
        });

        ManualProductAddLayout.setVisibility(View.GONE);

        try {
            Cursor data = databaseHelper.getData();
            while (data.moveToNext()) {
                ProductName.add(data.getString(2));
                Price.add(data.getString(3));
                PackSize.add("200 grams");
                Quantity.add("0");
                Unit.add("5");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productAdapter = new ProductAdapter(getContext(), ProductName, Gst, PackSize, Price, Quantity, Unit);
        productAdapter.notifyDataSetChanged();
        ProductsGrid.setAdapter(productAdapter);
        ProductsGrid.setExpanded(true);

        ProductsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (SelectCustomerEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Select Customer First.", Toast.LENGTH_SHORT).show();
                } else {
                    customDialogAddClass = new CustomDialogAddClass(getActivity(), Quantity.get(position));
                    customDialogAddClass.show();
                    ImageView SubtractBtn = (ImageView) customDialogAddClass.findViewById(R.id.subtractQuantBtn);
                    ImageView AddBtn = (ImageView) customDialogAddClass.findViewById(R.id.addQuanBtn);
                    Button SaveQuanBtn = (Button) customDialogAddClass.findViewById(R.id.addQunatity);
                    final EditText QuantityText = (EditText) customDialogAddClass.findViewById(R.id.quantityText);

                    SubtractBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ProductName.get(position).contains("Unit")){
                                Float TempQuan = Float.valueOf(QuantityText.getText().toString());
                                Float TempAvailableQuan = Float.valueOf(QuantityAvailable.get(position));
                                if (TempQuan == 0) {

                                } else {
                                    TempQuan = TempQuan - 0.1f;
                                    QuantityText.setText(String.valueOf(TempQuan));
                                }
                            } else {
                                int TempQuan = Integer.valueOf(QuantityText.getText().toString());
                                int TempAvailableQuan = Integer.valueOf(QuantityAvailable.get(position));
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
                                Float TempAvailableQuan = Float.valueOf(QuantityAvailable.get(position));
                                if (TempQuan >= TempAvailableQuan) {
                                    //Toast.makeText(getContext(), QuantityAvailable.get(position), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Sorry This Much Quantity Not Available In Stock.", Toast.LENGTH_SHORT).show();
                                } else {
                                    TempQuan = TempQuan + 0.1f;
                                    QuantityText.setText(String.valueOf(TempQuan));
                                }
                            } else {
                                int TempQuan = Integer.valueOf(QuantityText.getText().toString());
                                int TempAvailableQuan = Integer.valueOf(QuantityAvailable.get(position));
                                if (TempQuan >= TempAvailableQuan) {
                                    //Toast.makeText(getContext(), QuantityAvailable.get(position), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Sorry This Much Quantity Not Available In Stock.", Toast.LENGTH_SHORT).show();
                                } else {
                                    TempQuan = TempQuan + 1;
                                    QuantityText.setText(String.valueOf(TempQuan));
                                }
                            }
                        }
                    });

                    SaveQuanBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Quantity.set(position, QuantityText.getText().toString());
                            productAdapter = new ProductAdapter(getContext(), ProductName, Gst, PackSize, Price, Quantity, Unit);
                            productAdapter.notifyDataSetChanged();
                            ProductsGrid.setAdapter(productAdapter);
                            ProductsGrid.setExpanded(true);
                            customDialogAddClass.dismiss();


                            TempTotalAmount = TempTotalAmount + (Float.valueOf(QuantityText.getText().toString()) * Float.valueOf(Price.get(position)));
                            SubTotalEdit.setText(String.valueOf(TempTotalAmount));
                            TotalAmountEdit.setText(String.valueOf(TempTotalAmount));
                        }
                    });
                }
            }
        });

        AddProductText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectCustomerEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Select Customer First.", Toast.LENGTH_SHORT).show();
                } else {
                    customDialogClass.show();

                    final ArrayList<String> GSTList = new ArrayList<String>();
                    GSTList.add("5%");
                    GSTList.add("12%");
                    GSTList.add("18%");
                    GSTList.add("28%");
                    ArrayAdapter<String> GSTADapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, GSTList);

                    final EditText BarcodeNumberEdit = (EditText) customDialogClass.findViewById(R.id.barcodeNumberEdit);
                    final EditText ProductNameEdit = (EditText) customDialogClass.findViewById(R.id.productIDEdit);
                    final EditText ProductPriceEdit = (EditText) customDialogClass.findViewById(R.id.productPriceEdit);
                    final EditText ProductQuantityEdit = (EditText) customDialogClass.findViewById(R.id.quantityEdit);
                    final EditText ProductUnitEdit = (EditText)customDialogClass.findViewById(R.id.unitEdit);
                    final EditSpinner ProductGSTEdit = (EditSpinner)customDialogClass.findViewById(R.id.productGSTEdit);
                    final Button SaveProductBtn = (Button) customDialogClass.findViewById(R.id.saveProductBtn);
                    final ImageView BarcodeScanImage = (ImageView) customDialogClass.findViewById(R.id.barcodeScanImage);

                    ProductGSTEdit.setAdapter(GSTADapter);

                    BarcodeScanImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent barcodeIntent = new Intent(getContext(), BarcodeScan.class);
                            startActivityForResult(barcodeIntent, 1);
                        }
                    });

                    SaveProductBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (BarcodeNumberEdit.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "Please Enter Barcode Number.", Toast.LENGTH_SHORT).show();
                            } else if (ProductNameEdit.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "Please Enter Product Name.", Toast.LENGTH_SHORT).show();
                            } else if (ProductPriceEdit.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "Please Enter Product Price.", Toast.LENGTH_SHORT).show();
                            } else if (ProductQuantityEdit.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "Please Enter Product Quantity.", Toast.LENGTH_SHORT).show();
                            } else if (ProductGSTEdit.getText().toString().isEmpty()) {
                                Toast.makeText(getContext(), "Please Select Product GST.", Toast.LENGTH_SHORT).show();
                            } else if (ProductName.contains(ProductNameEdit.getText().toString())){
                                Toast.makeText(getContext(), "Sorry The Product Already Exist.", Toast.LENGTH_SHORT).show();
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

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

                                        // Stuff that updates the UI
                                        productAdapter = new ProductAdapter(getContext(), ProductName, Gst, PackSize, Price, Quantity, Unit);
                                        productAdapter.notifyDataSetChanged();
                                        ProductsGrid.setAdapter(productAdapter);
                                        ProductsGrid.setExpanded(true);
                                    }
                                });

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

                                if (ProductUnitEdit.getText().toString().isEmpty()) {
                                    AddData = databaseHelper.addData(BarcodeNumberEdit.getText().toString(), ProductNameEdit.getText().toString(), ProductPriceEdit.getText().toString());
                                    AddProduct = databaseHelperProductQty.addData(CalculatedGST, ProductNameEdit.getText().toString(), ProductQuantityEdit.getText().toString());
                                } else {
                                    AddData = databaseHelper.addData(BarcodeNumberEdit.getText().toString(), ProductNameEdit.getText().toString()+" Unit:"+ProductUnitEdit.getText().toString(), ProductPriceEdit.getText().toString());
                                    AddProduct = databaseHelperProductQty.addData(CalculatedGST, ProductNameEdit.getText().toString()+" Unit:"+ProductUnitEdit.getText().toString(), ProductQuantityEdit.getText().toString());
                                }

                                customDialogClass.dismiss();
                            }
                        }
                    });
                }

            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SelectCustomerEdit.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please Select Customer First.", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> SrNoList = new ArrayList<String>();
                    ArrayList<String> ProductList = new ArrayList<String>();
                    ArrayList<String> PriceList = new ArrayList<String>();
                    ArrayList<String> QuantityList = new ArrayList<String>();
                    ArrayList<String> GSTList = new ArrayList<String>();

                    for (int i = 0; i < ProductName.size(); i++) {
                        if (Quantity.get(i) == "0") {

                        } else {
                            ProductList.add(ProductName.get(i));
                            PriceList.add(Price.get(i));
                            QuantityList.add(Quantity.get(i));
                            GSTList.add(Gst.get(i));
                        }
                    }

                    Intent BillIntent = new Intent(getContext(), BillPage.class);
                    BillIntent.putExtra("Products", ProductList);
                    BillIntent.putExtra("Prices", PriceList);
                    BillIntent.putExtra("Quantity", QuantityList);
                    BillIntent.putExtra("CustomerName", SelectCustomerEdit.getText().toString());
                    if (DiscountEdit.getText().toString().isEmpty()){
                        BillIntent.putExtra("CustomerPoints", 0.0f);
                    } else {
                        BillIntent.putExtra("CustomerPoints", Float.valueOf(DiscountEdit.getText().toString()));
                    }
                    BillIntent.putExtra("GST", GSTList);
                    startActivity(BillIntent);


                }
            }
        });



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                //Update List
                //BarcodeNumberText.setText(data.getStringExtra("BarcodeValue"));
                ProductName = (ArrayList<String>) data.getSerializableExtra("ProductName");
                Price = (ArrayList<String>) data.getSerializableExtra("Price");
                Quantity = (ArrayList<String>) data.getSerializableExtra("Quantity");
                //Toast.makeText(getContext(), ProductName.toString(), Toast.LENGTH_SHORT).show();

                for (int i = 0; i < ProductName.size(); i++){
                    Gst.add("Rs.5");
                    PackSize.add("200 grams");
                    Unit.add("5");
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // Stuff that updates the UI
                        productAdapter = new ProductAdapter(getContext(), ProductName, Gst, PackSize, Price, Quantity, Unit);
                        productAdapter.notifyDataSetChanged();
                        ProductsGrid.setAdapter(productAdapter);
                        ProductsGrid.setExpanded(true);
                    }
                });


            }

            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}
