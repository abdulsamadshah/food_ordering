package com.microlan.rushhandingoffline.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.microlan.rushhandingoffline.BaseURL.ApiClient;
import com.microlan.rushhandingoffline.BaseURL.ApiInterface;
import com.microlan.rushhandingoffline.DB.AllCatogeryDBHelper;
import com.microlan.rushhandingoffline.DB.AllCustomerDBHelper;
import com.microlan.rushhandingoffline.DB.AllGstDBHelper;
import com.microlan.rushhandingoffline.DB.AllProductDBHelper;
import com.microlan.rushhandingoffline.DB.AllUserDBHelper;
import com.microlan.rushhandingoffline.DB.CustomerAddressDBHelper;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperProductQty;
import com.microlan.rushhandingoffline.OfflineModel.ALLCustomerModel;
import com.microlan.rushhandingoffline.OfflineModel.AllCatogeryModel;
import com.microlan.rushhandingoffline.OfflineModel.AllCustomerAddressModel;
import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;
import com.microlan.rushhandingoffline.OfflineModel.USerListLoginModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.CompanyLogoItem;
import com.microlan.rushhandingoffline.model.CompanySetting;
import com.microlan.rushhandingoffline.model.UserAddressItem;
import com.microlan.rushhandingoffline.model.UserAddressResponse;
import com.microlan.rushhandingoffline.model.UsernameModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class Login extends AppCompatActivity {

    Button LoginButton;
    DatabaseHelper databaseHelper;
    DatabaseHelperProductQty databaseHelperProductQty;
    Boolean AddData = false, AddProduct = false;
    ArrayList<String> ProductName, BarcodeNumber, ProductPrice, ProductQty, Gst;
    EditText UserIDEdit, PasswordEdit;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ArrayList<String> StatesList;
    ArrayAdapter<String> StateAdapter;
    SharedPreferences sharedPreferences;

    ImageView loginimg;
    String gst_no, company_logo, billing_address, Email, Password, user_id, session_id;
    TextView forgotpass;

    AllProductDBHelper dbHelper;
    AllCatogeryDBHelper dbCatogeryHelpers;
    ItemInCartDBHelper dbAddItemHelpers;
    AllGstDBHelper dbgstHelpers;
    AllUserDBHelper dbuserlistHelpers;
    ArrayList<USerListLoginModel> arrayListuser, arrayListusers;
    ArrayList<AllProductModel> arrayListAudios;
    ArrayList<AllGstModel> arrayListgst;
    ArrayList<ALLCustomerModel> arrayListcustomer;
    ArrayList<AllCustomerAddressModel> arrayListcustomeraddress;
    ArrayList<AllCatogeryModel> arrayListcatogery;
    ArrayList<String> CustomerID, CustomerName, CustomerAddress, CustomerState;
    AllCustomerDBHelper dbAddCustomer;
    String Mdpassword, nextlogin;
    String todate;
    List<UserAddressItem> userAddress;
    CustomerAddressDBHelper dbcustomeraddress;
    ArrayList<AllCustomerAddressModel> arrayaddressListitem;
    ArrayList<OrderDataModel> arrayListorderdata;
    ArrayList<OrderDataDetailsModel> arrayListorderdetail;
    OrderDataDBHelper dborderdata;
    OrderDataDetailDBHelper dborderdetails;
    byte[] bytesImage;
    ArrayList<String> ProductID, UserIDList, GstID, AddressId, Orderno, CatogeryID, RemoveCatId;
    ArrayList<UsernameModel> userdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        company_logo = sharedPreferences.getString("company_logo", "");
        billing_address = sharedPreferences.getString("billing_address", "");
        gst_no = sharedPreferences.getString("gst_no", "");
        Email = sharedPreferences.getString("Email", "");
        Password = sharedPreferences.getString("Password", "");
        user_id = sharedPreferences.getString("id", "");
        session_id = sharedPreferences.getString("session_id", "");
        nextlogin = ((sharedPreferences.getString("nextlogin", "")));

        //  nextlogin= ("2021-04-15T10:00:55Z");
        Log.d("", "nextlogin" + nextlogin);

        dbHelper = new AllProductDBHelper(getApplicationContext());
        dbCatogeryHelpers = new AllCatogeryDBHelper(getApplicationContext());
        dbAddItemHelpers = new ItemInCartDBHelper(getApplicationContext());
        dbgstHelpers = new AllGstDBHelper(getApplicationContext());
        dbuserlistHelpers = new AllUserDBHelper(getApplicationContext());
        dbAddCustomer = new AllCustomerDBHelper(getApplicationContext());
        dbcustomeraddress = new CustomerAddressDBHelper(getApplicationContext());
        dborderdata = new OrderDataDBHelper(getApplicationContext());
        dborderdetails = new OrderDataDetailDBHelper(getApplicationContext());

        UserIDEdit = (EditText) findViewById(R.id.userIDEdit);
        PasswordEdit = (EditText) findViewById(R.id.passwordEdit);
        forgotpass = findViewById(R.id.forgotpass);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });
        ProductName = new ArrayList<String>();
        BarcodeNumber = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();
        ProductQty = new ArrayList<String>();
        Gst = new ArrayList<String>();
        StatesList = new ArrayList<String>();
        CustomerID = new ArrayList<String>();
        CustomerName = new ArrayList<String>();
        CustomerAddress = new ArrayList<String>();
        CustomerState = new ArrayList<String>();

        CatogeryID = new ArrayList<String>();
        RemoveCatId = new ArrayList<String>();
        ProductID = new ArrayList<String>();
        UserIDList = new ArrayList<String>();
        GstID = new ArrayList<String>();
        AddressId = new ArrayList<String>();
        Orderno = new ArrayList<String>();

        databaseHelper = new DatabaseHelper(Login.this);
        databaseHelperProductQty = new DatabaseHelperProductQty(Login.this);

        loginimg = findViewById(R.id.loginimg);
        LoginButton = (Button) findViewById(R.id.loginButton);
//        if (company_logo.isEmpty()) {
//            Picasso.get().load(R.drawable.microlan).placeholder(R.drawable.microlan).into(loginimg);
//
//        } else {
//            Picasso.get().load(company_logo).placeholder(R.drawable.microlan).into(loginimg);
//
//        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Date date = new Date();
        todate = (dateFormat.format(date));


        UserIDEdit.setText(Email);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserIDEdit.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter UserID", Toast.LENGTH_SHORT).show();
                } else if (PasswordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    if (!isOnline()) {
                        Date date1 = null, date2 = null;
                        DateFormat dates = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        try {
                            date1 = dates.parse(todate);

                            date2 = dates.parse(nextlogin);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Log.d("", "date1" + date1);
                        Log.d("", "date1" + date2);
                        if ((date1).after(date2)) {

                            AlertDialog.Builder ab = new AlertDialog.Builder(Login.this);
                            ab.setMessage(" please connect to internet we need to update the product ");
                            ab.setCancelable(false);
                            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //   LoginUser(UserIDEdit.getText().toString(), PasswordEdit.getText().toString());

                                    dialog.dismiss();

                                }
                            });
                            AlertDialog al = ab.create();
                            al.show();


                        } else {
                            String Username = UserIDEdit.getText().toString();
                            String password = PasswordEdit.getText().toString();

                            convertPassMd5(password);


                            arrayListuser = dbuserlistHelpers.getloginuser(Username, Mdpassword);


                            Log.d("", "arrayListuser" + arrayListuser.size());
                            int sizes = arrayListuser.size();
                            if (sizes != 0) {
                                for (int j = 0; j < arrayListuser.size(); j++) {
                                    String id = arrayListuser.get(j).getOp_user_id();
                                    String email = arrayListuser.get(j).getEmail_address();
                                    String pass = arrayListuser.get(j).getPassword();
                                    String first_name = arrayListuser.get(j).getFull_name();
                                    String mob = arrayListuser.get(j).getMobile_number();

                                    SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                                    editor.putString("id", (id));
                                    editor.putString("Email", (email));
                                    editor.putString("Password", (pass));
                                    editor.putString("first_name", (first_name));
                                    editor.putString("Registered", ("Yes"));
                                    editor.putString("nextlogin", String.valueOf(nextlogin));
                                    editor.commit();
                                    Toast.makeText(Login.this, "Successfully Loged In Fetching Products.", Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(Login.this, MainActivity.class);
                                    startActivity(loginIntent);
                                    finish();

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "UserName and Password is Wrong ,Please Try Again ", Toast.LENGTH_SHORT).show();

                            }

                        }


                    } else {

/*                         Intent loginIntent = new Intent(Login.this, MainActivity.class);
                         startActivity(loginIntent);
                         finish();*/

                        LoginUser(UserIDEdit.getText().toString(), PasswordEdit.getText().toString());

                    }
                }
            }
        });
    }

    public String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
            Mdpassword = password;
            Log.d("", "password" + password);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }


    public void LoginUser(final String Email, final String Password) {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();
        //http://microlanpos.microlan.in/
        // String insertUrl = "http://papasmart.online/api2/validate_signin";

        String insertUrl = "http://microlanpos.com/demo/api2/validate_signin";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");
                    String msg = jsonObj.getString("msg");

                    if (stat.equals("1")) {

                        String username = jsonObj.getString("user_name");
                        String op_user_ids = jsonObj.getString("op_user_id");
                        String addresss = jsonObj.getString("address");
                        String contactnos = jsonObj.getString("contact_no");

                        SharedPreferences sharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("usernames", username);
                        myEdit.putString("op_user_id", op_user_ids);
                        myEdit.putString("contact", contactnos);
                        myEdit.putString("addressno", addresss);

                        myEdit.apply();

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                        Date date = new Date();
                        String todate = dateFormat.format(date);

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, 1);
                        Date todate1 = cal.getTime();
                        String fromdate = dateFormat.format(todate1);

                        final String UserId = jsonObj.getString("op_user_id");
//                        final String username = jsonObj.getString("user_name");
//                        Log.d("usernames", username);

                        Log.d("", "UsrrId" + UserId);
                        Log.d("", "fromdate" + fromdate);
                        SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                        editor.putString("id", (UserId));
                        editor.putString("session_id", "");
                        editor.putString("Email", (Email));
                        editor.putString("Password", (Password));
                        editor.putString("Registered", encrypt("Yes"));
                        editor.putString("today", todate);
                        editor.putString("nextlogin", fromdate);
                        editor.commit();


                        if (isOnline()) {


                            AlertDialog.Builder ab = new AlertDialog.Builder(Login.this);
                            ab.setMessage(" Do You Want To Sync data form Server ");
                            ab.setCancelable(false);
                            ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    getcompanysetting();

                                    AllLoginUser();

                                    GetAllProducts();
                                    GetAllCategories();
                                    getAddress();

                                    getOrderHistory(UserId);
                                    GetAllGst();

                                    GetCustomers();


                                }
                            });
                            ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Toast.makeText(Login.this, "Successfully Loged In Fetching Products.", Toast.LENGTH_SHORT).show();

                                    Intent loginIntent = new Intent(Login.this, MainActivity.class);
//                                    loginIntent.putExtra("username", username.toString());

                                    startActivity(loginIntent);
                                    finish();

                                }
                            });
                            AlertDialog al = ab.create();
                            al.show();

                        }


                    } else {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(PostNewAgreementProperty.this, e.toString(), Toast.LENGTH_SHORT).show();
                    System.out.print(e.toString());
                }
                //messageAdapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Log.d("sdsdsd", "sdsdsd" + error.toString());
                //   Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", Email);
                parameters.put("password", Password);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    private void getOrderHistory(final String userId) {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();


        // String insertUrl = "http://papasmart.online/api2/my_orders";
        String insertUrl = "http://microlanpos.com/demo/api2/my_orders";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    //String stat = jsonObj.getString("status");
                    //ProductsGrid.setVisibility(View.VISIBLE);
                    JSONArray catDetails = jsonObj.getJSONArray("order_data");
                    for (int i = 0; i < catDetails.length(); i++) {
                        JSONObject jsonObject = catDetails.getJSONObject(i);
                        String order_id = jsonObject.getString("order_id");
                        String userNameString = jsonObject.getString("customer_name");
                        String orderNumberString = jsonObject.getString("order_number");
                        String address = jsonObject.getString("address_id");
                        String totalAmountString = jsonObject.getString("total_amount");
                        String dateTimeString = jsonObject.getString("order_date_time");
                        String wallet_amount = jsonObject.getString("wallet_amount");
                        String discount = jsonObject.getString("discount");
                        String cash_amount = jsonObject.getString("cash_amount");
                        String payment_method = jsonObject.getString("payment_method");
                        String discount_type = jsonObject.getString("discount_type");
                        String igst = jsonObject.getString("igst");
                        String cgst = jsonObject.getString("cgst");
                        String sgst = jsonObject.getString("sgst");


//                        SharedPreferences sharedPreferences = getSharedPreferences("wallpref", MODE_PRIVATE);
//                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//
//                        // write all the data entered by the user in SharedPreference and apply
//                        myEdit.putString("name", name.getText().toString());
//                        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
//                        myEdit.apply();
                        Log.d("", "orderNumberString" + orderNumberString);
                        arrayListorderdata = dborderdata.getallupdateorderdata(orderNumberString);
                        Log.d("", "arrayListorderdata.size" + arrayListorderdata.size());
                        if (arrayListorderdata == null || arrayListorderdata.isEmpty() || arrayListorderdata.size() == 0) {

                            //   Log.d("","If not exit"+arrayListorderdata.get(0).getOrder_number());
                            OrderDataModel recordingItem = new OrderDataModel(user_id, orderNumberString, totalAmountString, payment_method, address, "3", userNameString, dateTimeString, wallet_amount, discount, discount_type, cash_amount, "", totalAmountString, "1", igst, cgst, sgst);
                            dborderdata.addorderdata(recordingItem);

                        } else {

                            for (int j = 0; j < arrayListorderdata.size(); j++) {
                                Log.d("", "arrayListorderdata" + arrayListorderdata.get(j).getOrder_number());
                                String id = arrayListorderdata.get(j).getOrder_number();
                                if (id.equals(orderNumberString)) {

                                    Log.d("arrayListorderdata", "Update");
                                    OrderDataModel recordingItem = new OrderDataModel(user_id, orderNumberString, totalAmountString, payment_method, address, "3", userNameString, dateTimeString, wallet_amount, discount, discount_type, cash_amount, "", totalAmountString, "1", "", "", "");
                                    dborderdata.updateorderdata(recordingItem);
                                } else {

                                    Log.d("arrayListorderdata", "Insert");

                                    OrderDataModel recordingItem = new OrderDataModel(user_id, orderNumberString, totalAmountString, payment_method, address, "3", userNameString, dateTimeString, wallet_amount, discount, discount_type, cash_amount, "", totalAmountString, "1", "", "", "");

                                    dborderdata.addorderdata(recordingItem);

                                }
                            }


                        }

                        try {
                            JSONArray productsArray = jsonObject.getJSONArray("order_details");
                            for (int j = 0; j < productsArray.length(); j++) {
                                JSONObject jsonObjects = productsArray.getJSONObject(j);
                                String productNameString = jsonObjects.getString("product_name");
                                String productSinglePriceString = jsonObjects.getString("price");
                                String productPriceString = jsonObjects.getString("unit_total");
                                String productQuantityString = jsonObjects.getString("qty");
                                String productImageString = jsonObjects.getString("image_name");


                                arrayListorderdetail = dborderdetails.updategetorderdetails(orderNumberString);

                                Log.d("", "arrayListorderdetail" + arrayListorderdetail.size());
                                if (arrayListorderdetail == null || arrayListorderdetail.isEmpty() || arrayListorderdetail.size() == 0) {

                                    OrderDataDetailsModel orderdetails = new OrderDataDetailsModel(order_id, "", productNameString, "", productSinglePriceString, productQuantityString, "", "", "", "", productPriceString, user_id, order_id, orderNumberString, "1");

                                    dborderdetails.addorderdetails(orderdetails);

                                } else {
                                    for (int k = 0; k < arrayListorderdetail.size(); k++) {
                                        Log.d("", " order details getOrder_number" + arrayListorderdetail.get(k).getOrder_number());
                                        String id = arrayListorderdetail.get(k).getOrder_number();
                                        if (id.equals(orderNumberString)) {

                                            Log.d("", "Update");
                                            OrderDataDetailsModel orderdetails = new OrderDataDetailsModel(order_id, "", productNameString, "", productSinglePriceString, productQuantityString, "", "", "", "", productPriceString, user_id, order_id, orderNumberString, "1");
                                            dborderdetails.updateexitorderdetails(orderdetails);
                                        } else {

                                            Log.d("", "Insert");

                                            OrderDataDetailsModel orderdetails = new OrderDataDetailsModel(order_id, "", productNameString, "", productSinglePriceString, productQuantityString, "", "", "", "", productPriceString, user_id, order_id, orderNumberString, "1");

                                            dborderdetails.addorderdetails(orderdetails);

                                        }
                                    }


                                }
                            }


                        } catch (JSONException e) {

                        }


                    }
                    arrayListorderdetail = dborderdetails.getuserorderdetails(userId);

                    if (arrayListorderdetail == null || arrayListorderdetail.isEmpty() || arrayListorderdetail.size() == 0) {

                    } else {


                        for (int i = 0; i < arrayListorderdetail.size(); i++) {
                            if (Orderno.contains(arrayListorderdetail.get(i).getOrder_number())) {


                                Log.d("", "MAtch id" + arrayListcustomeraddress.get(i));
                            } else {


                                RemoveCatId.add(arrayListorderdetail.get(i).getOrder_number());

                                arrayListorderdetail = dborderdetails.deleteItem(arrayListorderdetail.get(i).getOrder_number());

                                Log.d("", "RemoveCatId" + RemoveCatId);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(PostNewAgreementProperty.this, e.toString(), Toast.LENGTH_SHORT).show();
                    System.out.print(e.toString());
                }
                //messageAdapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", userId);

                Log.d("", "parameters" + parameters);
                return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    private void getAddress() {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        Retrofit r = ApiClient.getClient();
        ApiInterface api = r.create(ApiInterface.class);


        Call<UserAddressResponse> call = api.getaddress("");
        call.enqueue(new Callback<UserAddressResponse>() {

            @Override
            public void onResponse(Call<UserAddressResponse> call, retrofit2.Response<UserAddressResponse> response) {
                progressDialog.cancel();
                String status = response.body().getStatus();
                if (status.equals("1")) {
                    userAddress = response.body().getUserAddress();
                    Log.d("", "userAddress" + userAddress);

                    for (int i = 0; i < userAddress.size(); i++) {
                        String name = userAddress.get(i).getFullName();
                        String city = userAddress.get(i).getTownCity();
                        String pincode = userAddress.get(i).getPincode();
                        String ares = userAddress.get(i).getLandmarkNearestArea();
                        String email = userAddress.get(i).getEmailAddress();
                        String state = userAddress.get(i).getState();
                        String address = userAddress.get(i).getAddress1();
                        String address2 = userAddress.get(i).getAddress2();
                        String mobile = userAddress.get(i).getMobileNumber();
                        String customerid = userAddress.get(i).getUser_id();
                        String stateCode = userAddress.get(i).getStateCode();
                        String type = userAddress.get(i).getAddressType();
                        String address_id = userAddress.get(i).getAddress_id();
                        String uniqe = userAddress.get(i).getUnique_id();

                        Toast.makeText(Login.this, customerid, Toast.LENGTH_SHORT).show();

                        Log.d("", "address" + name);
                        Log.d("", "address" + address);

/*
                        AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(city,customerid,pincode,ares,name,email,type,address2,address,state,mobile,stateCode,"1",uniqe);
                        dbcustomeraddress.addaddress(recordingItem);
*/

                        arrayListcustomeraddress = dbcustomeraddress.geexitaddress(uniqe);
                        if (arrayListcustomeraddress == null || arrayListcustomeraddress.isEmpty() || arrayListcustomeraddress.size() == 0) {

                            AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(city, customerid, pincode, ares, name, email, type, address, address2, state, mobile, stateCode, "1", uniqe);
                            dbcustomeraddress.addaddress(recordingItem);

                        } else {
                            for (int j = 0; j < arrayListcustomeraddress.size(); j++) {
                                Log.d("", "arrayListgst" + arrayListcustomeraddress.get(j).getUniqeid());
                                String id = arrayListcustomeraddress.get(j).getUniqeid();
                                if (id.equals(uniqe)) {

                                    Log.d("", "Update");
                                    AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(city, customerid, pincode, ares, name, email, type, address, address2, state, mobile, stateCode, "1", uniqe);
                                    dbcustomeraddress.updateaddress(recordingItem);
                                } else {

                                    Log.d("", "Insert");

                                    AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(city, customerid, pincode, ares, name, email, type, address, address2, state, mobile, stateCode, "1", uniqe);

                                    dbcustomeraddress.addaddress(recordingItem);

                                }
                            }

                        }
                    }
                    arrayListcustomeraddress = dbcustomeraddress.getaddress("");

/*
                    if (arrayListcustomeraddress == null || arrayListcustomeraddress.isEmpty() || arrayListcustomeraddress.size() == 0) {

                    }
                    else {

                        for (int i=0; i < arrayListcustomeraddress.size(); i++){
                            if(AddressId.contains(arrayListcustomeraddress.get(i).getUniqeid())){


                                Log.d("","MAtch id"+arrayListcustomeraddress.get(i));
                            }else{



                                arrayListcustomeraddress = dbcustomeraddress.deleteItem(arrayListcustomeraddress.get(i).getUniqeid());

                                Log.d("","RemoveCatId"+RemoveCatId);
                            }

                        }

                    }
*/


                }

            }

            @Override
            public void onFailure(Call<UserAddressResponse> call, Throwable t) {
                progressDialog.cancel();

            }
        });

    }

    private void getcompanysetting() {

        Retrofit r = ApiClient.getClient();
        ApiInterface api = r.create(ApiInterface.class);


        Call<com.microlan.rushhandingoffline.model.CompanySetting> call = api.companysetting();
        call.enqueue(new Callback<com.microlan.rushhandingoffline.model.CompanySetting>() {
            @Override
            public void onResponse(Call<com.microlan.rushhandingoffline.model.CompanySetting> call, retrofit2.Response<com.microlan.rushhandingoffline.model.CompanySetting> response) {
                Log.d("TAG", "SignUPADRESS_finish_onResponse: " + "successful");

                List<CompanyLogoItem> companyLogo;
                companyLogo = response.body().getCompanyLogo();
                SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                editor.putString("billing_address", (companyLogo.get(0).getBillingAddress()));
                editor.putString("company_logo", (companyLogo.get(0).getCompanyLogo()));
                editor.putString("gst_no", (companyLogo.get(0).getGstNo()));
                editor.commit();
            }

            @Override
            public void onFailure(Call<CompanySetting> call, Throwable t) {
                Log.d("TAG", "SignUPADRESS_Finish_onFailure: " + t.fillInStackTrace());
            }
        });


    }


    public void GetCustomers() {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        CustomerID.clear();
        CustomerName.clear();
        CustomerAddress.clear();
        CustomerState.clear();

        // final String url = "http://papasmart.online/api2/details_list";
        final String url = "http://microlanpos.com/demo/api2/details_list";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.cancel();
                System.out.print(response.toString());

                try {
                    JSONArray details = response.getJSONArray("list");
                    for (int i = 0; i < details.length(); i++) {
                        JSONObject jsonObject = details.getJSONObject(i);
                        String customerIDString = jsonObject.getString("user_id");
                        String customerNameString = jsonObject.getString("Full Name");
                        String customerMobileString = jsonObject.getString("Mobile_numer");
                        String email_address = jsonObject.getString("email_address");
                        String unique_id = jsonObject.getString("unique_id");

                        CustomerID.add(customerIDString);
                        CustomerName.add(customerNameString + " ( " + customerMobileString + " ) ");


                        Log.d("", "customerIDString" + customerIDString);


                        arrayListcustomer = dbAddCustomer.getupdatecustomer(unique_id);
                        Log.d("arrayListcustomer", "arrayListcustomer" + arrayListcustomer.size());
                        if (arrayListcustomer == null || arrayListcustomer.isEmpty() || arrayListcustomer.size() == 0) {
                            ALLCustomerModel recordingItem = new ALLCustomerModel(customerIDString, customerNameString, customerMobileString, email_address, "", "", "", "", "", "", "1", unique_id);
                            dbAddCustomer.addcustomer(recordingItem);

                        } else {
                            for (int j = 0; j < arrayListcustomer.size(); j++) {
                                Log.d("", "arrayListcustomer" + arrayListcustomer.get(j).getCustomerid());
                                String id = arrayListcustomer.get(j).getUniqeid();
                                if (id.equals(unique_id)) {

                                    Log.d("", "Update");
                                    ALLCustomerModel recordingItem = new ALLCustomerModel(customerIDString, customerNameString, customerMobileString, email_address, "", "", "", "", "", "", "1", unique_id);
                                    dbAddCustomer.updatecustomer(recordingItem);
                                } else {
                                    Log.d("", "Insert");

                                    ALLCustomerModel recordingItem = new ALLCustomerModel(customerIDString, customerNameString, customerMobileString, email_address, "", "", "", "", "", "", "1", unique_id);

                                    dbAddCustomer.addcustomer(recordingItem);

                                }
                            }

                        }


                    }
                    arrayListcustomer = dbAddCustomer.getallcustomer();

                    if (arrayListcustomer == null || arrayListcustomer.isEmpty() || arrayListcustomer.size() == 0) {

                    } else {

                        for (int i = 0; i < arrayListcustomer.size(); i++) {
                            if (CustomerID.contains(arrayListcustomer.get(i).getUniqeid())) {


                            } else {


                                RemoveCatId.add(arrayListcustomer.get(i).getUniqeid());

                                arrayListcustomer = dbAddCustomer.deleteItem(arrayListcustomer.get(i).getUniqeid());

                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Customers Not Found.", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error.Response");
                progressDialog.cancel();

            }
        });

        requestQueue.add(getRequest);
    }

    public void GetAllGst() {
        // final String url = "http://papasmart.online/api2/getGstSettings";
        final String url = "http://microlanpos.com/demo/api2/getGstSettings";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  progressDialog.cancel();
                System.out.print(response.toString());

                try {
                    // if (stat.equals("1")) {
                    JSONArray details = response.getJSONArray("gst_settings");

                    Log.d("", "Gst details" + details);
                    for (int i = 0; i < details.length(); i++) {
                        JSONObject jsonObject = details.getJSONObject(i);
                        String gst_set_id = jsonObject.getString("gst_set_id");
                        String category_id = jsonObject.getString("category_id");
                        String gst_type = jsonObject.getString("gst_type");
                        String hsn_code = jsonObject.getString("hsn_code");
                        String description = jsonObject.getString("description");
                        String SGST = jsonObject.getString("SGST");
                        String CGST = jsonObject.getString("CGST");
                        String IGST = jsonObject.getString("IGST");
                        String CESS = jsonObject.getString("CESS");

                        arrayListgst = dbgstHelpers.updateallgst(gst_set_id);
                        if (arrayListgst == null || arrayListgst.isEmpty() || arrayListgst.size() == 0) {
                            AllGstModel recordingItem = new AllGstModel(gst_set_id, gst_type, hsn_code, description, SGST, CGST, IGST, CESS);
                            dbgstHelpers.addgst(recordingItem);

                        } else {
                            for (int j = 0; j < arrayListgst.size(); j++) {
                                Log.d("", "arrayListgst" + arrayListgst.get(j).getGstSetId());
                                String id = arrayListgst.get(j).getGstSetId();
                                if (id.equals(gst_set_id)) {

                                    Log.d("", "Update");
                                    AllGstModel recordingItem = new AllGstModel(gst_set_id, gst_type, hsn_code, description, SGST, CGST, IGST, CESS);
                                    dbgstHelpers.updateexitsgst(recordingItem);
                                } else {

                                    Log.d("", "Insert");

                                    AllGstModel recordingItem = new AllGstModel(gst_set_id, gst_type, hsn_code, description, SGST, CGST, IGST, CESS);
                                    dbgstHelpers.addgst(recordingItem);

                                }
                            }

                        }


                        Toast.makeText(Login.this, "Successfully Loged In Fetching Products.", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(Login.this, MainActivity.class);
                        startActivity(loginIntent);
                        finish();


                    }

                    arrayListgst = dbgstHelpers.getallgstss();


                    Log.d("","arrayListgstssss "+arrayListgst);
                    if (arrayListgst == null || arrayListgst.isEmpty() || arrayListgst.size() == 0) {

                    } else {

                        for (int i = 0; i < arrayListgst.size(); i++) {
                            if (GstID.contains(arrayListgst.get(i).getGstSetId())) {


                                Log.d("", "MAtch id" + arrayListgst.get(i));
                            } else {


                                RemoveCatId.add(arrayListgst.get(i).getGstSetId());

                                arrayListgst = dbgstHelpers.deleteItem(arrayListgst.get(i).getGstSetId());

                                Log.d("", "RemoveCatId" + RemoveCatId);
                            }
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error.Response");
                //  progressDialog.cancel();

            }
        });

        requestQueue.add(getRequest);
    }

    public void GetAllCategories() {
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        // final String url = "http://papasmart.online/api2/get_all_category";
        final String url = "http://microlanpos.com/demo/api2/get_all_category";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.cancel();
                System.out.print(response.toString());

                try {
                    String stat = response.getString("status");
                    if (stat.equals("1")) {
                        JSONArray details = response.getJSONArray("category_list");
                        for (int i = 0; i < details.length(); i++) {
                            JSONObject jsonObject = details.getJSONObject(i);
                            String categoryIDString = jsonObject.getString("category_id");
                            String categoryNameString = jsonObject.getString("category_name");
                            String categoriesImageString = jsonObject.getString("image_name");


                            Log.d("", "All Catogery");

                            arrayListcatogery = dbCatogeryHelpers.addexitscatogery(categoryIDString);

                            if (arrayListcatogery == null || arrayListcatogery.isEmpty() || arrayListcatogery.size() == 0) {
                                Log.d("", "Add Catogery not there");

                                AllCatogeryModel recordingItem = new AllCatogeryModel(categoryIDString, categoryNameString, categoriesImageString);

                                dbCatogeryHelpers.addcatogery(recordingItem);

                            } else {

                                for (int j = 0; j < arrayListcatogery.size(); j++) {
                                    Log.d("", "arrayListcatogery" + arrayListcatogery.get(j).getCatogerId());
                                    String id = arrayListcatogery.get(j).getCatogerId();
                                    if (id.equals(categoryIDString)) {

                                        Log.d("", "Update Cotogery");
                                        AllCatogeryModel recordingItem = new AllCatogeryModel(categoryIDString, categoryNameString, categoriesImageString);

                                        dbCatogeryHelpers.updateexitscatogery(recordingItem);

                                    } else {
                                        Log.d("", "Add Catogery Cotogery");
                                        AllCatogeryModel recordingItem = new AllCatogeryModel(categoryIDString, categoryNameString, categoriesImageString);

                                        dbCatogeryHelpers.addcatogery(recordingItem);

                                    }
                                }


                                arrayListcatogery = dbCatogeryHelpers.getallcatogery();


/*
                                   if(arrayListcatogery==null || arrayListcatogery.isEmpty() || arrayListcatogery.size()==0)
                                   {

                                   }
                                   else {
                                       for (int j=0; j < arrayListcatogery.size(); j++){
                                           if(CatogeryID.contains(arrayListcatogery.get(j).getCatogerId())){


                                               Log.d("","MAtch id"+arrayListcatogery.get(i));
                                           }else{
                                               //do something for not equals
                                               int index = CatogeryID.indexOf(arrayListcatogery.get(i).getCatogerId());

                                               Log.d("","Not Match id"+arrayListcatogery.get(i).getCatogerId());

                                               RemoveCatId.add(arrayListcatogery.get(j).getCatogerId());

                                               arrayListcatogery = dbCatogeryHelpers.deleteItem(arrayListcatogery.get(j).getCatogerId());

                                               Log.d("","RemoveCatId"+RemoveCatId);
                                           }
                                       }

                                   }
*/


                            }

                        }


                    } else {
                        Toast.makeText(Login.this, "Products Not Found.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error.Response");
                progressDialog.cancel();

            }
        });

        requestQueue.add(getRequest);
    }

    public void GetAllProducts() {

        //   final String url = "http://papasmart.online/api2/get_all_productdetails";
        final String url = "http://microlanpos.com/demo/api2/get_all_productdetails";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  progressDialog.cancel();
                System.out.print(response.toString());

                try {
                    String stat = response.getString("status");
                    if (stat.equals("1")) {
                        JSONArray details = response.getJSONArray("product_details");

                        Log.d("", "details" + details);
                        for (int i = 0; i < details.length(); i++) {
                            JSONObject jsonObject = details.getJSONObject(i);
                            String productIDString = jsonObject.getString("product_id");
                            String categoryid = jsonObject.getString("category_id");
                            String productNameString = jsonObject.getString("product_name");
                            String productImageString = jsonObject.getString("image_name");
                            String productPriceString = jsonObject.getString("price");
                            String stock_qty = jsonObject.getString("stock_qty");
                            String pack_size1 = jsonObject.getString("pack_size1");
                            String price1 = jsonObject.getString("price1");
                            String unit_name = jsonObject.getString("unit_name");
                            String description = jsonObject.getString("description");
                            String terms_conditions = jsonObject.getString("terms_conditions");
                            String hsn_code = jsonObject.getString("hsn_code");

                            String[] costs = productPriceString.split(",");
                            String singleprice = costs[0];

                            Log.d("", "productNameString" + productNameString);

                            if (hsn_code.equals("null")) {
                                hsn_code = "0";
                                arrayListAudios = dbHelper.getupdateproduct(productIDString);

                                if (arrayListAudios == null || arrayListAudios.isEmpty() || arrayListAudios.size() == 0) {
                                    AllProductModel recordingItem = new AllProductModel(categoryid, productIDString, productNameString, productImageString, singleprice, pack_size1, price1, unit_name, terms_conditions, description, hsn_code, stock_qty);

                                    dbHelper.addproduct(recordingItem);


                                } else {
                                    for (int j = 0; j < arrayListAudios.size(); j++) {
                                        Log.d("", "arrayListAudios" + arrayListAudios.get(j).getProduct_id());
                                        String id = arrayListAudios.get(j).getProduct_id();
                                        if (id.equals(productIDString)) {
                                            AllProductModel recordingItem = new AllProductModel(categoryid, productIDString, productNameString, productImageString, singleprice, pack_size1, price1, unit_name, terms_conditions, description, hsn_code, stock_qty);

                                            dbHelper.getupdateexitproduct(recordingItem);

                                        } else {
                                            AllProductModel recordingItem = new AllProductModel(categoryid, productIDString, productNameString, productImageString, singleprice, pack_size1, price1, unit_name, terms_conditions, description, hsn_code, stock_qty);

                                            dbHelper.addproduct(recordingItem);

                                        }
                                    }

                                }

                            } else {
                                arrayListAudios = dbHelper.getupdateproduct(productIDString);

                                if (arrayListAudios == null || arrayListAudios.isEmpty() || arrayListAudios.size() == 0) {
                                    AllProductModel recordingItem = new AllProductModel(categoryid, productIDString, productNameString, productImageString, singleprice, pack_size1, price1, unit_name, terms_conditions, description, hsn_code, stock_qty);

                                    dbHelper.addproduct(recordingItem);


                                } else {
                                    for (int j = 0; j < arrayListAudios.size(); j++) {
                                        Log.d("", "arrayListAudios" + arrayListAudios.get(j).getProduct_id());
                                        String id = arrayListAudios.get(j).getProduct_id();
                                        if (id.equals(productIDString)) {
                                            AllProductModel recordingItem = new AllProductModel(categoryid, productIDString, productNameString, productImageString, singleprice, pack_size1, price1, unit_name, terms_conditions, description, hsn_code, stock_qty);

                                            dbHelper.getupdateexitproduct(recordingItem);

                                        } else {
                                            AllProductModel recordingItem = new AllProductModel(categoryid, productIDString, productNameString, productImageString, singleprice, pack_size1, price1, unit_name, terms_conditions, description, hsn_code, stock_qty);

                                            dbHelper.addproduct(recordingItem);

                                        }
                                    }

                                }

                            }
                            arrayListAudios = dbHelper.getallproduct();

/*
                                    if(arrayListAudios==null || arrayListAudios.isEmpty() || arrayListAudios.size()==0)
                                    {

                                    }
                                    else {

                                        for (int j=0; j < arrayListAudios.size(); j++){
                                            if(ProductID.contains(arrayListAudios.get(j).getProduct_id())){


                                                Log.d("","MAtch id"+arrayListAudios.get(j));
                                            }else{


                                                RemoveCatId.add(arrayListAudios.get(j).getProduct_id());

                                                arrayListAudios = dbHelper.deleteItem(arrayListAudios.get(j).getProduct_id());

                                                Log.d("","RemoveCatId"+RemoveCatId);
                                            }
                                        }

                                    }
*/


                        }


                    } else {
                        Toast.makeText(Login.this, "Products Not Found.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error.Response");

                //   progressDialog.cancel();

            }
        });

        requestQueue.add(getRequest);
    }

    public void AllLoginUser() {

        UserIDList.clear();
        //  final String url = "http://papasmart.online/api2/getUserData";

        final String url = "http://microlanpos.com/demo/api2/getUserData";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  progressDialog.cancel();
                System.out.print(response.toString());

                try {
                    String stat = response.getString("status");
                    if (stat.equals("1")) {
                        JSONArray details = response.getJSONArray("user_details");
                        userdata = new ArrayList<>();
                        Log.d("", "details" + details);
                        for (int i = 0; i < details.length(); i++) {
                            JSONObject jsonObject = details.getJSONObject(i);
                            String op_user_id = jsonObject.getString("op_user_id");
                            String user_ids = jsonObject.getString("user_id");
                            String user_name = jsonObject.getString("user_name");
                            userdata.add(new UsernameModel(user_name));
                            ///userdata.add(TextUtils.isEmpty(user_name)?"":user_name);// Change if blank Space occur in GUI
                            String email_address = jsonObject.getString("email");
                            String password = jsonObject.getString("password");
                            String mobile_number = jsonObject.getString("contact_no");
                            String role_id = jsonObject.getString("role_id");
                            String map_with = jsonObject.getString("map_with");
                            String map_with_id = jsonObject.getString("map_with_id");
                            String profile_pic = jsonObject.getString("profile_photo");
                            String aadhar_no = jsonObject.getString("aadhar_no");
                            String pan_no = jsonObject.getString("pan_no");
                            String phone_no = jsonObject.getString("phone_no");
                            String address = jsonObject.getString("address");


//                            Log.e("TAG",sharedPreferences.getString("usernames",""));
                            arrayListuser = dbuserlistHelpers.getupdateproduct(op_user_id);

                            Log.d("", "arrayListuser" + arrayListuser.size());
                            Log.d("", "arrayListuser" + arrayListuser);
                            if (arrayListuser == null || arrayListuser.isEmpty() || arrayListuser.size() == 0) {
                                USerListLoginModel recordingItem = new USerListLoginModel(op_user_id, user_id, user_name, email_address, password, mobile_number, role_id, map_with, map_with_id, profile_pic, aadhar_no, pan_no, phone_no, address);

                                dbuserlistHelpers.adduser(recordingItem);

                            } else {
                                for (int j = 0; j < arrayListuser.size(); j++) {
                                    Log.d("", "arrayListAudios" + arrayListuser.get(j).getUser_id());
                                    String id = arrayListuser.get(j).getOp_user_id();
                                    if (id.equals(user_id)) {
                                        USerListLoginModel recordingItem = new USerListLoginModel(op_user_id, user_id, user_name, email_address, password, mobile_number, role_id, map_with, map_with_id, profile_pic, aadhar_no, pan_no, phone_no, address);

                                        dbuserlistHelpers.updateuser(recordingItem);

                                    } else {
                                        USerListLoginModel recordingItem = new USerListLoginModel(op_user_id, user_id, user_name, email_address, password, mobile_number, role_id, map_with, map_with_id, profile_pic, aadhar_no, pan_no, phone_no, address);

                                        dbuserlistHelpers.adduser(recordingItem);
                                    }
                                }

                            }
                            arrayListusers = dbuserlistHelpers.getalluser();

                            Log.d("", "arrayListusers" + arrayListusers);


                        }


                    } else {
                        Toast.makeText(Login.this, "Products Not Found.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error.Response");

                //   progressDialog.cancel();

            }
        });

        requestQueue.add(getRequest);
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
