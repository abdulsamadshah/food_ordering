package com.microlan.rushhandingoffline.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microlan.rushhandingoffline.Adapters.CounterProductRecyclerAdapter;
import com.microlan.rushhandingoffline.BaseURL.ApiClient;
import com.microlan.rushhandingoffline.BaseURL.ApiInterface;
import com.microlan.rushhandingoffline.DB.AllCustomerDBHelper;
import com.microlan.rushhandingoffline.DB.AllGstDBHelper;
import com.microlan.rushhandingoffline.DB.AllStateCodeDBHelper;
import com.microlan.rushhandingoffline.DB.CustomerAddressDBHelper;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.DBAdapter.CounterAdapter;
import com.microlan.rushhandingoffline.Dialogs.BillDetailsDialog;
import com.microlan.rushhandingoffline.Dialogs.CustomDialogAddCustomer;
import com.microlan.rushhandingoffline.OfflineModel.ALLCustomerModel;
import com.microlan.rushhandingoffline.OfflineModel.AllCustomerAddressModel;
import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;
import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;
import com.microlan.rushhandingoffline.OfflineModel.StateModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.UserAddressItem;
import com.microlan.rushhandingoffline.model.UserAddressResponse;
import com.reginald.editspinner.EditSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class ItemInCartOffline extends AppCompatActivity {

    ItemInCartDBHelper dbAddItemHelpers;

    AllCustomerDBHelper dbAddCustomer;
    CustomerAddressDBHelper dbcustomeraddress;
    AllStateCodeDBHelper addstate;
    ArrayList<ItemInCardModel> arrayListitem;
    ArrayList<ALLCustomerModel> arraycustomerListitem;
    ArrayList<AllCustomerAddressModel> arrayaddressListitem;
    ArrayList<String> CartID, CartProductID, CartProductName, CartProductImage, CartProductQuan, CartProductPrice, CartUnitTotal, CArtPAckSize, Cartprice, CartColor, CartSize, CartpackUnit,CartProductcode;
    RecyclerView ProductsInCounterGrid;
    CounterProductRecyclerAdapter counterProductRecyclerAdapter;
    String Intro = "", UserID, session_id;
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    Button save;
    AutoCompleteTextView CustomerEdit;
    CustomDialogAddCustomer customDialogAddCustomer;
    EditSpinner PaymentTypeEdit;
    TextView CustomerPointsText, CustomerWalletText;
    EditText CustomerPointsEdit, CustomerWalletEdit;
    BillDetailsDialog billDetailsDialog;
    List<UserAddressItem> userAddress;
    RecyclerView addressracycler;
    String customerid,SeletedAddress="";
    RadioButton price,discount,discountyes;
    String countvac="0",dicountlist="0",extra_rate="0",CustomerAddresss,totalweight,balance_amt;
    Float TotalAmount = Float.valueOf(String.valueOf(0.0));
    Float AfterDicount,Afterdicountamt,CartTotalAmt;
    ProgressDialog progressDialog;
    String TotalWallet;
    ArrayAdapter<String> StateAdapter;

    TextView TotalAmountText;
    ArrayList<String> CustomerID, CustomerUniqe,CustomerName, CustomerAddress, StatesList, CustomerState;
    double CartSGSTAmount = 0.0;
    double CartIGSTAmount = 0.0;
    double CartCGSTAmount = 0.0;
    TextView grandTotalText;
    Float sum = Float.valueOf(String.valueOf(0.0));
    String CustomerStateString="",customeruniqrid;
    String SelectedStatecode,HascodeStatecode;
    String SelectedStateName,State_code;
    ArrayList<String > Statename,StateCode,Addstatename,Addstatecode;
    AllGstDBHelper dbgstHelpers;
    ArrayList<AllGstModel> arrayListgst;
    ArrayList<StateModel> arraystatecode;

    Spinner CustomerStateEdit;

    ArrayList<String> SGSTLIST,CGSTLIST,CESSLIST,IGSTLIST;
    String GstType="I";
    String subtotal;
    ImageView back;
    double GstTotal=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_in_cart_offline);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        session_id = sharedPreferences.getString("session_id", "");
        State_code = sharedPreferences.getString("state_code", "27");
        Intro = decrypt(Intro);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Log.d("","UserID"+UserID);
        Log.d("","State_code"+State_code);

        dbAddItemHelpers = new ItemInCartDBHelper(getApplicationContext());
        dbAddCustomer = new AllCustomerDBHelper(getApplicationContext());
        dbcustomeraddress = new CustomerAddressDBHelper(getApplicationContext());
        dbgstHelpers = new AllGstDBHelper(getApplicationContext());
        addstate = new AllStateCodeDBHelper(getApplicationContext());


        Log.d("","arraycustomerListitem"+arraycustomerListitem);

        billDetailsDialog = new BillDetailsDialog(ItemInCartOffline.this);
        customDialogAddCustomer = new CustomDialogAddCustomer(ItemInCartOffline.this);

        grandTotalText=findViewById(R.id.grandTotalText);
        ProductsInCounterGrid = (RecyclerView) findViewById(R.id.productsInCounterGrid);
        save=findViewById(R.id.save);
        back=findViewById(R.id.back);


        CustomerID = new ArrayList<String>();
        CustomerUniqe = new ArrayList<String>();
        CustomerName = new ArrayList<String>();
        CustomerAddress = new ArrayList<String>();
        CustomerState = new ArrayList<String>();
        Addstatename = new ArrayList<String>();
        Addstatecode = new ArrayList<String>();
        SGSTLIST = new ArrayList<String>();
        CGSTLIST = new ArrayList<String>();
        CESSLIST = new ArrayList<String>();
        IGSTLIST = new ArrayList<String>();



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OfflineCounter.class);
                startActivity(intent);
                finish();
            }
        });




        StateCode = new ArrayList<String>();
        StatesList = new ArrayList<String>();

        StatesList.clear();
        StateCode.clear();

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


        StateCode.add("01");
        StateCode.add("02");
        StateCode.add("03");
        StateCode.add("04");
        StateCode.add("05");
        StateCode.add("06");
        StateCode.add("07");
        StateCode.add("08");
        StateCode.add("09");
        StateCode.add("10");
        StateCode.add("11");
        StateCode.add("12");
        StateCode.add("13");
        StateCode.add("14");
        StateCode.add("15");
        StateCode.add("16");
        StateCode.add("17");
        StateCode.add("18");
        StateCode.add("19");
        StateCode.add("20");
        StateCode.add("21");
        StateCode.add("22");
        StateCode.add("23");
        StateCode.add("24");
        StateCode.add("25");
        StateCode.add("26");
        StateCode.add("27");
        StateCode.add("28");
        StateCode.add("29");
        StateCode.add("30");
        StateCode.add("31");
        StateCode.add("32");
        StateCode.add("33");
        StateCode.add("34");
        StateCode.add("35");
        StateCode.add("36");
        StateCode.add("37");

        arraystatecode = addstate.getallstae();

        if(arraystatecode==null || arraystatecode.isEmpty() || arraystatecode.size()==0)
        {
            GetAllstate();

        }
        else {

            getstate();

        }


        Log.d("arrayListcatogery","arrayListcatogery"+arrayListitem);

        CartID = new ArrayList<String>();
        CartProductID = new ArrayList<String>();
        CartProductName = new ArrayList<String>();
        CartProductImage = new ArrayList<String>();
        CartProductQuan = new ArrayList<String>();
        CartProductPrice = new ArrayList<String>();
        CartUnitTotal = new ArrayList<String>();
        CArtPAckSize = new ArrayList<String>();
        Cartprice = new ArrayList<String>();
        CartColor = new ArrayList<String>();
        CartSize = new ArrayList<String>();
        CartpackUnit = new ArrayList<String>();
        CartProductcode = new ArrayList<String>();


        getCartitem();
        final List<StateModel> list = new ArrayList<StateModel>();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtotal= String.valueOf(Float.valueOf(grandTotalText.getText().toString()));
/*
                double totalsgt=0.0;
                double totaligst=0.0;
                double totalcgst=0.0;
                TotalAmount = Float.valueOf(grandTotalText.getText().toString());
                 CartSGSTAmount = 0.0;
                 CartIGSTAmount = 0.0;
                 CartCGSTAmount = 0.0;
                GstTotal=0.0;
                for(int i=0;i<CartProductID.size();i++){

                    int code= Integer.parseInt((CartProductcode.get(i)));
                    Float total= Float.parseFloat(CartUnitTotal.get(i));
                    Log.d("","code"+code);
                    arrayListgst = dbgstHelpers.getallgst(code);

                    Log.d("","arrayListgst"+arrayListgst.size());
                    if(arrayListgst.size()==0)
                    {
                        CartSGSTAmount= CartSGSTAmount+0;
                        CartCGSTAmount= CartCGSTAmount+0;
                        CartIGSTAmount= CartIGSTAmount+0;

                    }
                    else {
                        Float CESS = Float.valueOf(arrayListgst.get(0).getcESS());
                        Float CGST = Float.valueOf(arrayListgst.get(0).getcGST());
                        Float IGST = Float.valueOf(arrayListgst.get(0).getiGST());
                        Float SGST = Float.valueOf(arrayListgst.get(0).getsGST());

                        GstType=arrayListgst.get(0).getGstType();



                        if(State_code.equals("27"))
                        {

                            Float sgst=((total*100)/(100+IGST))*SGST/100;
                            Float cgst=((total*100)/(100+IGST))*CGST/100;
                            int igst=0;
                            totalsgt=totalsgt+sgst;
                            totalcgst=totalcgst+cgst;

                            Log.d("","igst"+sgst);
                            Log.d("","igstcgst"+cgst);

                            DecimalFormat df = new DecimalFormat("0.00");
                            double SGSTAmount= Double.parseDouble(String.valueOf((df.format(totalsgt))));
                            double CGSTAmount= Double.parseDouble(String.valueOf(df.format(totalcgst)));
                            double GSTAmount= Double.parseDouble(String.valueOf(df.format(totaligst)));



                            CartSGSTAmount= SGSTAmount;
                            CartCGSTAmount= CGSTAmount;
                            CartIGSTAmount= GSTAmount;

                            Log.d("","CartCGSTAmount"+CartCGSTAmount);
                            Log.d("","SGSTAmount"+SGSTAmount);
                        }
                        else {
                            Float igst=((total*100)/(100+IGST))*IGST/100;
                            int cgst=0;
                            int sgst=0;

                            totaligst=totaligst+igst;

                            DecimalFormat df = new DecimalFormat("0.00");
                            double SGSTAmount= Double.parseDouble(String.valueOf((df.format(totalsgt))));
                            double CGSTAmount= Double.parseDouble(String.valueOf(df.format(totalcgst)));
                            double GSTAmount= Double.parseDouble(String.valueOf(df.format(totaligst)));
                            Log.d("","igst"+igst);


                            CartSGSTAmount= SGSTAmount;
                            CartCGSTAmount= CGSTAmount;
                            CartIGSTAmount= GSTAmount;

                        }


                    }

                    //  }

                }

                if(GstType.equals("E"))
                {
                    double cst= (CartCGSTAmount);
                    double sst= (CartSGSTAmount);
                    double ist= (CartIGSTAmount);
                     GstTotal=cst+sst+ist;
                    Log.d("","GstTotal E "+GstTotal);

                    Double sub= Double.valueOf(Float.valueOf(TotalAmount)+GstTotal);

                    Log.d("","subsub"+sub);
                    DecimalFormat df = new DecimalFormat("0.00");

                    subtotal= String.valueOf((df.format(TotalAmount)));

                    TotalAmount= Float.valueOf(""+sub);

                    Log.d("","subtotal"+TotalAmount);
                    Log.d("","subtotal"+subtotal);
                }
                else {
                    double cst= (CartCGSTAmount);
                    double sst= (CartSGSTAmount);
                    double ist= (CartIGSTAmount);
                     GstTotal=cst+sst+ist;
                    Log.d("","GstTotal "+GstTotal);
                    Double sub= Double.valueOf(Float.valueOf(TotalAmount)-GstTotal);
                    DecimalFormat df = new DecimalFormat("0.00");


                    subtotal= String.valueOf((df.format(sub)));

                }


*/

                if (CartProductID.isEmpty()) {
                    Toast.makeText(ItemInCartOffline.this, "Please Add Products To Cart.", Toast.LENGTH_SHORT).show();
                }
                else {
                    billDetailsDialog.show();


                arraycustomerListitem = dbAddCustomer.getallcustomer();

                TextView AddCustomerText = (TextView) billDetailsDialog.findViewById(R.id.addNewCustomerText);
                    TextView addnewaddress = (TextView) billDetailsDialog.findViewById(R.id.addnewaddress);
                    CustomerEdit = (AutoCompleteTextView) billDetailsDialog.findViewById(R.id.customersEdit);
                    PaymentTypeEdit = (EditSpinner) billDetailsDialog.findViewById(R.id.paymentTypeEdit);
                    EditText OfferCodeEdit = (EditText) billDetailsDialog.findViewById(R.id.offerCodeEdit);
                    CustomerWalletText = (TextView) billDetailsDialog.findViewById(R.id.walletBalanceText);
                    CustomerWalletEdit = (EditText) billDetailsDialog.findViewById(R.id.walletPointsEdit);
                    final TextView cart_total = (TextView) billDetailsDialog.findViewById(R.id.cart_total);
                    final TextView gst_total = (TextView) billDetailsDialog.findViewById(R.id.gst_total);

                    final LinearLayout addaddresslay = (LinearLayout) billDetailsDialog.findViewById(R.id.addaddresslay);
                    final EditText DiscountEdit = (EditText) billDetailsDialog.findViewById(R.id.discountEdit);
                    final EditText perdiscountEdit = (EditText) billDetailsDialog.findViewById(R.id.perdiscountEdit);

                    addressracycler = billDetailsDialog.findViewById(R.id.addressracycler);
                    addressracycler.setLayoutManager(new LinearLayoutManager(ItemInCartOffline.this));


                    final TextView    BalanceAmount = (TextView) billDetailsDialog.findViewById(R.id.OutstandingEdit);
                    price = (RadioButton)billDetailsDialog.findViewById(R.id.price);
                    discount = (RadioButton)billDetailsDialog. findViewById(R.id.discount);


                    Log.d("arraycustomerListitem","arraycustomerListitem"+TotalAmount);
                    getCustomer();

                    final TextView after_dis=billDetailsDialog.findViewById(R.id.after_dis);
                    final TextView after_dis_per=billDetailsDialog.findViewById(R.id.after_dis_per);
                    final TextView sumtotal=billDetailsDialog.findViewById(R.id.sumtotal);

                    Float total= Float.parseFloat(grandTotalText.getText().toString());
                    cart_total.setText(""+TotalAmount);
                    gst_total.setText(""+GstTotal);

                    addnewaddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addaddresslay.setVisibility(View.VISIBLE);
                        }
                    });
                    ArrayList<String> paymentList = new ArrayList<String>();
                    paymentList.add("Cash");
                    paymentList.add("Wallet");
                    paymentList.add("Credit Card");
                    paymentList.add("Debit Card");
                    paymentList.add("NetBanking");
                    ArrayAdapter<String> paymentAdapter = new ArrayAdapter<String>(ItemInCartOffline.this, android.R.layout.simple_spinner_dropdown_item, paymentList);
                    PaymentTypeEdit.setAdapter(paymentAdapter);

                    final CheckBox DeliveryCheck = (CheckBox) billDetailsDialog.findViewById(R.id.deliveryCheck);
                    final LinearLayout DeliveryLayout = (LinearLayout) billDetailsDialog.findViewById(R.id.deliveryLayout);
                    Button SaveBtnDialog = (Button) billDetailsDialog.findViewById(R.id.saveBtn);

                    DeliveryLayout.setVisibility(View.GONE);
                    BalanceAmount.setText(""+TotalAmount);
                    sumtotal.setText(""+TotalAmount);

                    price.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            countvac="1";
                            perdiscountEdit.setVisibility(View.GONE);
                            DiscountEdit.setVisibility(View.VISIBLE);
                            BalanceAmount.setText(""+TotalAmount);
                            DiscountEdit.setText("");

                        }
                    });
                    discount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            countvac="2";
                            perdiscountEdit.setVisibility(View.VISIBLE);
                            DiscountEdit.setVisibility(View.GONE);
                            BalanceAmount.setText(""+TotalAmount);

                            perdiscountEdit.setText("");
                        }
                    });

                    perdiscountEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)


                        {
                            if (perdiscountEdit.getText().toString().isEmpty()){
                                after_dis.setText("0");

                                Log.d("fdd","after_dis.getText().toString()"+after_dis.getText().toString());
                                BalanceAmount.setText(String.valueOf(TotalAmount));
                                sumtotal.setText(""+TotalAmount);


                            } else {
                                if (Integer.valueOf(perdiscountEdit.getText().toString()) > 0) {

                                    Log.d("test","price"+cart_total.getText().toString());
                                    Float percentage=Float.valueOf(String.valueOf(TotalAmount));
                                    Float dec=percentage/100;
                                    Float total=dec*Float.valueOf(perdiscountEdit.getText().toString());

                                    if(Integer.valueOf(perdiscountEdit.getText().toString())>100)
                                    {
                                        Toast.makeText(getApplicationContext(),"Please Enter Valide Discount .",
                                                Toast.LENGTH_SHORT).show();

                                        perdiscountEdit.setText(perdiscountEdit.getText().toString().substring(0, perdiscountEdit.getText().toString().length()-1));

                                    }
                                    else {

                                        Float amt = Float.parseFloat(String.valueOf(TotalAmount));
                                        Float amount=amt-total;
                                        after_dis_per.setText(""+total);

                                        DecimalFormat df = new DecimalFormat("0.00");
                                        double Amount= Double.parseDouble(String.valueOf((df.format(amount))));

                                        BalanceAmount.setText(""+Amount);
                                        sumtotal.setText(""+Amount);

                                    }

                                } else {
                                    after_dis.setText("0");

                                    Log.d("","after_dis"+after_dis.getText().toString());
                                    BalanceAmount.setText(String.valueOf(Float.valueOf(TotalAmount)));

                                    sumtotal.setText(""+TotalAmount);

                                }


                            }



                        }



                        @Override
                        public void afterTextChanged(Editable s) {


                        }
                    });

                    DiscountEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            if (DiscountEdit.getText().toString().isEmpty()){
                                after_dis.setText("0");
                                BalanceAmount.setText(String.valueOf(TotalAmount));

                                sumtotal.setText(""+TotalAmount);

                            } else {
                                if (Integer.valueOf(DiscountEdit.getText().toString()) > Float.parseFloat(String.valueOf(TotalAmount))){
                                    Toast.makeText(ItemInCartOffline.this, "Sorry Advanced Amount Can Not Be Greater than Total Amount.", Toast.LENGTH_SHORT).show();
                                    DiscountEdit.setText(DiscountEdit.getText().toString().substring(0, DiscountEdit.getText().toString().length()-1));
                                } else {
                                    if (Integer.valueOf(DiscountEdit.getText().toString()) >= 0){
                                        after_dis.setText(DiscountEdit.getText().toString());

                                        AfterDicount=(Float.parseFloat(String.valueOf(TotalAmount)) -
                                                Float.parseFloat(after_dis.getText().toString()));

                                        DecimalFormat df = new DecimalFormat("0.00");
                                        double Amount= Double.parseDouble(String.valueOf((df.format(AfterDicount))));

                                        BalanceAmount.setText(""+Amount);
                                        sumtotal.setText(""+Amount);

                                    } else {

                                        BalanceAmount.setText(""+(Float.valueOf(TotalAmount)));
                                        after_dis.setText("0");
                                        sumtotal.setText(""+TotalAmount);

                                    }
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {

                        }
                    });



                DeliveryCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if(customerid==null|| customerid.equals("")||customerid.isEmpty())
                            {
                                Toast.makeText(getApplicationContext(),"Please Select Customer",Toast.LENGTH_SHORT).show();
                                DeliveryCheck.setChecked(false);
                            }
                            else {
                                if (isChecked) {
                                    arrayaddressListitem = dbcustomeraddress.getalladdress(customerid);

                                    Log.d("","arrayaddressListitem.size()"+arrayaddressListitem.size());
                                    if(arrayaddressListitem.size()==0)
                                    {
                                        Toast.makeText(getApplicationContext(),"Address Not Available",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        addressracycler.setAdapter(new AddressAdapterlist());

                                    }



                                    DeliveryLayout.setVisibility(View.VISIBLE);
                                } else {
                                    DeliveryLayout.setVisibility(View.GONE);
                                }
                            }


                        }
                    });

                    CustomerEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int TempCust = CustomerName.indexOf(CustomerEdit.getText().toString());
                            CustomerStateString = CustomerState.get(position);
                            customerid = CustomerID.get(TempCust);
                            customeruniqrid = CustomerUniqe.get(TempCust);
                          //  GetCustomerPoints(customerid);



                        }
                    });

//
                    CustomerWalletEdit.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String dicount = null;
                            Log.d("","dicount"+dicount);
                            if (CustomerWalletEdit.getText().toString().isEmpty()){
                                BalanceAmount.setText(String.valueOf(sumtotal.getText().toString()));
                            } else {
                                Log.d("","sumtotal"+sumtotal.getText().toString());
                                if (Float.valueOf(CustomerWalletEdit.getText().toString()) > Float.valueOf(BalanceAmount.getText().toString())){
                                    Toast.makeText(ItemInCartOffline.this, "Sorry Advanced Amount Can Not Be Greater than Total Amount.", Toast.LENGTH_SHORT).show();
                                    CustomerWalletEdit.setText(CustomerWalletEdit.getText().toString().substring(0, CustomerWalletEdit.getText().toString().length()-1));
                                } else {
                                    if (Float.valueOf(CustomerWalletEdit.getText().toString()) >= Float.valueOf(TotalWallet)){

                                        BalanceAmount.setText(String.valueOf(Float.valueOf(sumtotal.getText().toString()) -
                                                Float.valueOf(CustomerWalletEdit.getText().toString())));

                                        Float ab =Float.valueOf(CustomerWalletText.getText().toString())-Float.valueOf(CustomerWalletEdit.getText().toString());

                                    } else {
                                        BalanceAmount.setText(String.valueOf(Float.valueOf(sumtotal.getText().toString()) -
                                                Float.valueOf(CustomerWalletEdit.getText().toString())));


                                    }
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {

                        }
                    });

                    addnewaddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialogAddCustomer.show();

                            final EditText CustomerNameEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerNameEdit);
                            final EditText CustomerMobileEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerMobileEdit);
                            final EditText CustomerEmailEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerEmailEdit);
                            final EditText CustomerAddressEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerAddressEdit);
                            final EditText CustomerNearAreaEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerNearAreaEdit);
                            final EditText CustomerCityEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerCityEdit);
                            CustomerStateEdit = (Spinner) customDialogAddCustomer.findViewById(R.id.customerStateEdit);
                            final EditText CustomerPincodeEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerPincodeEdit);
                            final Button SaveBtn = (Button) customDialogAddCustomer.findViewById(R.id.saveCustomerBtn);
                            final  LinearLayout addresslayout=customDialogAddCustomer.findViewById(R.id.addresslayout);
                            addresslayout.setVisibility(View.VISIBLE);
                            CustomerStateEdit.setAdapter(StateAdapter);

                            CustomerStateEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    SelectedStatecode = (Addstatecode.get(position));//This will be the student id.
                                    SelectedStateName=Addstatename.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            SaveBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (CustomerNameEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Name Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerMobileEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Mobile Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerMobileEdit.getText().toString().length()<10) {
                                        Toast.makeText(ItemInCartOffline.this, "Please Enter Proper No.", Toast.LENGTH_SHORT).show();
                                    }else if (CustomerMobileEdit.getText().toString().length()<10) {
                                        Toast.makeText(ItemInCartOffline.this, "Please Enter Proper No.", Toast.LENGTH_SHORT).show();
                                    }else if (CustomerAddressEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Address Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerNearAreaEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Near Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerCityEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer City Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    } /*else if (CustomerStateEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer State Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    }*/ else if (CustomerPincodeEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Pincode Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (CustomerEmailEdit.getText().toString().isEmpty()) {

                                                AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(CustomerCityEdit.getText().toString(),customerid,CustomerPincodeEdit.getText().toString(),CustomerNearAreaEdit.getText().toString(),CustomerNameEdit.getText().toString(),CustomerEmailEdit.getText().toString(),"","",CustomerAddressEdit.getText().toString(),SelectedStateName,CustomerMobileEdit.getText().toString(),SelectedStatecode,"0",customeruniqrid);
                                            dbcustomeraddress.addaddress(recordingItem);

                                           /* arrayaddressListitem = dbcustomeraddress.getalladdress(customerid);
                                            addressracycler.setAdapter(new AddressAdapterlist());
                                           */ customDialogAddCustomer.cancel();
                                        } else {

                                            AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(CustomerCityEdit.getText().toString(),customerid,CustomerPincodeEdit.getText().toString(),CustomerNearAreaEdit.getText().toString(),CustomerNameEdit.getText().toString(),CustomerEmailEdit.getText().toString(),"","",CustomerAddressEdit.getText().toString(),SelectedStateName,CustomerMobileEdit.getText().toString(),SelectedStatecode,"0",customeruniqrid);
                                            dbcustomeraddress.addaddress(recordingItem);

                                          /*  arrayaddressListitem = dbcustomeraddress.getalladdress(customerid);
                                            addressracycler.setAdapter(new AddressAdapterlist());
                                          */  customDialogAddCustomer.cancel();
                                        }
                                    }
                                }
                            });
                        }

                    });


                    SaveBtnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (CustomerEdit.getText().toString().isEmpty()) {
                                Toast.makeText(ItemInCartOffline.this, "Please Enter Customer Name.", Toast.LENGTH_SHORT).show();
                            } else if (PaymentTypeEdit.getText().toString().isEmpty()) {
                                Toast.makeText(ItemInCartOffline.this, "Please Select Payment Type.", Toast.LENGTH_SHORT).show();
                            } else
                                {

                                    {





                                        Log.d("","CartCGSTAmountsss "+CartCGSTAmount);

                                        Intent productBillIntent = new Intent(ItemInCartOffline.this, BillPage.class);
                                            productBillIntent.putExtra("CustomerName", CustomerEdit.getText().toString());
                                            productBillIntent.putExtra("customerid", customerid);
                                            productBillIntent.putExtra("cartid", CartID);
                                            productBillIntent.putExtra("Productid", CartProductID);
                                            productBillIntent.putExtra("Products", CartProductName);
                                            productBillIntent.putExtra("Prices", CartProductPrice);
                                            productBillIntent.putExtra("Quantity", CartProductQuan);
                                            productBillIntent.putExtra("Amount", CartUnitTotal);
                                            productBillIntent.putExtra("SubTotal", subtotal);
                                            productBillIntent.putExtra("SGST", String.valueOf(CartSGSTAmount));
                                            productBillIntent.putExtra("IGST", String.valueOf(CartIGSTAmount));
                                            productBillIntent.putExtra("CGST", String.valueOf(CartCGSTAmount));
                                            productBillIntent.putExtra("GSTTYPE", GstType);
                                            productBillIntent.putExtra("packsize", CArtPAckSize);
                                            productBillIntent.putExtra("color", CartColor);
                                            productBillIntent.putExtra("size", CartSize);
                                            productBillIntent.putExtra("unit", CartpackUnit);
                                            productBillIntent.putExtra("Payment", PaymentTypeEdit.getText().toString());
                                            productBillIntent.putExtra("TotalAmount", String.valueOf(TotalAmount));
                                            productBillIntent.putExtra("CustomerState", CustomerStateString);
                                            productBillIntent.putExtra("CustomerAddress", SeletedAddress);
                                            productBillIntent.putExtra("dicount_type", countvac);
                                            productBillIntent.putExtra("balanceamt", BalanceAmount.getText().toString());
                                            productBillIntent.putExtra("walletbalance", CustomerWalletEdit.getText().toString());

                                            if(countvac.equals("1"))
                                            {
                                                productBillIntent.putExtra("dicount", DiscountEdit.getText().toString());

                                            }
                                            else if(countvac.equals("2"))
                                            {
                                                productBillIntent.putExtra("dicount", after_dis_per.getText().toString());

                                            }
                                            else {
                                                productBillIntent.putExtra("dicount", "0");

                                            }

                                            startActivity(productBillIntent);
                                            finish();

                                        }
                            }
                        }
                    });

                    AddCustomerText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialogAddCustomer.show();

                            final EditText CustomerNameEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerNameEdit);
                            final EditText CustomerMobileEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerMobileEdit);
                            final EditText CustomerEmailEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerEmailEdit);
                            final EditText CustomerAddressEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerAddressEdit);
                            final EditText CustomerNearAreaEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerNearAreaEdit);
                            final EditText CustomerCityEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerCityEdit);
                            CustomerStateEdit = (Spinner) customDialogAddCustomer.findViewById(R.id.customerStateEdit);
                            final EditText CustomerPincodeEdit = (EditText) customDialogAddCustomer.findViewById(R.id.customerPincodeEdit);
                            final Button SaveBtn = (Button) customDialogAddCustomer.findViewById(R.id.saveCustomerBtn);

                            CustomerStateEdit.setAdapter(StateAdapter);


                            CustomerStateEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    SelectedStatecode = (Addstatecode.get(position));//This will be the student id.
                                    SelectedStateName = Addstatename.get(position);
                                    Log.d("","State code ids "+SelectedStatecode);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            SaveBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (CustomerNameEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Name Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerMobileEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Mobile Not Entered.", Toast.LENGTH_SHORT).show();
                                    }/* else if (CustomerAddressEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Address Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerNearAreaEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Near Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    } else if (CustomerCityEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer City Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    } *//*else if (CustomerStateEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer State Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    }*//* else if (CustomerPincodeEdit.getText().toString().isEmpty()) {
                                        Toast.makeText(ItemInCartOffline.this, "Customer Pincode Area Not Entered.", Toast.LENGTH_SHORT).show();
                                    }*/ else {
                                        DateFormat df = new SimpleDateFormat("HmsdMy", Locale.getDefault());
                                        String currentDateAndTime = df.format(new Date());
                                        Log.d("dates","currentTime"+currentDateAndTime);

                                        String uniqeid=UserID+currentDateAndTime;

                                        if (CustomerEmailEdit.getText().toString().isEmpty()) {


                                            ALLCustomerModel recordingItem = new ALLCustomerModel(CustomerNameEdit.getText().toString(),CustomerMobileEdit.getText().toString(),CustomerEmailEdit.getText().toString(),CustomerAddressEdit.getText().toString(),CustomerNearAreaEdit.getText().toString(),CustomerCityEdit.getText().toString(),SelectedStateName,CustomerPincodeEdit.getText().toString(),SelectedStatecode,"0",uniqeid);
                                            dbAddCustomer.addcustomer(recordingItem);

                                            getCustomer();
                                            customDialogAddCustomer.cancel();
                                        } else {
                                            ALLCustomerModel recordingItem = new ALLCustomerModel(CustomerNameEdit.getText().toString(),CustomerMobileEdit.getText().toString(),"",CustomerAddressEdit.getText().toString(),CustomerNearAreaEdit.getText().toString(),CustomerCityEdit.getText().toString(),SelectedStateName,CustomerPincodeEdit.getText().toString(),SelectedStatecode,"0",uniqeid);
                                            dbAddCustomer.addcustomer(recordingItem);

                                            getCustomer();

                                            customDialogAddCustomer.cancel();
                                        }
                                    }
                                }
                            });


                        }
                    });


                }

                }
        });

    }

    private void getstate() {


        for(int i = 0;i<arraystatecode.size();i++) {
            String name = arraystatecode.get(i).getState();
            String id = arraystatecode.get(i).getStatecode();

            Addstatename.add(name);
            Addstatecode .add(id);


            StateAdapter = new ArrayAdapter<String>(ItemInCartOffline.this, android.R.layout.simple_spinner_dropdown_item, Addstatename);




        }


    }

    private void GetAllstate() {

        for(int i=0;i<StateCode.size();i++)
        {

            String ab=StateCode.get(i);
            String ac=StatesList.get(i);
            Log.d("","State code"+ab);
            Log.d("","State code"+ac);

            StateModel recordingItem = new StateModel( ac,ab);

            addstate.addstate(recordingItem);

        }
        getstate();

    }



    public void GetCustomerPoints(final String UserIDs) {
        progressDialog = new ProgressDialog(ItemInCartOffline.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        //Toast.makeText(ItemInCart.this, CategoryID, Toast.LENGTH_SHORT).show();

        String insertUrl = "http://microlanpos.com/demo/api2/wallet_balance";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                TotalWallet=response;

                Log.d("sfdfdf", "Wallet Balance" + response);
                CustomerWalletText.setText("Wallet Balance : " + response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ItemInCartOffline.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", UserIDs);
                Log.d("fddfdf","UserIDs"+UserIDs);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }




    private void getCustomer() {

        arraycustomerListitem = dbAddCustomer.getallcustomer();

        Log.d("","arraycustomerListitem"+arraycustomerListitem.size());
        CustomerID.clear();
        CustomerName.clear();
        CustomerAddress.clear();
        CustomerState.clear();
        CustomerUniqe.clear();

        for (int i = 0; i < arraycustomerListitem.size(); i++) {

                String customerIDString = arraycustomerListitem.get(i).getCustomerid();
                String customerNameString = arraycustomerListitem.get(i).getCustomername();
                String customerAddress1String = arraycustomerListitem.get(i).getAddress1();
                String customerAddress2String = arraycustomerListitem.get(i).getAddress2();
                String customerCityString = arraycustomerListitem.get(i).getCity();
                String customerMobileString = arraycustomerListitem.get(i).getCustomernumber();
                String customerStateString = arraycustomerListitem.get(i).getState();
                String customerPencodeString = arraycustomerListitem.get(i).getPincode();
                String flags = arraycustomerListitem.get(i).getFlag();
                String uniqe = arraycustomerListitem.get(i).getUniqeid();


                CustomerID.add(customerIDString );
                CustomerUniqe.add(uniqe);
                CustomerName.add(customerNameString + " ( " + customerMobileString + " ) ");
                CustomerAddress.add(customerAddress1String + ", " + customerAddress2String + ", " + customerCityString);
                CustomerState.add(customerStateString +"  ,  "+customerPencodeString);



            }
        Log.d("","CustomerName"+CustomerName.size());

            ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(ItemInCartOffline.this, android.R.layout.simple_spinner_dropdown_item, CustomerName);
            //customerAdapter.notifyDataSetChanged();
            CustomerEdit.setAdapter(customerAdapter);


    }


    public void getCartitem() {
        arrayListitem = dbAddItemHelpers.getaddedproduct(UserID);

        CartID.clear();
        CartProductID.clear();
        CartProductName.clear();
        CartProductImage.clear();
        CartProductQuan.clear();
        CArtPAckSize.clear();
        Cartprice.clear();
        CartSize.clear();
        CartColor.clear();
        CartpackUnit.clear();
        CartProductPrice.clear();
        CartUnitTotal.clear();
        CartProductcode.clear();
        {
            ProductsInCounterGrid.setVisibility(View.VISIBLE);
            for (int i = 0; i < arrayListitem.size(); i++) {

                String iDString = arrayListitem.get(i).getCart_id();
                String productIDString = arrayListitem.get(i).getProduct_id();
                String productNameString = arrayListitem.get(i).getProduct_name();
                String productImageString = arrayListitem.get(i).getImage_name();
                String productPriceString = arrayListitem.get(i).getPrice();
                String productQtyString = String.valueOf(arrayListitem.get(i).getQty());
                String productUnitTotalString = String.valueOf(arrayListitem.get(i).getUnit_total());
                String pack_size = arrayListitem.get(i).getPack_size();
                String price = arrayListitem.get(i).getPrice();
                String product_size = arrayListitem.get(i).getProduct_size();
                String pacK_unit = arrayListitem.get(i).getPack_unit();
                String product_color = arrayListitem.get(i).getProduct_color();
                String code = arrayListitem.get(i).getCode();

                CartID.add(iDString);

                CartProductID.add(productIDString);
                CartProductName.add(productNameString);
                CartProductImage.add(productImageString);
                CartProductQuan.add(productQtyString);
                CartProductPrice.add(productPriceString);
                CartUnitTotal.add(productUnitTotalString);
                CArtPAckSize.add(pack_size);
                Cartprice.add(price);
                CartSize.add(product_size);
                CartColor.add(product_color);
                CartpackUnit.add(pacK_unit);
                if(code.equals(""))
                {
                    CartProductcode.add("1");

                }
                else {
                    CartProductcode.add(code);

                }


              }

            sum=Float.valueOf(String.valueOf(0.0));
            for(int i=0;i<CartUnitTotal.size();i++){
                float add= Float.parseFloat(CartUnitTotal.get(i));

                Log.d("","add"+add);

                Log.d("0000","TotalAmounts "+Float.parseFloat(String.valueOf(sum+add)));
                sum= Float.parseFloat(String.valueOf(sum+add));
                Log.d("","adddd"+TotalAmount);


            }

            TotalAmount=sum;
            if(CartUnitTotal.size()==0)
            {
                grandTotalText.setText("0.0");

            }
            else {
                grandTotalText.setText(""+TotalAmount);

            }


            Log.d("","TotalAmount"+TotalAmount);
            Log.d("","TotalAmount"+CartUnitTotal);


            counterProductRecyclerAdapter = new CounterProductRecyclerAdapter(ItemInCartOffline.this, CartID, CartProductID, CartProductName, CartProductImage, CartProductQuan, CArtPAckSize, Cartprice, CartpackUnit,UserID,CartUnitTotal,CartProductcode);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(ItemInCartOffline.this);
            ProductsInCounterGrid.setHasFixedSize(true);
            ProductsInCounterGrid.setLayoutManager(MyLayoutManager);
            ProductsInCounterGrid.setAdapter(counterProductRecyclerAdapter);


        }
    }

    private class AddressAdapterlist extends RecyclerView.Adapter<AddressAdapterlist.MyViewHolder> {

        private int selectedItem=-1;

        @NonNull
        @Override
        public AddressAdapterlist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(ItemInCartOffline.this).inflate(R.layout.single_grid_item_addresses, parent, false);
            return new AddressAdapterlist.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AddressAdapterlist.MyViewHolder holder, final int position) {
            final AllCustomerAddressModel item = arrayaddressListitem.get(position);
            holder.AddressText.setText(" " + item.getAddress1() + "  " + item.getAddress2());
            holder.city.setText(item.getTownCity());
            holder.state.setText(item.getState());
            holder.AddressPincodeText.setText(item.getPincode());
            holder.Name.setText(item.getFullName());
            holder.mobile.setText(item.getMobileNumber());

            if (selectedItem == position) {
                holder.addresslay.setBackgroundResource((R.color.colorAccent));
            }
            else {
                //  myViewHolder.sizelay.setBackgroundResource((R.color.grey));

            }

            holder.select_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousItem = selectedItem;
                    selectedItem = position;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(position);

                    HascodeStatecode=item.getStateCode();
                    SeletedAddress=item.getAddress1() + "   " + item.getAddress2()+"   "+item.getTownCity()+"  "+item.getState()+"   "+item.getPincode();
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayaddressListitem.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout addresslay;
            ImageView CancelImage;
            TextView AddressTitleText, AddressText, Name, mobile, city, state, select_address, AddressPincodeText;

            public MyViewHolder(@NonNull View gridView) {
                super(gridView);
                AddressTitleText = (TextView) gridView.findViewById(R.id.addressTitleText);
                AddressText = (TextView) gridView.findViewById(R.id.addressText);
                Name = (TextView) gridView.findViewById(R.id.Name);
                mobile = (TextView) gridView.findViewById(R.id.mobile);
                city = (TextView) gridView.findViewById(R.id.city);
                state = (TextView) gridView.findViewById(R.id.state);
                select_address = (TextView) gridView.findViewById(R.id.select_address);
                AddressPincodeText = (TextView) gridView.findViewById(R.id.pincodeText);
                CancelImage = (ImageView) gridView.findViewById(R.id.deleteImage);
                addresslay=gridView.findViewById(R.id.addresslay);
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(getApplicationContext(),OfflineCounter.class);
        startActivity(intent);
        finish();
    }


}