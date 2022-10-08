package com.microlan.rushhandingoffline.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
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
import com.microlan.rushhandingoffline.Adapters.OrdersAdapter;
import com.microlan.rushhandingoffline.DB.OrderDataDBHelper;
import com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper;
import com.microlan.rushhandingoffline.DBAdapter.OrderHistoeryAdapter;
import com.microlan.rushhandingoffline.Dialogs.FileNameDialog;
import com.microlan.rushhandingoffline.Helper.CSVWriter;
import com.microlan.rushhandingoffline.Helper.DatabaseHelperBills;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Util.BluetoothUtil;
import com.microlan.rushhandingoffline.Util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Orders extends AppCompatActivity {

    ArrayList<String> UserName, OrderNumber, TotalAmount, Address, DateTime, DateList,WalletAmount,Discount,CashAmount,address_id,PaymentMode,Orderid;
    ArrayAdapter<String> DateAdapter;
    OrderHistoeryAdapter ordersAdapter;
    GridView OrdersGrid;
    DatabaseHelperBills databaseHelperBills;
    TextView DateSelectEdit;
    String [] ColumnName;
    FileNameDialog fileNameDialog;
    Button ExportExcelBtn;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String UserID;
    Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    ArrayList<JSONArray> OrderProducts;
    OrderDataDBHelper dborderdata;
    OrderDataDetailDBHelper dborderdetails;
    ArrayList<String> IGST,CGST,SGST,Balance;

    ArrayList<OrderDataModel> arrayListorderdata;

    String userNameString,orderNumberString,totalAmountString,wallet_amount,discount,cash_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        getSupportActionBar().setTitle("Orders");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        fileNameDialog = new FileNameDialog(Orders.this);
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);

        OrdersGrid = (GridView)findViewById(R.id.ordersGrid);
        DateSelectEdit = (TextView)findViewById(R.id.dateSelectEdit);
        ExportExcelBtn = (Button)findViewById(R.id.exportExcelBtn);
        dborderdata = new OrderDataDBHelper(getApplicationContext());
        databaseHelperBills = new DatabaseHelperBills(Orders.this);
        UserID = sharedPreferences.getString("id", "");

        myCalendar = Calendar.getInstance();

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
        IGST = new ArrayList<String>();
        CGST = new ArrayList<String>();
        SGST = new ArrayList<String>();
        Balance = new ArrayList<String>();


        OrderProducts = new ArrayList<JSONArray>();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                DateSelectEdit.setText(sdf.format(myCalendar.getTime()));
                OrderList();

            }
        };
        DateSelectEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Orders.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
               // OrderList();

            }
        });

       // OrderList();

        OrdersGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }

    public void OrderList() {
        Log.d("","DateSelectEdit"+DateSelectEdit.getText().toString());
        arrayListorderdata = dborderdata.getdateorder(DateSelectEdit.getText().toString());

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
        IGST.clear();
        CGST.clear();
        SGST.clear();
        Balance.clear();

        Log.d("","arrayListorderdata"+arrayListorderdata.size());

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
                String flag = arrayListorderdata.get(i).getFlag();
                String igst = arrayListorderdata.get(i).getIgst();
                String cgst = arrayListorderdata.get(i).getCgst();
                String sgst = arrayListorderdata.get(i).getSgst();
                String balance = arrayListorderdata.get(i).getBalance();

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
                IGST.add(igst);
                CGST.add(cgst);
                SGST.add(sgst);
                Balance.add(balance);


            }

            ordersAdapter = new OrderHistoeryAdapter(getApplicationContext(), UserName, OrderNumber, TotalAmount, Address, DateTime,WalletAmount,Discount,CashAmount,PaymentMode,Orderid,IGST,CGST,SGST,Balance);
            ordersAdapter.notifyDataSetChanged();
            OrdersGrid.setAdapter(ordersAdapter);

        }
    }

    public void ExportDB (String FileName)
    {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "Microlan POS Outputs");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        File file = new File(exportDir, FileName + ".csv");

        try{

            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            csvWrite.writeNext(ColumnName);

            for (int i = 0; i< DateTime.size(); i++){
                String arrStr [] = {DateTime.get(i), OrderNumber.get(i), UserName.get(i), Address.get(i), TotalAmount.get(i)};
                csvWrite.writeNext(arrStr);
            }

            csvWrite.close();
            Toast.makeText(Orders.this, "CSV File Successfully Created.", Toast.LENGTH_SHORT).show();

//            Intent listDocumentsInent = new Intent(TransactionsList.this, FilesList.class);
//            listDocumentsInent.putExtra("Path", Environment.getExternalStorageDirectory() + "Money Diary Excel Outputs");
//            startActivity(listDocumentsInent);

        } catch (Exception e){
            Toast.makeText(Orders.this, "Can not Create CSV File.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
