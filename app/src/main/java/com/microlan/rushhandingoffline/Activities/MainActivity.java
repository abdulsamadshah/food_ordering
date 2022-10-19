package com.microlan.rushhandingoffline.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.microlan.rushhandingoffline.Adapters.DashboardOrderAdapter;
import com.microlan.rushhandingoffline.Adapters.NotificationAdapter;
import com.microlan.rushhandingoffline.BaseURL.ApiClient;
import com.microlan.rushhandingoffline.BaseURL.ApiInterface;
import com.microlan.rushhandingoffline.DB.AllCustomerDBHelper;
import com.microlan.rushhandingoffline.DB.CustomerAddressDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperBills;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomer;
import com.microlan.rushhandingoffline.OfflineModel.ALLCustomerModel;
import com.microlan.rushhandingoffline.OfflineModel.AllCustomerAddressModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.AnnouncementItem;
import com.microlan.rushhandingoffline.model.AnnouncementResponse;
import com.wooplr.spotlight.SpotlightView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.yuana.chart.pie.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView DateText;
    EditText TimeStampText;
    LinearLayout TakeOrderLayout, OrderListLayout, StockListLayout;
    DatabaseHelper databaseHelper;
    ArrayList<String> ProductName, BarcodeNumber, ProductPrice;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Boolean AddData = false;
    PieChartView PieChartOne, PieChartTwo;
    ArrayList<Float> ChartOneValuesFloat, ChartTwoValuesFloat, ChartThreeValuesFloat;
    ArrayList<Integer> ChartOneValuesInteger, ChartTwoValuesInteger, ChartThreeValuesInteger;
    Float[] ChartOneFloat;
    ArrayList<String> OrderId, OrderNumber, OrderAmount, TotalAmount, TotalWeekAmount, TotalMonthAmount, TotalCustomers;
    DashboardOrderAdapter dashboardOrderAdapter;
    DatabaseHelperBills databaseHelperBills;
    DatabaseHelperCustomer databaseHelperCustomer;
    int TodayTotal = 0, WeeklyTotal = 0, MonthlyTotal = 0, LastMonthTotal = 0;
    SpotlightView DailyReportSpotLightView, WeeklyReportSpotLightView;
    ScrollView MainScroll;
    EditText SearchButton;
    String Intro = "", UserID, first_name;
    SharedPreferences sharedPreferences;
    ImageView LogoutBtn;
    String gst_no, company_logo, billing_address, Email;
    private List<AnnouncementItem> announcement;
    TextView user_name;
    RecyclerView notificationrecy;

    ArrayList<String> UserName, Orderid, Address, DateTime, DateList, WalletAmount, Discount, CashAmount, address_id, PaymentMode, Customer_id, User_id, Discout_type, Order_type;
    OrderDataDBHelper dborderdata;
    OrderDataDetailDBHelper dborderdetails;
    AllCustomerDBHelper dbAddCustomer;
    CustomerAddressDBHelper dbAddaddress;

    ArrayList<OrderDataModel> arrayListorderdata;
    ArrayList<OrderDataDetailsModel> arrayListorderfetails;
    ArrayList<ALLCustomerModel> arraycustomerListitem;
    ArrayList<AllCustomerAddressModel> arrayaddressListitem;

    ArrayList<String> OrderProductName, ProductId, ProductQuantity, inglePrice, OrderProductPrice, ProductImage, SrNo, color, size, packsize, unit;
    ArrayList<String> CustomerID, CustomerName, CustomerAddress, StatesList, CustomerState, CustomerMObile, CustomerEmail, Customeruniqe;
    ArrayList<String> Arrayuniqeid, ArrayAddres1, ArrayAddress2, ArrayCity, ArrayPin;

    ArrayList<String> CGST, SGST, IGST;

    /*`user_id`, `full_name`, `mobile_no`, `email_id`, `address1`, `address2`, `landmark_nearest_area`, `town_city`, `pincode`, `state`,unique_id*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);




        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        Intro = decrypt(Intro);
        company_logo = sharedPreferences.getString("company_logo", "");
        Email = sharedPreferences.getString("Email", "");
        first_name = sharedPreferences.getString("first_name", "");
        billing_address = sharedPreferences.getString("billing_address", "");
        gst_no = sharedPreferences.getString("gst_no", "");
        notificationrecy = findViewById(R.id.notificationrecy);
        notificationrecy.setLayoutManager(new LinearLayoutManager(this));

        dborderdata = new OrderDataDBHelper(getApplicationContext());
        dborderdetails = new OrderDataDetailDBHelper(getApplicationContext());
        dbAddCustomer = new AllCustomerDBHelper(getApplicationContext());
        dbAddaddress = new CustomerAddressDBHelper(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SearchButton = (EditText) navigationView.findViewById(R.id.searchButton);
        navigationView.setNavigationItemSelectedListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LogoutBtn = (ImageView) findViewById(R.id.logoutBtn);

        MainScroll = (ScrollView) findViewById(R.id.mainScroll);

        SharedPreferences wsh = getSharedPreferences("userdata", MODE_PRIVATE);
        String unames = wsh.getString("usernames", "");


        user_name = findViewById(R.id.user_name);
        user_name.setText(unames);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //  getNotification();
        ProductName = new ArrayList<String>();
        BarcodeNumber = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();

        ChartOneValuesFloat = new ArrayList<Float>();
        ChartTwoValuesFloat = new ArrayList<Float>();
        ChartThreeValuesFloat = new ArrayList<Float>();

        ChartOneValuesInteger = new ArrayList<Integer>();
        ChartTwoValuesInteger = new ArrayList<Integer>();
        ChartThreeValuesInteger = new ArrayList<Integer>();

        OrderId = new ArrayList<String>();
        OrderNumber = new ArrayList<String>();
        OrderAmount = new ArrayList<String>();
        TotalAmount = new ArrayList<String>();
        TotalWeekAmount = new ArrayList<String>();
        TotalMonthAmount = new ArrayList<String>();
        TotalCustomers = new ArrayList<String>();


        CGST = new ArrayList<String>();
        SGST = new ArrayList<String>();
        IGST = new ArrayList<String>();
        UserName = new ArrayList<String>();
        OrderNumber = new ArrayList<String>();
        TotalAmount = new ArrayList<String>();
        Address = new ArrayList<String>();
        DateTime = new ArrayList<String>();
        DateList = new ArrayList<String>();
        WalletAmount = new ArrayList<String>();
        Discount = new ArrayList<String>();
        CashAmount = new ArrayList<String>();
        address_id = new ArrayList<String>();
        PaymentMode = new ArrayList<String>();
        Orderid = new ArrayList<String>();
        Customer_id = new ArrayList<String>();
        User_id = new ArrayList<String>();
        Discout_type = new ArrayList<String>();
        Order_type = new ArrayList<String>();
        ProductId = new ArrayList<String>();


        OrderProductName = new ArrayList<String>();
        ProductQuantity = new ArrayList<String>();
        inglePrice = new ArrayList<String>();
        OrderProductPrice = new ArrayList<String>();
        ProductImage = new ArrayList<String>();
        SrNo = new ArrayList<String>();
        color = new ArrayList<String>();
        size = new ArrayList<String>();
        packsize = new ArrayList<String>();
        unit = new ArrayList<String>();
//        unit = new ArrayList<String>();


        CustomerID = new ArrayList<String>();
        CustomerName = new ArrayList<String>();
        CustomerAddress = new ArrayList<String>();
        CustomerState = new ArrayList<String>();
        CustomerEmail = new ArrayList<String>();
        CustomerMObile = new ArrayList<String>();
        Arrayuniqeid = new ArrayList<String>();
        ArrayAddres1 = new ArrayList<String>();
        ArrayAddress2 = new ArrayList<String>();
        ArrayCity = new ArrayList<String>();
        ArrayPin = new ArrayList<String>();
        Customeruniqe = new ArrayList<String>();


        //ArrayUserid,ArrayAddres1,ArrayAddress2,ArrayCity
/*
        Addstatename = new ArrayList<String>();
        Addstatecode = new ArrayList<String>();
                CustomerEmail.clear();
        CustomerMObile.clear();

*/

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);


        float TempTotalToday = 0;
        for (int i = 0; i < TotalAmount.size(); i++) {
            TempTotalToday = TempTotalToday + Float.valueOf(TotalAmount.get(i));
        }

        TodayTotal = Math.round(TempTotalToday);


        TakeOrderLayout = (LinearLayout) findViewById(R.id.takeOrderLayout);
        OrderListLayout = (LinearLayout) findViewById(R.id.orderListLayout);

        TimeStampText = (EditText) findViewById(R.id.timeStampText);
        DateText = (TextView) findViewById(R.id.dateText);

        DateText.setText(formattedDate);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimeStampText.setText(String.valueOf(hour) + ":" + String.valueOf(minute));

        final Handler handler = new Handler();
        final int delay = 60000; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                handler.postDelayed(this, delay);
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimeStampText.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
            }
        }, delay);

        TakeOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeOrderIntent = new Intent(MainActivity.this, OfflineCounter.class);
                startActivity(takeOrderIntent);

            }
        });

        OrderListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent OrdersIntent = new Intent(MainActivity.this, Orders.class);
                startActivity(OrdersIntent);
            }
        });


        MainScroll.scrollTo(500, 100);
        TakeOrderLayout.requestFocus();


        if (Intro.equals("No")) {

        } else {

        }

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setMessage(" Do You Want To Sync data on Server ");
                ab.setCancelable(false);
                ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (!isOnline()) {
                            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                            ab.setMessage(" Internet Not Connected Please Connect Internet And Try again. ");
                            ab.setCancelable(false);
                            ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();


                                }
                            });

                            AlertDialog al = ab.create();
                            al.show();


                        } else {



                            Intent loginIntent = new Intent(MainActivity.this, Login.class);
                            startActivity(loginIntent);
                            finish();
                        }

                    }
                });
                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
                        editor.putString("Registered", encrypt("No"));
                        editor.commit();
                        Intent loginIntent = new Intent(MainActivity.this, Login.class);
                        startActivity(loginIntent);
                        finish();
                    }
                });
                AlertDialog al = ab.create();
                al.show();


            }
        });


        // Dashboard(UserID);

    }

    private void Addresslist() {


        arrayaddressListitem = dbAddaddress.getaddress("0");


        Log.d("", "arrayaddressListitem" + arrayaddressListitem.size());
        CustomerID.clear();
        CustomerName.clear();
        CustomerAddress.clear();
        CustomerState.clear();
        CustomerEmail.clear();
        CustomerMObile.clear();
        Arrayuniqeid.clear();
        ArrayAddres1.clear();
        ArrayAddress2.clear();
        ArrayCity.clear();
        ArrayPin.clear();
        Customeruniqe.clear();
        if (arrayaddressListitem.size() == 0) {

        } else {
            for (int i = 0; i < arrayaddressListitem.size(); i++) {

                String customerIDString = arrayaddressListitem.get(i).getCustomerId();
                String customerNameString = arrayaddressListitem.get(i).getFullName();
                String customerAddress1String = arrayaddressListitem.get(i).getAddress1();
                String customerAddress2String = arrayaddressListitem.get(i).getAddress2();
                String customerCityString = arrayaddressListitem.get(i).getTownCity();
                String email = arrayaddressListitem.get(i).getEmailAddress();
                // String customerMobileString = arraycustomerListitem.get(i).getCustomernumber();
                String customerStateString = arrayaddressListitem.get(i).getState();
                String customerPencodeString = arrayaddressListitem.get(i).getPincode();
                String uniqe = arrayaddressListitem.get(i).getUniqeid();


                CustomerID.add(customerIDString);
                CustomerName.add(customerNameString);
                CustomerMObile.add("");
                CustomerEmail.add(email);
                ArrayAddres1.add(customerAddress1String);
                ArrayAddress2.add(customerAddress2String);
                ArrayCity.add(customerCityString);
                CustomerState.add(customerStateString);
                ArrayPin.add(customerPencodeString);
                Customeruniqe.add(uniqe);


            }
            addaddresslist(CustomerID, CustomerName, CustomerMObile, CustomerEmail, ArrayAddres1, ArrayAddress2, ArrayCity, CustomerState, ArrayPin, Customeruniqe);

        }


    }

    private void addaddresslist(final ArrayList<String> customerID, final ArrayList<String> customerName, final ArrayList<String> customerMObile, final ArrayList<String> customerEmail, final ArrayList<String> arrayAddres1, final ArrayList<String> arrayAddress2, final ArrayList<String> arrayCity, final ArrayList<String> customerState, final ArrayList<String> arrayPin, final ArrayList<String> arrayuniqeid) {

        if (customerID == null) {
        } else {
            for (int i = 0; i < customerID.size(); i++) {
                String TempDimen = customerID.get(i);
                if (TempDimen.equals("")) {
                    customerID.set(i, "0");
                } else {
                }
            }
        }

        if (customerName == null) {
        } else {
            for (int i = 0; i < customerName.size(); i++) {
                String TempDimen = customerName.get(i);

                if (TempDimen.equals("")) {
                    customerName.set(i, "0");
                } else {
                }
            }
        }

        if (customerMObile == null) {
        } else {
            for (int i = 0; i < customerMObile.size(); i++) {
                String TempDimen = customerMObile.get(i);

                if (TempDimen.equals("")) {
                    customerMObile.set(i, "0");
                } else {
                }
            }
        }

        if (customerEmail == null) {
        } else {
            for (int i = 0; i < customerEmail.size(); i++) {
                String TempDimen = customerEmail.get(i);

                if (TempDimen.equals("")) {
                    customerEmail.set(i, "0");
                } else {
                }
            }
        }

        if (arrayAddres1 == null) {
        } else {
            for (int i = 0; i < arrayAddres1.size(); i++) {
                String TempDimen = customerEmail.get(i);

                if (TempDimen.equals("")) {
                    arrayAddres1.set(i, "0");
                } else {
                }
            }
        }

        if (arrayAddress2 == null) {
        } else {
            for (int i = 0; i < arrayAddress2.size(); i++) {
                String TempDimen = arrayAddress2.get(i);

                if (TempDimen.equals("")) {
                    arrayAddress2.set(i, "0");
                } else {
                }
            }
        }

        if (arrayCity == null) {
        } else {
            for (int i = 0; i < arrayCity.size(); i++) {
                String TempDimen = arrayCity.get(i);

                if (TempDimen.equals("")) {
                    arrayCity.set(i, "0");
                } else {
                }
            }
        }

        if (customerState == null) {
        } else {
            for (int i = 0; i < customerState.size(); i++) {
                String TempDimen = customerState.get(i);

                if (TempDimen.equals("")) {
                    customerState.set(i, "0");
                } else {
                }
            }
        }

        if (arrayPin == null) {
        } else {
            for (int i = 0; i < arrayPin.size(); i++) {
                String TempDimen = arrayPin.get(i);

                if (TempDimen.equals("")) {
                    arrayPin.set(i, "0");
                } else {
                }
            }
        }

        if (arrayuniqeid == null) {
        } else {
            for (int i = 0; i < arrayuniqeid.size(); i++) {
                String TempDimen = arrayuniqeid.get(i);

                if (TempDimen.equals("")) {
                    arrayuniqeid.set(i, "0");
                } else {
                }
            }
        }


        String insertUrl = "http://navy.microlan.in/api2/syncCustomerAddressTable";
        //  String insertUrl = "https://papasmart.online/api2/syncOrderDataDetailsTable";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");
                    if (stat.equals("1")) {
                        String msg = jsonObj.getString("msg");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();


                        for (int j = 0; j < arrayaddressListitem.size(); j++) {
                            Log.d("", "arrayListgst" + arrayaddressListitem.get(j).getFlag());
                            String id = arrayaddressListitem.get(j).getFlag();
                            String city = arrayaddressListitem.get(j).getTownCity();
                            String customerid = arrayaddressListitem.get(j).getCustomerId();
                            String pincode = arrayaddressListitem.get(j).getPincode();
                            String ares = arrayaddressListitem.get(j).getLandmarkNearestArea();
                            String name = arrayaddressListitem.get(j).getFullName();
                            String email = arrayaddressListitem.get(j).getEmailAddress();
                            String address = arrayaddressListitem.get(j).getAddress1();
                            String address2 = arrayaddressListitem.get(j).getAddress2();
                            String state = arrayaddressListitem.get(j).getState();
                            String mobile = arrayaddressListitem.get(j).getMobileNumber();
                            String stateCode = arrayaddressListitem.get(j).getStateCode();
                            String uniq = arrayaddressListitem.get(j).getUniqeid();
                            String type = arrayaddressListitem.get(j).getAddressType();


                            if (id.equals("0")) {

                                AllCustomerAddressModel recordingItem = new AllCustomerAddressModel(city, customerid, pincode, ares, name, email, type, address, address2, state, mobile, stateCode, "1", uniq);
                                dbAddaddress.updateaddress(recordingItem);

                            }


                        }


                    } else {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print(e.toString());
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
                Log.d("sdfsdfdsaa", "ssdddddadd " + error.toString());
                //   progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
//                    private void addorderlist(ArrayList<String> user_id, ArrayList<String> orderNumber, ArrayList<String> paymentMode, ArrayList<String> address, ArrayList<String> totalAmount, ArrayList<String> userName, ArrayList<String> customer_id, ArrayList<String> walletAmount, ArrayList<String> discount, ArrayList<String> discout_type, ArrayList<String> cashAmount, ArrayList<String> order_type, ArrayList<String> dateTime) {

                if (customerID == null) {
                    parameters.put("user_id", "");

                } else {
                    parameters.put("user_id", customerID.toString().substring(1, customerID.toString().length() - 1));
                }
                if (customerName == null) {
                    parameters.put("full_name", "");

                } else {
                    parameters.put("full_name", customerName.toString().substring(1, customerName.toString().length() - 1));
                }
                if (customerMObile == null) {
                    parameters.put("mobile_no", "");

                } else {
                    parameters.put("mobile_no", customerMObile.toString().substring(1, customerMObile.toString().length() - 1));
                }
                if (customerEmail == null) {
                    parameters.put("email_id", "");

                } else {
                    parameters.put("email_id", customerEmail.toString().substring(1, customerEmail.toString().length() - 1));
                }
                if (arrayAddres1 == null) {
                    parameters.put("address1", "");

                } else {
                    parameters.put("address1", arrayAddres1.toString().substring(1, arrayAddres1.toString().length() - 1));
                }
                if (arrayAddress2 == null) {
                    parameters.put("address2", "");

                } else {
                    parameters.put("address2", arrayAddress2.toString().substring(1, arrayAddress2.toString().length() - 1));
                }
                if (arrayCity == null) {
                    parameters.put("landmark_nearest_area", "");

                } else {
                    parameters.put("landmark_nearest_area", arrayCity.toString().substring(1, arrayCity.toString().length() - 1));
                }
                if (arrayCity == null) {
                    parameters.put("town_city", "");

                } else {
                    parameters.put("town_city", arrayCity.toString().substring(1, arrayCity.toString().length() - 1));
                }
                if (arrayPin == null) {
                    parameters.put("pincode", "");

                } else {
                    parameters.put("pincode", arrayPin.toString().substring(1, arrayPin.toString().length() - 1));
                }
                if (customerState == null) {
                    parameters.put("state", "");

                } else {
                    parameters.put("state", customerState.toString().substring(1, customerState.toString().length() - 1));
                }
                if (arrayuniqeid == null) {
                    parameters.put("unique_id", "");

                } else {
                    parameters.put("unique_id", arrayuniqeid.toString().substring(1, arrayuniqeid.toString().length() - 1));
                }


                Log.d("dsssssa", "Customer Addrsss " + parameters);

                return parameters;
            }
        };
        requestQueue.add(request);


    }

    private void Customerlist() {


        arraycustomerListitem = dbAddCustomer.getallflagcustomers("0");


        CustomerID.clear();
        CustomerName.clear();
        CustomerAddress.clear();
        CustomerState.clear();
        CustomerEmail.clear();
        CustomerMObile.clear();
        Customeruniqe.clear();

        Log.d("", "arraycustomerListitem.size()" + arraycustomerListitem.size());
        if (arraycustomerListitem.size() == 0) {

        } else {
            for (int i = 0; i < arraycustomerListitem.size(); i++) {

                String customerIDString = arraycustomerListitem.get(i).getCustomerid();
                String customerNameString = arraycustomerListitem.get(i).getCustomername();
                String customerMobileString = arraycustomerListitem.get(i).getCustomernumber();
                String customeruniqe = arraycustomerListitem.get(i).getUniqeid();
                String email = arraycustomerListitem.get(i).getCustomeremail();


                Log.d("", "customerNameString" + customerNameString);
                CustomerID.add(customerIDString);
                CustomerName.add(customerNameString);
                CustomerMObile.add(customerMobileString);
                CustomerEmail.add(email);
                Customeruniqe.add(customeruniqe);

                // CustomerAddress.add(customerAddress1String + ", " + customerAddress2String + ", " + customerCityString);
                //  CustomerState.add(customerStateString +"  ,  "+customerPencodeString);


            }
            addcustomer(CustomerID, CustomerName, CustomerMObile, CustomerEmail, Customeruniqe);


        }


    }

    private void addcustomer(final ArrayList<String> customerID, final ArrayList<String> customerName, final ArrayList<String> customerMObile, final ArrayList<String> customerEmail, final ArrayList<String> customeruniqe) {

        if (customerID == null) {
        } else {
            for (int i = 0; i < customerID.size(); i++) {
                String TempDimen = customerID.get(i);
                if (TempDimen.equals("")) {
                    customerID.set(i, "0");
                } else {
                }
            }
        }

        if (customerName == null) {
        } else {
            for (int i = 0; i < customerName.size(); i++) {
                String TempDimen = customerName.get(i);

                if (TempDimen.equals("")) {
                    customerName.set(i, "0");
                } else {
                }
            }
        }

        if (customerMObile == null) {
        } else {
            for (int i = 0; i < customerMObile.size(); i++) {
                String TempDimen = customerMObile.get(i);

                if (TempDimen.equals("")) {
                    customerMObile.set(i, "0");
                } else {
                }
            }
        }

        if (customerEmail == null) {
        } else {
            for (int i = 0; i < customerEmail.size(); i++) {
                String TempDimen = customerEmail.get(i);

                if (TempDimen.equals("")) {
                    customerEmail.set(i, "0");
                } else {
                }
            }
        }

        if (customeruniqe == null) {
        } else {
            for (int i = 0; i < customeruniqe.size(); i++) {
                String TempDimen = customeruniqe.get(i);

                if (TempDimen.equals("")) {
                    customeruniqe.set(i, "0");
                } else {
                }
            }
        }


        String insertUrl = "http://navy.microlan.in/api2/syncCustomerTable";
        //  String insertUrl = "https://papasmart.online/api2/syncOrderDataDetailsTable";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressDialog.cancel();

                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");
                    if (stat.equals("1")) {
                        String msg = jsonObj.getString("msg");
                        Log.d("", "msg" + msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();


                        for (int j = 0; j < arraycustomerListitem.size(); j++) {

                            Log.d("", "arrayListgst" + arraycustomerListitem.get(j).getCustomerid());
                            String id = arraycustomerListitem.get(j).getFlag();
                            String customerIDString = arraycustomerListitem.get(j).getCustomerid();
                            String customerNameString = arraycustomerListitem.get(j).getCustomername();
                            String customerMobileString = arraycustomerListitem.get(j).getCustomernumber();
                            String email_address = arraycustomerListitem.get(j).getCustomeremail();
                            String unique_id = arraycustomerListitem.get(j).getUniqeid();


                            arraycustomerListitem = dbAddCustomer.getallflagcustomers(id);
                            String flg = arraycustomerListitem.get(0).getFlag();
                            Log.d("", "CUSTflg" + flg);
                            if (flg.equals("0")) {
                                ALLCustomerModel recordingItem = new ALLCustomerModel(customerIDString, customerNameString, customerMobileString, email_address, "", "", "", "", "", "", "1", unique_id);
                                dbAddCustomer.updatecustomer(recordingItem);

                            }


                        }


                    } else {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print(e.toString());
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
                Log.d("sdfsdfdas", "ssssssadd " + error.toString());
                //   progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                //    private void addorderlist(ArrayList<String> user_id, ArrayList<String> orderNumber, ArrayList<String> paymentMode, ArrayList<String> address, ArrayList<String> totalAmount, ArrayList<String> userName, ArrayList<String> customer_id, ArrayList<String> walletAmount, ArrayList<String> discount, ArrayList<String> discout_type, ArrayList<String> cashAmount, ArrayList<String> order_type, ArrayList<String> dateTime) {

                if (customerName == null) {
                    parameters.put("full_name", "");

                } else {
                    parameters.put("full_name", customerName.toString().substring(1, customerName.toString().length() - 1));
                }
                if (customerMObile == null) {
                    parameters.put("mobile_no", "");

                } else {
                    parameters.put("mobile_no", customerMObile.toString().substring(1, customerMObile.toString().length() - 1));
                }
                if (customerEmail == null) {
                    parameters.put("email_id", "");

                } else {
                    parameters.put("email_id", customerEmail.toString().substring(1, customerEmail.toString().length() - 1));
                }
                if (customeruniqe == null) {
                    parameters.put("unique_id", "");

                } else {
                    parameters.put("unique_id", customeruniqe.toString().substring(1, customeruniqe.toString().length() - 1));
                }


                Log.d("dsscus", "Customer Data " + parameters);

                return parameters;
            }
        };
        requestQueue.add(request);


    }

    private void orderdetailslist() {

        arrayListorderfetails = dborderdetails.getallorderdetails(UserID, "0");

        Log.d("", "arrayListorderfetails.size" + arrayListorderfetails.size());

        OrderProductName.clear();
        ProductQuantity.clear();
        inglePrice.clear();
        OrderProductPrice.clear();
        ProductImage.clear();
        SrNo.clear();
        color.clear();
        size.clear();
        unit.clear();
        Orderid.clear();
        OrderNumber.clear();
        User_id.clear();

        //try {
        // JSONArray details = new JSONArray(getIntent().getStringExtra(""));

        if (arrayListorderfetails.size() == 0) {

        } else {
            for (int i = 0; i < arrayListorderfetails.size(); i++) {
                String productNameString = arrayListorderfetails.get(i).getProduct_name();
                String inglePriceString = arrayListorderfetails.get(i).getPrice();
                String productPriceString = arrayListorderfetails.get(i).getUnit_total();
                String productQuantityString = arrayListorderfetails.get(i).getQty();
                String productImageString = arrayListorderfetails.get(i).getImage_name();
                String productOrderString = arrayListorderfetails.get(i).getOrder_number();
                String orderid = arrayListorderfetails.get(i).getOrder_id();
                String productid = arrayListorderfetails.get(i).getProduct_id();
                String colors = arrayListorderfetails.get(i).getProduct_color();
                String sizes = arrayListorderfetails.get(i).getPack_size();
                String packsizes = arrayListorderfetails.get(i).getProduct_color();
                String units = arrayListorderfetails.get(i).getPack_unit();

                productOrderString.split("\\s+");
                OrderProductName.add(productNameString);
                ProductQuantity.add(productQuantityString);
                inglePrice.add(inglePriceString);
                OrderProductPrice.add(productPriceString);
                ProductImage.add(productImageString);
                Orderid.add(orderid);
                OrderNumber.add(productOrderString);
                ProductId.add(productid);

                color.add(colors);
                size.add(sizes);
                packsize.add(packsizes);
                unit.add(units);
                User_id.add(UserID);

                for (int j = 0; j < ProductName.size(); j++) {
                    SrNo.add(String.valueOf(i + 1));
                }


            }
            addOrderdetails(Orderid, OrderNumber, User_id, ProductId, OrderProductName, OrderProductPrice, ProductQuantity, size, packsize, unit, color, inglePrice);

        }


    }

    private void addOrderdetails(final ArrayList<String> orderid, final ArrayList<String> orderNumber, final ArrayList<String> user_id, final ArrayList<String> productId, final ArrayList<String> productName, final ArrayList<String> orderProductPrice, final ArrayList<String> productQuantity, final ArrayList<String> size, final ArrayList<String> packsize, final ArrayList<String> unit, final ArrayList<String> color, final ArrayList<String> inglePrice) {

        if (user_id == null) {
        } else {
            for (int i = 0; i < user_id.size(); i++) {
                String TempDimen = user_id.get(i);
                if (TempDimen.equals("")) {
                    user_id.set(i, "0");
                } else {
                }
            }
        }

        if (orderNumber == null) {
        } else {
            for (int i = 0; i < orderNumber.size(); i++) {
                String TempDimen = orderNumber.get(i);

                if (TempDimen.equals("")) {
                    orderNumber.set(i, "0");
                } else {
                }
            }
        }

        if (orderid == null) {
        } else {
            for (int i = 0; i < orderid.size(); i++) {
                String TempDimen = orderid.get(i);

                if (TempDimen.equals("")) {
                    orderid.set(i, "0");
                } else {
                }
            }
        }

        if (productId == null) {
        } else {
            for (int i = 0; i < productId.size(); i++) {
                String TempDimen = productId.get(i);

                if (TempDimen.equals("")) {
                    productId.set(i, "0");
                } else {
                }
            }
        }

        if (productName == null) {
        } else {
            for (int i = 0; i < productName.size(); i++) {
                String TempDimen = productName.get(i);

                if (TempDimen.equals("")) {
                    productName.set(i, "0");
                } else {
                }
            }
        }

        if (orderProductPrice == null) {
        } else {
            for (int i = 0; i < orderProductPrice.size(); i++) {
                String TempDimen = orderProductPrice.get(i);

                if (TempDimen.equals("")) {
                    orderProductPrice.set(i, "0");
                } else {
                }
            }
        }

        if (productQuantity == null) {
        } else {
            for (int i = 0; i < productQuantity.size(); i++) {
                String TempDimen = productQuantity.get(i);

                if (TempDimen.equals("")) {
                    productQuantity.set(i, "0");
                } else {
                }
            }
        }

        if (size == null) {
        } else {
            for (int i = 0; i < size.size(); i++) {
                String TempDimen = size.get(i);

                if (TempDimen.equals("")) {
                    size.set(i, "0");
                } else {
                }
            }
        }

        if (packsize == null) {
        } else {
            for (int i = 0; i < packsize.size(); i++) {
                String TempDimen = packsize.get(i);

                if (TempDimen.equals("")) {
                    packsize.set(i, "0");
                } else {
                }
            }
        }
        if (unit == null) {
        } else {
            for (int i = 0; i < unit.size(); i++) {
                String TempDimen = unit.get(i);

                if (TempDimen.equals("")) {
                    unit.set(i, "0");
                } else {
                }
            }
        }
        if (color == null) {
        } else {
            for (int i = 0; i < color.size(); i++) {
                String TempDimen = color.get(i);

                if (TempDimen.equals("")) {
                    color.set(i, "0");
                } else {
                }
            }
        }
        if (inglePrice == null) {
        } else {
            for (int i = 0; i < inglePrice.size(); i++) {
                String TempDimen = inglePrice.get(i);

                if (TempDimen.equals("")) {
                    inglePrice.set(i, "0");
                } else {
                }
            }
        }

        String insertUrl = "http://navy.microlan.in/api2/syncOrderDataDetailsTable";
        //  String insertUrl = "https://papasmart.online/api2/syncOrderDataDetailsTable";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressDialog.cancel();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                Toast.makeText(MainActivity.this, finalResponse, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");
                    if (stat.equals("1")) {
                        String msg = jsonObj.getString("msg");
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                        for (int j = 0; j < arrayListorderfetails.size(); j++) {
                            String id = arrayListorderfetails.get(j).getFlag();
                            Log.d("", "arrayListorderfetails.get(j).getFlag()" + arrayListorderfetails.get(j).getFlag());
                            String order_id = arrayListorderfetails.get(j).getOrder_id();
                            String productNameString = arrayListorderfetails.get(j).getProduct_name();
                            String productSinglePriceString = arrayListorderfetails.get(j).getPrice();
                            String productQuantityString = arrayListorderfetails.get(j).getQty();
                            String productPriceString = arrayListorderfetails.get(j).getUnit_total();
                            String orderNumberString = arrayListorderfetails.get(j).getOrder_number();
                            String user_ids = arrayListorderfetails.get(j).getUser_id();
                            String units = arrayListorderfetails.get(j).getPack_unit();
                            String color = arrayListorderfetails.get(j).getProduct_color();
                            String size = arrayListorderfetails.get(j).getPack_size();
                            String prodsize = arrayListorderfetails.get(j).getProduct_size();


                            if (id.equals("0")) {


                                OrderDataDetailsModel orderdetails = new OrderDataDetailsModel(order_id, "", productNameString, "", productSinglePriceString, productQuantityString, size, prodsize, units, color, productPriceString, user_ids, order_id, orderNumberString, "1");
                                dborderdetails.updateexitorderdetails(orderdetails);
                            }
                        }


                    } else {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print(e.toString());
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
                //server error
                Log.d("sdfsdfdsamad", "addsssss " + error.toString());
                //   progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                //    private void addorderlist(ArrayList<String> user_id, ArrayList<String> orderNumber, ArrayList<String> paymentMode, ArrayList<String> address, ArrayList<String> totalAmount, ArrayList<String> userName, ArrayList<String> customer_id, ArrayList<String> walletAmount, ArrayList<String> discount, ArrayList<String> discout_type, ArrayList<String> cashAmount, ArrayList<String> order_type, ArrayList<String> dateTime) {

                if (user_id == null) {
                    parameters.put("userid", "");

                } else {
                    parameters.put("userid", user_id.toString().substring(1, user_id.toString().length() - 1));
                }
                if (orderNumber == null) {
                    parameters.put("order_number", "");

                } else {
                    parameters.put("order_number", orderNumber.toString().substring(1, orderNumber.toString().length() - 1));
                }
                if (orderid == null) {
                    parameters.put("order_id", "");

                } else {
                    parameters.put("order_id", orderid.toString().substring(1, orderid.toString().length() - 1));
                }
                if (productId == null) {
                    parameters.put("productid", "");

                } else {
                    parameters.put("productid", productId.toString().substring(1, productId.toString().length() - 1));
                }
                if (productName == null) {
                    parameters.put("productname", "");

                } else {
                    parameters.put("productname", productName.toString().substring(1, productName.toString().length() - 1));
                }
                if (inglePrice == null) {
                    parameters.put("price", "");

                } else {
                    parameters.put("price", inglePrice.toString().substring(1, inglePrice.toString().length() - 1));
                }
                if (productQuantity == null) {
                    parameters.put("qty", "");

                } else {
                    parameters.put("qty", productQuantity.toString().substring(1, productQuantity.toString().length() - 1));
                }
                if (orderProductPrice == null) {
                    parameters.put("unit_total", "");

                } else {
                    parameters.put("unit_total", orderProductPrice.toString().substring(1, orderProductPrice.toString().length() - 1));
                }
                if (size == null) {
                    parameters.put("size", "");

                } else {
                    parameters.put("size", size.toString().substring(1, size.toString().length() - 1));
                }
                if (packsize == null) {
                    parameters.put("product_size", "");

                } else {
                    parameters.put("product_size", packsize.toString().substring(1, packsize.toString().length() - 1));
                }
                if (unit == null) {
                    parameters.put("unit", "");

                } else {
                    parameters.put("unit", unit.toString().substring(1, unit.toString().length() - 1));
                }
                if (color == null) {
                    parameters.put("product_color", "");

                } else {
                    parameters.put("product_color", color.toString().substring(1, color.toString().length() - 1));
                }


                Log.d("dsssssa", "Order Detailk " + parameters);

                return parameters;
            }
        };
        requestQueue.add(request);


    }

    public void orderlist() {

        arrayListorderdata = dborderdata.getorderdataflag(UserID, "0");


        Log.d("arrayListorderdata", "arrayListorderdata" + arrayListorderdata.size());
        UserName.clear();
        OrderNumber.clear();
        TotalAmount.clear();
        DateTime.clear();
        Address.clear();
        WalletAmount.clear();
        Discount.clear();
        CashAmount.clear();
        address_id.clear();
        PaymentMode.clear();
        Orderid.clear();
        Order_type.clear();
        User_id.clear();
        Discout_type.clear();
        Customer_id.clear();
        CGST.clear();
        SGST.clear();
        IGST.clear();


        if (arrayListorderdata.size() == 0) {

        } else {
            {
                for (int i = 0; i < arrayListorderdata.size(); i++) {

                    String userNameString = arrayListorderdata.get(i).getCc_user_name();
                    String orderNumberString = arrayListorderdata.get(i).getOrder_number();
                    String totalAmountString = arrayListorderdata.get(i).getTotal_amount();
                    String address = arrayListorderdata.get(i).getAddress_id();
                    String dateTimeString = arrayListorderdata.get(i).getOrder_date_time();
                    String payment_method = arrayListorderdata.get(i).getPayment_method();
                    String wallet_amount = arrayListorderdata.get(i).getWallet_amount();
                    String discount = arrayListorderdata.get(i).getDiscount();
                    String cash_amount = arrayListorderdata.get(i).getCash_amount();
                    String orderid = arrayListorderdata.get(i).getOrderid();
                    String cust_id = arrayListorderdata.get(i).getCust_id();
                    String discout_type = arrayListorderdata.get(i).getDiscount_type();
                    String flag = arrayListorderdata.get(i).getFlag();
                    String source = arrayListorderdata.get(i).getOrder_source();
                    String cgst = arrayListorderdata.get(i).getCgst();
                    String igst = arrayListorderdata.get(i).getIgst();
                    String sgst = arrayListorderdata.get(i).getSgst();

                    UserName.add(userNameString);
                    OrderNumber.add(orderNumberString);
                    TotalAmount.add(totalAmountString);
                    Address.add(address);
                    DateTime.add(dateTimeString);
                    PaymentMode.add(payment_method);

                    WalletAmount.add(wallet_amount);
                    Discount.add(discount);
                    CashAmount.add(cash_amount);
                    Orderid.add(orderid);
                    Customer_id.add(cust_id);
                    User_id.add(UserID);
                    Discout_type.add(discout_type);

                    Order_type.add(source);
                    CGST.add(cgst);
                    SGST.add(igst);
                    IGST.add(sgst);


                }

                addorderlist(User_id, OrderNumber, PaymentMode, Address, TotalAmount, UserName, Customer_id, WalletAmount, Discount, Discout_type, CashAmount, Order_type, DateTime, CGST, SGST, IGST);


            }

        }

    }

    private void addorderlist(final ArrayList<String> user_id, final ArrayList<String> orderNumber, final ArrayList<String> paymentMode, final ArrayList<String> address, final ArrayList<String> totalAmount, final ArrayList<String> userName, final ArrayList<String> customer_id, final ArrayList<String> walletAmount, final ArrayList<String> discount, final ArrayList<String> discout_type, final ArrayList<String> cashAmount, final ArrayList<String> order_type, final ArrayList<String> dateTime, final ArrayList<String> CGST, final ArrayList<String> SGST, final ArrayList<String> IGST) {

        if (user_id == null) {
        } else {
            for (int i = 0; i < user_id.size(); i++) {
                String TempDimen = user_id.get(i);
                if (TempDimen.equals("")) {
                    user_id.set(i, "0");
                } else {
                }
            }
        }
        if (CGST == null) {
        } else {
            for (int i = 0; i < CGST.size(); i++) {
                String TempDimen = CGST.get(i);
                if (TempDimen.equals("")) {
                    CGST.set(i, "0");
                } else {
                }
            }
        }
        if (SGST == null) {
        } else {
            for (int i = 0; i < SGST.size(); i++) {
                String TempDimen = SGST.get(i);
                if (TempDimen.equals("")) {
                    SGST.set(i, "0");
                } else {
                }
            }
        }
        if (IGST == null) {
        } else {
            for (int i = 0; i < IGST.size(); i++) {
                String TempDimen = IGST.get(i);
                if (TempDimen.equals("")) {
                    IGST.set(i, "0");
                } else {
                }
            }
        }

        if (orderNumber == null) {
        } else {
            for (int i = 0; i < orderNumber.size(); i++) {
                String TempDimen = orderNumber.get(i);
                if (TempDimen.equals("")) {
                    orderNumber.set(i, "0");
                } else {
                }
            }
        }

        if (paymentMode == null) {
        } else {
            for (int i = 0; i < paymentMode.size(); i++) {
                String TempDimen = paymentMode.get(i);

                if (TempDimen.equals("")) {
                    paymentMode.set(i, "0");
                } else {
                }
            }
        }

        if (address == null) {
        } else {
            for (int i = 0; i < address.size(); i++) {
                String TempDimen = address.get(i);

                if (TempDimen.equals("")) {
                    address.set(i, "0");
                } else {
                }
            }
        }

        if (totalAmount == null) {
        } else {
            for (int i = 0; i < totalAmount.size(); i++) {
                String TempDimen = totalAmount.get(i);

                if (TempDimen.equals("")) {
                    totalAmount.set(i, "0");
                } else {
                }
            }
        }

        if (userName == null) {
        } else {
            for (int i = 0; i < userName.size(); i++) {
                String TempDimen = userName.get(i);

                if (TempDimen.equals("")) {
                    userName.set(i, "0");
                } else {
                }
            }
        }

        if (customer_id == null) {
        } else {
            for (int i = 0; i < customer_id.size(); i++) {
                String TempDimen = customer_id.get(i);

                if (TempDimen.equals("")) {
                    customer_id.set(i, "0");
                } else {
                }
            }
        }

        if (walletAmount == null) {
        } else {
            for (int i = 0; i < walletAmount.size(); i++) {
                String TempDimen = walletAmount.get(i);

                if (TempDimen.equals("")) {
                    walletAmount.set(i, "0");
                } else {
                }
            }
        }

        if (discount == null) {
        } else {
            for (int i = 0; i < discount.size(); i++) {
                String TempDimen = discount.get(i);

//                if (TempDimen.equals("")) {
//                    discount.set(i, "0");
//                } else {
//                }
            }
        }
        if (discout_type == null) {
        } else {
            for (int i = 0; i < discout_type.size(); i++) {
                String TempDimen = discout_type.get(i);

                if (TempDimen.equals("")) {
                    discout_type.set(i, "0");
                } else {
                }
            }
        }
        if (cashAmount == null) {
        } else {
            for (int i = 0; i < cashAmount.size(); i++) {
                String TempDimen = cashAmount.get(i);

                if (TempDimen.equals("")) {
                    cashAmount.set(i, "0");
                } else {
                }
            }
        }
        if (order_type == null) {
        } else {
            for (int i = 0; i < order_type.size(); i++) {
                String TempDimen = order_type.get(i);

                if (TempDimen.equals("")) {
                    order_type.set(i, "0");
                } else {
                }
            }
        }
        if (dateTime == null) {
        } else {
            for (int i = 0; i < dateTime.size(); i++) {
                String TempDimen = dateTime.get(i);

                if (TempDimen.equals("")) {
                    dateTime.set(i, "0");
                } else {
                }
            }
        }

        // String insertUrl = "https://papasmart.online/api2/syncOrderDataTable";
        String insertUrl = "http://navy.microlan.in/api2/syncOrderDataTable";
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  progressDialog.cancel();
                Toast.makeText(MainActivity.this, "data inserted successfully", Toast.LENGTH_SHORT).show();
                System.out.println(response);
                String finalResponse = response.substring(1);
                System.out.println(finalResponse);
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    String stat = jsonObj.getString("status");
                    if (stat.equals("1")) {
                        String msg = jsonObj.getString("msg");
                        Log.d("", "Scan Order details" + msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();


                        for (int j = 0; j < arrayListorderdata.size(); j++) {
                            Log.d("", "arrayListgst" + arrayListorderdata.get(j).getOrder_number());
                            String id = arrayListorderdata.get(j).getFlag();
                            String orderNumberString = arrayListorderdata.get(j).getOrder_number();
                            String totalAmountString = arrayListorderdata.get(j).getTotal_amount();
                            String payment_method = arrayListorderdata.get(j).getPayment_method();
                            String address = arrayListorderdata.get(j).getAddress_id();
                            String userNameString = arrayListorderdata.get(j).getCc_user_name();
                            String dateTimeString = arrayListorderdata.get(j).getOrder_date_time();
                            String wallet_amount = arrayListorderdata.get(j).getWallet_amount();
                            String discount_type = arrayListorderdata.get(j).getDiscount_type();
                            String cash_amount = arrayListorderdata.get(j).getCash_amount();
                            String discounts = arrayListorderdata.get(j).getDiscount();
                            String user_ids = arrayListorderdata.get(j).getUser_id();
                            String igst = arrayListorderdata.get(j).getIgst();
                            String cgst = arrayListorderdata.get(j).getCgst();
                            String sgst = arrayListorderdata.get(j).getSgst();


                            if (id.equals("0")) {


                                Log.d("order flag", "OrderDataModel  Flag " + orderNumberString);

                                OrderDataModel recordingItem = new OrderDataModel(user_ids, orderNumberString, totalAmountString, payment_method, address, "3", userNameString, dateTimeString, wallet_amount, discounts, discount_type, cash_amount, "", totalAmountString, "1", igst, cgst, sgst);
                                dborderdata.updateorderdata(recordingItem);


                            }
                        }


                    } else {

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print(e.toString());
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
                Log.d("sdfsdfd", "addddd " + error.toString());
                //   progressDialog.cancel();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                //    private void addorderlist(ArrayList<String> user_id, ArrayList<String> orderNumber, ArrayList<String> paymentMode, ArrayList<String> address, ArrayList<String> totalAmount, ArrayList<String> userName, ArrayList<String> customer_id, ArrayList<String> walletAmount, ArrayList<String> discount, ArrayList<String> discout_type, ArrayList<String> cashAmount, ArrayList<String> order_type, ArrayList<String> dateTime) {

                if (user_id == null) {
                    parameters.put("userid", "");

                } else {
                    parameters.put("userid", user_id.toString().substring(1, user_id.toString().length() - 1));
                }
                if (orderNumber == null) {
                    parameters.put("order_number", "");

                } else {
                    parameters.put("order_number", orderNumber.toString().substring(1, orderNumber.toString().length() - 1));
                }
                if (paymentMode == null) {
                    parameters.put("payment_method", "");

                } else {
                    parameters.put("payment_method", paymentMode.toString().substring(1, paymentMode.toString().length() - 1));
                }
                if (address == null) {
                    parameters.put("deliver_address", "");

                } else {
                    parameters.put("deliver_address", address.toString().substring(1, address.toString().length() - 1));
                }
                if (totalAmount == null) {
                    parameters.put("total_amount", "");

                } else {
                    parameters.put("total_amount", totalAmount.toString().substring(1, totalAmount.toString().length() - 1));
                }
                if (userName == null) {
                    parameters.put("customer_name", "");

                } else {
                    parameters.put("customer_name", userName.toString().substring(1, userName.toString().length() - 1));
                }
                if (customer_id == null) {
                    parameters.put("customer_id", "");

                } else {
                    parameters.put("customer_id", customer_id.toString().substring(1, customer_id.toString().length() - 1));
                }
                if (walletAmount == null) {
                    parameters.put("wallet_amount", "");

                } else {
                    parameters.put("wallet_amount", walletAmount.toString().substring(1, walletAmount.toString().length() - 1));
                }
                if (discount == null) {
                    parameters.put("discount", "");

                } else {
                    parameters.put("discount", discount.toString().substring(1, discount.toString().length() - 1));
                }
                if (discout_type == null) {
                    parameters.put("discount_type", "");

                } else {
                    parameters.put("discount_type", discout_type.toString().substring(1, discout_type.toString().length() - 1));
                }
                if (cashAmount == null) {
                    parameters.put("cash_amount", "");

                } else {
                    parameters.put("cash_amount", cashAmount.toString().substring(1, cashAmount.toString().length() - 1));
                }
                if (order_type == null) {
                    parameters.put("order_source", "");

                } else {
                    parameters.put("order_source", order_type.toString().substring(1, order_type.toString().length() - 1));
                }
                if (dateTime == null) {
                    parameters.put("order_date", "");

                } else {
                    parameters.put("order_date", dateTime.toString().substring(1, dateTime.toString().length() - 1));
                }
                if (CGST == null) {
                    parameters.put("cgst", "");

                } else {
                    parameters.put("cgst", CGST.toString().substring(1, CGST.toString().length() - 1));
                }
                if (SGST == null) {
                    parameters.put("sgst", "");

                } else {
                    parameters.put("sgst", SGST.toString().substring(1, SGST.toString().length() - 1));
                }
                if (IGST == null) {
                    parameters.put("igst", "");

                } else {
                    parameters.put("igst", IGST.toString().substring(1, IGST.toString().length() - 1));
                }
                if (dateTime == null) {
                    parameters.put("order_date", "");

                } else {
                    parameters.put("order_date", dateTime.toString().substring(1, dateTime.toString().length() - 1));
                }


                Log.d("dsssssa", "Order list" + parameters);

                return parameters;
            }
        };
        requestQueue.add(request);


    }

    private void getNotification() {

        Retrofit r = ApiClient.getClient();
        ApiInterface api = r.create(ApiInterface.class);


        Call<AnnouncementResponse> call = api.getnoti();
        call.enqueue(new Callback<AnnouncementResponse>() {
            @Override
            public void onResponse(Call<AnnouncementResponse> call, retrofit2.Response<AnnouncementResponse> response) {
                Log.d("TAG", "SignUPADRESS_finish_onResponse: " + "successful");
                String status = String.valueOf(response.body().getStatus());
                if (status.equals("1")) {
                    announcement = response.body().getAnnouncement();

                    NotificationAdapter adapter = new NotificationAdapter(MainActivity.this, announcement);
                    notificationrecy.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<AnnouncementResponse> call, Throwable t) {
                Log.d("TAG", "SignUPADRESS_Finish_onFailure: " + t.fillInStackTrace());
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_take_order) {
            Intent takeOrderIntent = new Intent(MainActivity.this, OfflineCounter.class);
            startActivity(takeOrderIntent);
        } else if (id == R.id.nav_order_list) {
            Intent OrdersIntent = new Intent(MainActivity.this, Orders.class);
            startActivity(OrdersIntent);
        } else if (id == R.id.nav_order_list_offile) {
            Intent OrdersIntent = new Intent(MainActivity.this, OrderHostory.class);
            startActivity(OrdersIntent);
        } else if (id == R.id.nav_licenses) {
            Intent MembershipIntent = new Intent(MainActivity.this, PremiumMembership.class);
            startActivity(MembershipIntent);
        } else if (id == R.id.nav_faq) {
            Intent FAQIntent = new Intent(MainActivity.this, FAQ.class);
            startActivity(FAQIntent);
        } else if (id == R.id.nav_terms) {
            Intent TermsIntent = new Intent(MainActivity.this, TermsAndConditions.class);
            startActivity(TermsIntent);
        } else if (id == R.id.nav_com_set) {
            Intent TermsIntent = new Intent(MainActivity.this, CompanySetting.class);
            startActivity(TermsIntent);
        } else if (id == R.id.syncdata) {
            Customerlist();
            Addresslist();
            orderlist();
            orderdetailslist();


            SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
            editor.putString("Registered", encrypt("No"));
            editor.commit();





        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
