package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microlan.rushhandingoffline.Adapters.BillAdapter;
import com.microlan.rushhandingoffline.DB.OrderDataDetailDBHelper;
import com.microlan.rushhandingoffline.OfflineModel.OrderDataDetailsModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.Views.ExpandableHeightGridView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderProducts extends AppCompatActivity {

    ArrayList<String> ProductName, ProductQuantity, ProductSinglePrice, ProductPrice, ProductImage, SrNo, color, size, packsize, unit;
    ExpandableHeightGridView ProductsGrid;
    BillAdapter billAdapter;
    String TotalAmount = "", IGSTString = "", SGSTString = "", CGSTString = "", SubTotalString = "", UserID, orderNo;
    TextView comp_address, gst_id, customerAddressText, discount_amts, paymentmode, walletamt, FinalTotalText, CustomerNameText, RewardPointsText, order_id;
    String gst_no, company_logo, billing_address, CustomerAddress, Customername, RegistrationStat, walletbalance;
    private String sharePath = "no", dicount, dicount_type, Payment, balanceamt, customerid;
    SharedPreferences sharedPreferences;
    TextView OrderSubtotalText;
    ImageView StoreImage;
    LinearLayout walletlay;
    ArrayList<OrderDataDetailsModel> arrayListorderfetails;
    OrderDataDetailDBHelper dborderdetails;
    String orderid;
    TextView CgstText, SgstText, IgstText;
    LinearLayout GSTLayout, RewardPointsLayout, CGSTLayout, SGSTLayout, IGSTLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_products);
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);

        getSupportActionBar().setTitle("Bill Page");
//        company_logo = sharedPreferences.getString("company_logo","");
//        billing_address = sharedPreferences.getString("billing_address","");
//        gst_no = sharedPreferences.getString("gst_no","");

        order_id = (TextView) findViewById(R.id.order_id);
        comp_address = (TextView) findViewById(R.id.comp_address);
        discount_amts = (TextView) findViewById(R.id.discount_amts);
        walletamt = (TextView) findViewById(R.id.walletamt);
        gst_id = (TextView) findViewById(R.id.gst_id);
//        StoreImage = (ImageView) findViewById(R.id.storeImage);
//        OrderSubtotalText = (TextView) findViewById(R.id.orderSubTotalText);
        FinalTotalText = (TextView) findViewById(R.id.finalTotalText);
        CustomerNameText = (TextView) findViewById(R.id.customerNameText);
        walletlay = (LinearLayout) findViewById(R.id.walletlay);
        customerAddressText = findViewById(R.id.customerAddressText);
        paymentmode = findViewById(R.id.paymentmode);
        ProductsGrid = (ExpandableHeightGridView) findViewById(R.id.productsGrid);
//        CgstText = (TextView) findViewById(R.id.cgstText);
//        SgstText = (TextView) findViewById(R.id.sgstText);
////        IgstText = (TextView) findViewById(R.id.igstText);
//        GSTLayout = (LinearLayout) findViewById(R.id.gstLayout);
//        CGSTLayout = (LinearLayout) findViewById(R.id.cgstLayout);
//        SGSTLayout = (LinearLayout) findViewById(R.id.sgstLayout);
//        IGSTLayout = (LinearLayout) findViewById(R.id.igstLayout);

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        billing_address = sharedPreferences.getString("billing_address", "");
        gst_no = sharedPreferences.getString("gst_no", "");


        comp_address.setText(billing_address);
        gst_id.setText(gst_no);
        //   Picasso.get().load(company_logo).into(StoreImage);
        ProductName = new ArrayList<String>();
        ProductQuantity = new ArrayList<String>();
        ProductSinglePrice = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();
        ProductImage = new ArrayList<String>();
        SrNo = new ArrayList<String>();
        color = new ArrayList<String>();
        size = new ArrayList<String>();
        packsize = new ArrayList<String>();
        unit = new ArrayList<String>();


//        if(company_logo.isEmpty())
//        {
//            Picasso.get().load(R.drawable.microlan).placeholder(R.drawable.microlan).into(StoreImage);
//
//        }
//        else {
//            Picasso.get().load(company_logo).placeholder(R.drawable.microlan).into(StoreImage);
//
//        }


//        SubTotalString = getIntent().getStringExtra("SubTotal");
        Customername = getIntent().getStringExtra("Customername");
        orderNo = getIntent().getStringExtra("orderNo");
//        dicount = getIntent().getStringExtra("dicount");
        walletbalance = getIntent().getStringExtra("walletbalance");
        CustomerAddress = getIntent().getStringExtra("CustomerAddress");
        Payment = getIntent().getStringExtra("payment");
        balanceamt = getIntent().getStringExtra("TotalAmount");
//        orderid = getIntent().getStringExtra("orderid");


//       // customerid = getIntent().getStringExtra("customerid");
//
//
//        dicount_type = getIntent().getStringExtra("dicount_type");
//
//
        CustomerNameText.setText("Customer Name : " + Customername);
//        discount_amts.setText("" + dicount);
        walletamt.setText(walletbalance);
        order_id.setText("Order No :" + orderNo);
        customerAddressText.setText("Address : " + CustomerAddress);
        paymentmode.setText("" + Payment);
        FinalTotalText.setText(balanceamt);
//        OrderSubtotalText.setText(SubTotalString);
//        IGSTString = getIntent().getStringExtra("IGST");
//        SGSTString = getIntent().getStringExtra("SGST");
//        CGSTString = getIntent().getStringExtra("CGST");

     /*   CgstText.setText(CGSTString);
        SgstText.setText(SGSTString);
        IgstText.setText(IGSTString);
*/

//        if(CGSTString.equals("")||CGSTString.equals("0.0"))
//        {
//            CGSTLayout.setVisibility(View.GONE);
//        }
//        else {
//            CgstText.setText(CGSTString);
//
//        }
//        if(SGSTString.equals("")||SGSTString.equals("0.0"))
//        {
//            SGSTLayout.setVisibility(View.GONE);
//        }
//        else {
//            SgstText.setText(SGSTString);
//
//        }
//        if(IGSTString.equals("")||IGSTString.equals("0.0"))
//        {
//            IGSTLayout.setVisibility(View.GONE);
//        }
//        else {
//            IgstText.setText(IGSTString);
//
//        }
//
//        discount_amts.setText(""+dicount);

//        FinalTotalText.setText(balanceamt);
//        order_id.setText("Order No :"+orderNo);
//
//        if(CustomerAddress.equals(""))
//        {
//            customerAddressText.setVisibility(View.GONE);
//        }
//        else {
//            customerAddressText.setText("Address : "+CustomerAddress);
//
//        }
//        if(walletbalance.equals("0.00"))
//        {
//            walletlay.setVisibility(View.GONE);
//        }
//        else {
//            walletamt.setText(walletbalance);
//
//        }
        dborderdetails = new OrderDataDetailDBHelper(getApplicationContext());

        Log.d("", "orderNo" + orderNo);

        Log.d("", "arrayListorderfetails  " + arrayListorderfetails);

        getproduct();

       /* } catch (JSONException e){

        }
*/
    }

    private void getproduct() {
        arrayListorderfetails = dborderdetails.getorderdetails(orderNo);

        ProductName.clear();
        ProductQuantity.clear();
        ProductSinglePrice.clear();
        ProductPrice.clear();
        ProductImage.clear();
        SrNo.clear();
        color.clear();
        size.clear();
        unit.clear();


        //try {
        // JSONArray details = new JSONArray(getIntent().getStringExtra("Products"));
        for (int i = 0; i < arrayListorderfetails.size(); i++) {
            String productNameString = arrayListorderfetails.get(i).getProduct_name();
            String productSinglePriceString = arrayListorderfetails.get(i).getPrice();
            String productPriceString = arrayListorderfetails.get(i).getUnit_total();
            String productQuantityString = arrayListorderfetails.get(i).getQty();
            String productImageString = arrayListorderfetails.get(i).getImage_name();
            String productOrderString = arrayListorderfetails.get(i).getOrder_number();
            String colors = arrayListorderfetails.get(i).getProduct_color();
            String units = arrayListorderfetails.get(i).getPack_unit();
            String sizes = arrayListorderfetails.get(i).getPack_size();
            String packsizes = arrayListorderfetails.get(i).getProduct_size();
            String flag = arrayListorderfetails.get(i).getFlag();

            ProductName.add(productNameString + " , " + flag);
            ProductQuantity.add(productQuantityString);
            ProductSinglePrice.add(productSinglePriceString);
            ProductPrice.add(productPriceString);
            ProductImage.add(productImageString);

            color.add(colors);
            size.add(sizes);
            packsize.add(packsizes);
            unit.add(units);

            Log.d("ddddd", "dddddd" + productNameString);
            Log.d("ddddd", "productOrderString" + productOrderString);
            Log.d("ddddd", "dddddd" + ProductName);
            //BillImage.add("http://microlan.in/rashan/products/medium/medium_" + productImageString);
            for (int j = 0; j < ProductName.size(); j++) {
                SrNo.add(String.valueOf(i + 1));
            }

        }

        billAdapter = new BillAdapter(getApplicationContext(), SrNo, ProductName, ProductQuantity, ProductSinglePrice, ProductPrice, color, size, packsize, unit);
        ProductsGrid.setAdapter(billAdapter);
        ProductsGrid.setExpanded(true);

    }
}