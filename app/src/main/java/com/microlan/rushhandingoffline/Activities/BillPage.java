package com.microlan.rushhandingoffline.Activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karan.churi.PermissionManager.PermissionManager;
import com.microlan.rushhandingoffline.Adapters.BillAdapter;
import com.microlan.rushhandingoffline.BuildConfig;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperBills;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomer;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperCustomerPoints;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperProductQty;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperTrendingProducts;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Util.BluetoothUtil;
import com.microlan.rushhandingoffline.Util.Utils;
import com.microlan.rushhandingoffline.Views.ExpandableHeightGridView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;


public class BillPage extends AppCompatActivity {

    ArrayList<String> SrNo,Cartid, Productid,ProductName, Quantity, Rate, Amount, QuantityAvailable, Customers, CustomerAvailablePoints, Gst,color,size,packsize,unit;
    BillAdapter billAdapter;
    ExpandableHeightGridView ProductsGrid;
    TextView OrderSubtotalText, CgstText, SgstText, IgstText, FinalTotalText, CustomerNameText, RewardPointsText,order_id;
    Float AmountInt = 0.0f;
    Button PrintBtn,shareBtn;
    String ProductsString = "";
    ImageView StoreImage;
    DatabaseHelperBills databaseHelperBills;
    DatabaseHelperProductQty databaseHelperProductQty;
    String PointsValue, GSTStat, PointsReceived;
    SharedPreferences sharedPreferences;
    LinearLayout GSTLayout, RewardPointsLayout, CGSTLayout, SGSTLayout, IGSTLayout;
    DatabaseHelperCustomerPoints databaseHelperCustomerPoints;
    boolean AddPoints = false;
    Float CustomerPoints = 0.0f;
    Float TempTotalAmount = 0.0f;
    Float GSTFloat = 0.0f;
    String State, CustomerState;
    DatabaseHelperCustomer databaseHelperCustomer;
    DatabaseHelperTrendingProducts databaseHelperTrendingProducts;
    String TotalAmount = "", IGSTString = "",SGSTString = "",CGSTString = "", SubTotalString = "",UserID,orderNo;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    LinearLayout printsharelay;
    Button saveorder;
    String gst_no,company_logo,billing_address,CustomerAddress,permissionStat,RegistrationStat,walletbalance;

    TextView comp_address,gst_id,customerAddressText,discount_amts,paymentmode,walletamt,orderbeforeText;
    LinearLayout ll_linear;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    File imagePath;
    private String sharePath="no",dicount,dicount_type,Payment,balanceamt,customerid,CustomerName;
    private static final int STORAGE_PERMISSION_CODE = 123;
    PermissionManager permissionManager;
    LinearLayout walletlay;
    OrderDataDBHelper dborderdata;
    OrderDataDetailDBHelper dborderdetails;
    ArrayList<OrderDataModel> arrayListitem;
    ItemInCartDBHelper dbAddItemHelpers;
    String GSTTYPE;
    double GstTotal=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_page);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Logo dynamc
        //Billing Address
        //Order no

        dborderdata = new OrderDataDBHelper(getApplicationContext());
        dborderdetails = new OrderDataDetailDBHelper(getApplicationContext());
        dbAddItemHelpers = new ItemInCartDBHelper(getApplicationContext());

        databaseHelperCustomer = new DatabaseHelperCustomer(BillPage.this);
        databaseHelperBills = new DatabaseHelperBills(BillPage.this);
        databaseHelperProductQty = new DatabaseHelperProductQty(BillPage.this);
        databaseHelperTrendingProducts = new DatabaseHelperTrendingProducts(BillPage.this);
        databaseHelperCustomerPoints = new DatabaseHelperCustomerPoints(BillPage.this);

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        GSTStat = sharedPreferences.getString("GSTEnable", encrypt("False"));
        GSTStat = decrypt(GSTStat);
        PointsValue = sharedPreferences.getString("point_value", "0");
        PointsReceived = sharedPreferences.getString("point_received", "0");
        State = sharedPreferences.getString("State", encrypt("None"));
        State = decrypt(State);
        UserID = sharedPreferences.getString("id", "");
        permissionStat = sharedPreferences.getString("permissionStat", encrypt("NotGranted"));
        RegistrationStat = sharedPreferences.getString("Registered", encrypt("No"));
        permissionStat = decrypt(permissionStat);

        company_logo = sharedPreferences.getString("company_logo","");
        billing_address = sharedPreferences.getString("billing_address","");
        gst_no = sharedPreferences.getString("gst_no","");

        Log.d("","company_logo"+company_logo);

        OrderSubtotalText = (TextView)findViewById(R.id.orderSubTotalText);
        CgstText = (TextView)findViewById(R.id.cgstText);
        SgstText = (TextView)findViewById(R.id.sgstText);
        IgstText = (TextView)findViewById(R.id.igstText);
        FinalTotalText = (TextView)findViewById(R.id.finalTotalText);
        CustomerNameText = (TextView)findViewById(R.id.customerNameText);
        order_id = (TextView)findViewById(R.id.order_id);
        comp_address = (TextView)findViewById(R.id.comp_address);
        discount_amts = (TextView)findViewById(R.id.discount_amts);
        walletamt = (TextView)findViewById(R.id.walletamt);
        orderbeforeText = (TextView)findViewById(R.id.orderbeforeText);


        gst_id = (TextView)findViewById(R.id.gst_id);
        StoreImage = (ImageView)findViewById(R.id.storeImage);

        PrintBtn = (Button)findViewById(R.id.printBtn);
        shareBtn = (Button)findViewById(R.id.share);
        saveorder = (Button)findViewById(R.id.saveorder);
        printsharelay=findViewById(R.id.printsharelay);
        customerAddressText=findViewById(R.id.customerAddressText);
        paymentmode=findViewById(R.id.paymentmode);
        ll_linear = (LinearLayout)findViewById(R.id.ll_linear);
        walletlay = (LinearLayout)findViewById(R.id.walletlay);


        comp_address.setText(billing_address);
        gst_id.setText(gst_no);
       // Picasso.get().load(company_logo).placeholder(R.drawable.microlan).into(StoreImage);
        if(company_logo.isEmpty())
        {
            Picasso.get().load(R.drawable.microlan).placeholder(R.drawable.microlan).into(StoreImage);

        }
        else {
            Picasso.get().load(company_logo).placeholder(R.drawable.microlan).into(StoreImage);

        }

        ProductsGrid = (ExpandableHeightGridView)findViewById(R.id.productsGrid);
        GSTLayout = (LinearLayout)findViewById(R.id.gstLayout);
        CGSTLayout = (LinearLayout)findViewById(R.id.cgstLayout);
        SGSTLayout = (LinearLayout)findViewById(R.id.sgstLayout);
        IGSTLayout = (LinearLayout)findViewById(R.id.igstLayout);

        SrNo = new ArrayList<String>();
        ProductName = new ArrayList<String>();
        Quantity = new ArrayList<String>();
        Rate = new ArrayList<String>();
        Gst = new ArrayList<String>();
        Amount = new ArrayList<String>();
        QuantityAvailable = new ArrayList<String>();
        Customers = new ArrayList<String>();
        CustomerAvailablePoints = new ArrayList<String>();
        color = new ArrayList<String>();
        size = new ArrayList<String>();
        packsize = new ArrayList<String>();
        unit = new ArrayList<String>();


        ProductName = (ArrayList<String>) getIntent().getSerializableExtra("Products");
        Quantity = (ArrayList<String>) getIntent().getSerializableExtra("Quantity");
        Rate = (ArrayList<String>) getIntent().getSerializableExtra("Prices");
        color = (ArrayList<String>) getIntent().getSerializableExtra("color");
        size = (ArrayList<String>) getIntent().getSerializableExtra("size");
        packsize = (ArrayList<String>) getIntent().getSerializableExtra("packsize");
        unit = (ArrayList<String>) getIntent().getSerializableExtra("unit");
        Amount = (ArrayList<String>)getIntent().getSerializableExtra("Amount");
        Cartid = (ArrayList<String>)getIntent().getSerializableExtra("cartid");
        Productid = (ArrayList<String>)getIntent().getSerializableExtra("Productid");

        SubTotalString = getIntent().getStringExtra("SubTotal");
        IGSTString = getIntent().getStringExtra("IGST");
        SGSTString = getIntent().getStringExtra("SGST");
        CGSTString = getIntent().getStringExtra("CGST");
        GSTTYPE = getIntent().getStringExtra("GSTTYPE");
        TotalAmount = getIntent().getStringExtra("TotalAmount");
      //  orderNo = getIntent().getStringExtra("orderNo");
        CustomerAddress = getIntent().getStringExtra("CustomerAddress");
        Payment = getIntent().getStringExtra("Payment");
        balanceamt = getIntent().getStringExtra("balanceamt");
        customerid = getIntent().getStringExtra("customerid");
        CustomerName  =getIntent().getStringExtra("CustomerName");
        dicount = getIntent().getStringExtra("dicount");
        dicount_type = getIntent().getStringExtra("dicount_type");
        walletbalance = getIntent().getStringExtra("walletbalance");


        Log.d("","SGSTString"+SGSTString);
        Log.d("","SGSTString"+CGSTString);
        Log.d("","SGSTString"+SGSTString);
        Log.d("","SGSTString"+TotalAmount);
        Log.d("","SGSTString"+SubTotalString);
        discount_amts.setText(""+dicount);
        paymentmode.setText(""+Payment);
        orderbeforeText.setText("₹ "+TotalAmount);

        OrderSubtotalText.setText("₹" + SubTotalString);

        if(CGSTString.equals("")||CGSTString.equals("0.0"))
        {
            CGSTLayout.setVisibility(View.GONE);
        }
        else {
            CgstText.setText(CGSTString);

        }
        if(SGSTString.equals("")||SGSTString.equals("0.0"))
        {
            SGSTLayout.setVisibility(View.GONE);
        }
        else {
            SgstText.setText(SGSTString);

        }
        if(IGSTString.equals("")||IGSTString.equals("0.0"))
        {
            IGSTLayout.setVisibility(View.GONE);
        }
        else {
            IgstText.setText(IGSTString);

        }

/*
        CgstText.setText(CGSTString);
        SgstText.setText(SGSTString);
        IgstText.setText(IGSTString);
*/
        FinalTotalText.setText(balanceamt);
        if(CustomerAddress.equals(""))
        {
            customerAddressText.setVisibility(View.GONE);
        }
        else {
            customerAddressText.setText("Address : "+CustomerAddress);

        }
        if(walletbalance.equals(""))
        {
            walletlay.setVisibility(View.GONE);
        }
        else {
            walletamt.setText(walletbalance);

        }


        for (int i = 0; i < ProductName.size();i++){
            SrNo.add(String.valueOf(i+1));
            Float TempPrice = Float.valueOf(Rate.get(i))*Float.valueOf(Quantity.get(i));
        }

        CustomerNameText.setText("Customer Name : " + getIntent().getStringExtra("CustomerName"));
        billAdapter = new BillAdapter(getApplicationContext(), SrNo, ProductName, Quantity, Rate, Amount,color,size,packsize,unit);
        ProductsGrid.setAdapter(billAdapter);
        ProductsGrid.setExpanded(true);


        saveorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // PlaceOrder(TotalAmount);
                Date todaysdate = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(todaysdate);
                System.out.println(date);


                DateFormat df = new SimpleDateFormat("HmsdMy", Locale.getDefault());
                String currentDateAndTime = df.format(new Date());
                Log.d("dates","currentTime"+currentDateAndTime);

                String orderno="DF"+currentDateAndTime;
                order_id.setText("Order No : "+orderno);

                Log.d("","orderno"+orderno);
              //  OrderDataModel recordingItem = new OrderDataModel( UserID,orderno,TotalAmount,Payment,CustomerAddress,"3",CustomerName,date,walletbalance,dicount,dicount_type,balanceamt,customerid,SubTotalString,"0");
                OrderDataModel recordingItem = new OrderDataModel( UserID,orderno,TotalAmount,Payment,CustomerAddress,"3",CustomerName,date,walletbalance,dicount,dicount_type,balanceamt,customerid,SubTotalString,"0",IGSTString,CGSTString,SGSTString);

                dborderdata.addorderdata(recordingItem);

               // arrayListitem = dborderdata.getorderdata(UserID,"0");
                arrayListitem = dborderdata.getorderdata();

                for(int j=0;j<arrayListitem.size();j++)
                {
                    Log.d("","order_id"+arrayListitem.get(0).getOrderid());
                    Log.d("","getOrder_number"+arrayListitem.get(0).getOrder_number());

                }
                Log.d("","arrayListitem"+arrayListitem);
                String order_ids=arrayListitem.get(0).getOrderid();
                String order_number=arrayListitem.get(0).getOrder_number();

                Log.d("","order_id"+order_ids);
                Log.d("","order_number"+order_number);

                for(int i=0;i<Cartid.size();i++)
                {
                    OrderDataDetailsModel orderdetails = new OrderDataDetailsModel( Cartid.get(i),Productid.get(i),ProductName.get(i),"",Rate.get(i),Quantity.get(i),packsize.get(i),size.get(i),unit.get(i),color.get(i),Amount.get(i),UserID,order_ids,orderno,"0");

                    dborderdetails.addorderdetails(orderdetails);

                }


                printsharelay.setVisibility(View.VISIBLE);
                saveorder.setVisibility(View.GONE);
                dbAddItemHelpers.deleteItemtable(UserID);






            }
        });
        PrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printIt("\n\n\tReceipt / Tax Invoice\n\n"+"Customer Name : " + getIntent().getStringExtra("CustomerName")+"\n\n--------------------------------" +
                        "\nSr.  Product  Qty  Rate  Amount\n--------------------------------\n\n"+
                        ProductsString + "\n--------------------------------\n\n\n" + "Sub Total : "+ OrderSubtotalText.getText().toString() + "\n" + "GST (18%): "+ IgstText.getText().toString() + "\n" + "Total : "+FinalTotalText.getText().toString()+"\n\n\n--------------------------------\n\n"+
                        "\n\tThank You\n\tFor Shopping With Us.\n\n\n--------------------------------");

                Intent intent=new Intent(BillPage.this,OfflineCounter.class);
                startActivity(intent);
                finish();


            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionStat.equals("Granted"))
                {
                    //takeScreenshot();
                    Bitmap bitmap1 = loadBitmapFromView(ll_linear, ll_linear.getWidth(), ll_linear.getHeight());
                    saveBitmap(bitmap1);
                    openScreenshot(imagePath);


                } else {
                    permissionManager = new PermissionManager() {};
                    permissionManager.checkAndRequestPermissions(BillPage.this);
                }
            }
        });



    }

   public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);

        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);

        v.draw(canvas);

        return b;
    }

    public void saveBitmap(Bitmap bitmap) {

        imagePath = new File("/sdcard/screenshotdemo.jpg");
        FileOutputStream fos = null;
        try {
            try {
                fos = new FileOutputStream(imagePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(),imagePath.getAbsolutePath()+"",Toast.LENGTH_SHORT).show();
            boolean_save = true;
            String filePath = imagePath.getPath();

            Log.d("","filePath"+filePath);
            sharePath = filePath;

            // sharedimension.setText("Check image");

            Log.e("ImageSave", "Saveimage"+boolean_save);

        }
        catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    private void openScreenshot(File imageFile) {
        Log.d("","imageFile"+imageFile);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(BillPage.this, BuildConfig.APPLICATION_ID + ".provider", imageFile));
        startActivity(Intent.createChooser(share, "Share Image"));
    }



    public void PlaceOrder (final String TotalAmount)
    {
        progressDialog = new ProgressDialog(BillPage.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        //Toast.makeText(BillPage.this, TotalAmount, Toast.LENGTH_SHORT).show();

        String insertUrl = "http://microlanpos.com/demo/api2/order_confirmation";
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

                    Log.d("","response"+response);
                    if (stat.equals("1")) {
                        String orderNo = jsonObj.getString("orderNo");

                        order_id.setText("Order No : "+orderNo);

                        Toast.makeText(BillPage.this, "Order Placed Successfully.", Toast.LENGTH_SHORT).show();
                        saveorder.setVisibility(View.GONE);
                        printsharelay.setVisibility(View.VISIBLE);
                    } else {
                       // ProductsGrid.setVisibility(View.GONE);
                        Toast.makeText(BillPage.this, "Something Went Wrong.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(BillPage.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.print(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("total_amount", SubTotalString);
                parameters.put("customer_id", customerid);
                parameters.put("payment_method", getIntent().getStringExtra("Payment"));
                parameters.put("deliver_address", CustomerAddress);
                parameters.put("user_id", UserID);
                parameters.put("customer_name", getIntent().getStringExtra("CustomerName"));
                parameters.put("wallet_amount", walletbalance);
                parameters.put("discount", dicount);
                parameters.put("discount_type", dicount_type);
                parameters.put("cash_amount", balanceamt);

                Log.d("","parameters"+parameters);
               return parameters;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }

    private void printIt(String thisData) {
        BluetoothSocket socket = null;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.microlan_small);
        byte[] imageIcon = Utils.decodeBitmap(bitmap);
        byte [] data = thisData.getBytes();
        byte [] finalData = new byte[imageIcon.length + data.length];
        ByteBuffer buff = ByteBuffer.wrap(finalData);
        buff.put(imageIcon);
        buff.put(data);

        byte [] combinedData = buff.array();

        //Get BluetoothAdapter
        BluetoothAdapter btAdapter = BluetoothUtil.getBTAdapter();
        if(btAdapter == null) {
            Toast.makeText(getBaseContext(), "Open Bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get sunmi InnerPrinter BluetoothDevice
        BluetoothDevice device = BluetoothUtil.getDevice(btAdapter);
        if(device == null) {
            Toast.makeText(getBaseContext(), "Make Sure Bluetooth have InnterPrinter", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            socket = BluetoothUtil.getSocket(device);
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(BillPage.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {
            BluetoothUtil.sendData(combinedData, socket);
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(BillPage.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
