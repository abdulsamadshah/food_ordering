package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.microlan.rushhandingoffline.Adapters.CategoryAdapter;
import com.microlan.rushhandingoffline.DB.AllCatogeryDBHelper;
import com.microlan.rushhandingoffline.DB.AllGstDBHelper;
import com.microlan.rushhandingoffline.DB.AllProductDBHelper;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.DBAdapter.CounterAdapter;
import com.microlan.rushhandingoffline.OfflineModel.AllCatogeryModel;
import com.microlan.rushhandingoffline.OfflineModel.AllGstModel;
import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;

import java.util.ArrayList;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class OfflineCounter extends AppCompatActivity {

    CounterAdapter productCounterAdapter;
    CategoryAdapter categoryAdapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    ArrayList<String> ProductID,Productcatogery, ProductName, ProductPrice, ProductImage, ProductQuan,ProductPrices,ProductSizes,ProductUnit,ProductDesc,ProductTerm,ProductCode;
    ArrayList<String> CategoryID;
    ArrayList<String> CategoryName;
    ArrayList<String> CategoryImage;
    ArrayList<String> ScanSize;
    ArrayList<String> ScanPrice;
    ArrayList<String> ScanStock,ByteImage;
    RecyclerView categoriesrecy;
    GridView ProductsGrid, CategoriesGrid;
    String Intro = "", UserID, session_id;
    SharedPreferences sharedPreferences;
    AllProductDBHelper dbHelper;
    AllCatogeryDBHelper dbCatogeryHelpers;
    ItemInCartDBHelper dbAddItemHelpers;
    AllGstDBHelper dbgstHelpers;
    ArrayList<AllProductModel> arrayListAudios;
    ArrayList<AllGstModel> arrayListgst;
    ArrayList<AllCatogeryModel> arrayListcatogery;
    RelativeLayout card;
    ArrayList<ItemInCardModel> arrayListitem;
    TextView countText;
    ImageView down,back,scanqr;
    LinearLayout catogery;
    EditText SearchProductsEdit;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_counter);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        session_id = sharedPreferences.getString("session_id", "");
        Intro = decrypt(Intro);
        dbHelper = new AllProductDBHelper(getApplicationContext());
        dbCatogeryHelpers = new AllCatogeryDBHelper(getApplicationContext());
        dbAddItemHelpers = new ItemInCartDBHelper(getApplicationContext());
        dbgstHelpers = new AllGstDBHelper(getApplicationContext());
        requestQueue = Volley.newRequestQueue(getApplicationContext());







        arrayListAudios = dbHelper.getallproduct();
        arrayListcatogery = dbCatogeryHelpers.getallcatogery();



        SearchProductsEdit = (EditText) findViewById(R.id.searchProductEdit);

        card=findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ItemInCartOffline.class);
                startActivity(intent);
                finish();
            }
        });


        down=findViewById(R.id.down);


        ProductID = new ArrayList<String>();
        ProductName = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();
        ProductImage = new ArrayList<String>();
        ProductQuan = new ArrayList<String>();
        ProductPrices = new ArrayList<String>();
        ProductSizes = new ArrayList<String>();
        ProductUnit = new ArrayList<String>();
        ProductDesc = new ArrayList<String>();
        ProductTerm = new ArrayList<String>();
        ProductCode = new ArrayList<String>();
        Productcatogery = new ArrayList<String>();

        CategoryID = new ArrayList<String>();
        CategoryName = new ArrayList<String>();
        CategoryImage = new ArrayList<String>();
        ScanSize = new ArrayList<String>();
        ScanPrice = new ArrayList<String>();
        ScanStock = new ArrayList<String>();
        ByteImage = new ArrayList<String>();


        categoriesrecy = (RecyclerView) findViewById(R.id.categoriesrecy);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(OfflineCounter.this);
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesrecy.setLayoutManager(MyLayoutManager);
        categoriesrecy.setHasFixedSize(true);
        ProductsGrid = (GridView) findViewById(R.id.productsGrid);
        countText = (TextView) findViewById(R.id.countText);
        catogery = (LinearLayout) findViewById(R.id.catogery);
        back=findViewById(R.id.back);
        scanqr=findViewById(R.id.scanqr);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),BarcodeScan.class);
                startActivity(intent);
/*
                Intent i = new Intent(OfflineCounter.this, QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);
*/
            }
        });

       getaddcode();



        SearchProductsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (SearchProductsEdit.getText().toString().isEmpty()) {

                } else if (SearchProductsEdit.getText().toString().length() < 3) {
                    getproduct();

                } else {
                    getsearchproduct(SearchProductsEdit.getText().toString());


                }
            }
        });


        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(catogery.getVisibility()==View.VISIBLE)
                {
                    catogery.setVisibility(View.GONE);


                }
                else {
                    catogery.setVisibility(View.VISIBLE);
                }
            }
        });

        getproduct();

        getAllCatogery();




    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void getsearchproduct(String name) {
        {


            arrayListAudios = dbHelper.getsearchproduct(name);

            if(arrayListAudios.size()==0)
            {
                Toast.makeText(getApplicationContext(),"No Product On this Category",Toast.LENGTH_SHORT).show();

            }
            else {

                ProductID.clear();
                ProductName.clear();
                ProductPrice.clear();
                ProductImage.clear();
                ProductQuan.clear();
                ProductPrices .clear();
                ProductSizes .clear();
                ProductUnit .clear();
                ProductDesc .clear();
                ProductTerm .clear();
                ProductCode .clear();
                Productcatogery .clear();

                for(int i = 0;i<arrayListAudios.size();i++) {
                    String Catogeryid = arrayListAudios.get(i).getCatogeryid();
                    String productIDString = arrayListAudios.get(i).getProduct_id();
                    String productNameString = arrayListAudios.get(i).getProduct_name();
                    String price = arrayListAudios.get(i).getPrice();
                    String productImageString = arrayListAudios.get(i).getImage_name();
                    String prices1 = arrayListAudios.get(i).getPrice1();
                    String sizes = arrayListAudios.get(i).getPack_size1();
                    String unit = arrayListAudios.get(i).getUnit_name();
                    String desc = arrayListAudios.get(i).getDescription();
                    String term = arrayListAudios.get(i).getTerms_conditions();
                    String code = arrayListAudios.get(i).getHsn_code();

                    ProductID.add(productIDString);
                    ProductName.add(productNameString);
                    ProductPrice.add("₹" + price);
                    ProductImage.add("http://microlanpos.com/demo/products/" + productImageString);
                    ProductQuan.add("1");
                    ProductPrices.add(prices1);
                    ProductSizes.add(sizes);
                    ProductUnit.add(unit);
                    ProductDesc.add(desc);
                    ProductTerm.add(term);
                    ProductCode.add(code);
                    Productcatogery.add(Catogeryid);




                    productCounterAdapter = new CounterAdapter(OfflineCounter.this, ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID,ProductPrices,ProductSizes,ProductUnit,ProductDesc,ProductTerm,ProductCode);
                    productCounterAdapter.notifyDataSetChanged();
                    ProductsGrid.setAdapter(productCounterAdapter);

                }


            }

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK)
        {
            //Log.d(LOGTAG,"COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(OfflineCounter.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d("","result"+result);

            ScanPrice.clear();
            ScanSize.clear();
            ScanStock.clear();

           // arrayListAudios = dbHelper.getscanproduct(result);
            arrayListAudios = dbHelper.getscanproduct("31531100");

            if(arrayListAudios.size()==0)
            {
                Toast.makeText(getApplicationContext(),"Product not found",Toast.LENGTH_SHORT).show();
            }
            else {
                String Catogeryid = arrayListAudios.get(0).getCatogeryid();
                String productIDString = arrayListAudios.get(0).getProduct_id();
                String productNameString = arrayListAudios.get(0).getProduct_name();
                String price = arrayListAudios.get(0).getPrice();
                String productImageString = arrayListAudios.get(0).getImage_name();
                String prices1 = arrayListAudios.get(0).getPrice1();
                String sizes = arrayListAudios.get(0).getPack_size1();
                String unit = arrayListAudios.get(0).getUnit_name();
                String desc = arrayListAudios.get(0).getDescription();
                String term = arrayListAudios.get(0).getTerms_conditions();
                String code = arrayListAudios.get(0).getHsn_code();
                String Stock = arrayListAudios.get(0).getStock_qty();

                Log.d("","sizesss  "+sizes);
                Log.d("","sizesss  "+Stock);
                Log.d("","sizes"+prices1);

                String scanprice,scansize;

                String[] res = Stock.split("[,]");

                for(String name : res){
                    ScanStock.add(name);
                }

                String[] pic = prices1.split("[,]");

                for(String name : pic){

                    ScanPrice.add(name);
                }

                 String[] siz = sizes.split("[,]");

                for(String name : siz){
                    ScanSize.add(name);
                }


                if(ScanStock.get(1).equals("31531100"))
                {
                    scanprice=ScanPrice.get(0);
                    scansize=ScanSize.get(0);
                }
                else {
                    scanprice=ScanPrice.get(1);
                    scansize=ScanSize.get(1);

                }


              addtocart(productIDString,productNameString,"http://microlanpos.com/demo/products/"+productImageString,scanprice,1, Float.valueOf(price),scansize,"","",unit,"",UserID,code);
/*
                dbAddItemHelpers.addItemIncart(recordingItem);
                Log.d("","addItemIncart");
                getaddcode();
*/


            }


            //  AddQRProducts(result,session_id,UserID);
        }
    }

    public void GetCatProducts(String id) {
        {



            arrayListAudios = dbHelper.getcatproduct(id);

            if(arrayListAudios.size()==0)
            {
                Toast.makeText(getApplicationContext(),"No Product On this Category",Toast.LENGTH_SHORT).show();

            }
            else {

                ProductID.clear();
                ProductName.clear();
                ProductPrice.clear();
                ProductImage.clear();
                ProductQuan.clear();
                ProductPrices .clear();
                ProductSizes .clear();
                ProductUnit .clear();
                ProductDesc .clear();
                ProductTerm .clear();
                ProductCode .clear();
                Productcatogery .clear();

                for(int i = 0;i<arrayListAudios.size();i++) {
                    String Catogeryid = arrayListAudios.get(i).getCatogeryid();
                    String productIDString = arrayListAudios.get(i).getProduct_id();
                    String productNameString = arrayListAudios.get(i).getProduct_name();
                    String price = arrayListAudios.get(i).getPrice();
                    String productImageString = arrayListAudios.get(i).getImage_name();
                    String prices1 = arrayListAudios.get(i).getPrice1();
                    String sizes = arrayListAudios.get(i).getPack_size1();
                    String unit = arrayListAudios.get(i).getUnit_name();
                    String desc = arrayListAudios.get(i).getDescription();
                    String term = arrayListAudios.get(i).getTerms_conditions();
                    String code = arrayListAudios.get(i).getHsn_code();

                    ProductID.add(productIDString);
                    ProductName.add(productNameString);
                    ProductPrice.add("₹" + price);
                    ProductImage.add("http://microlanpos.com/demo/products/" + productImageString);
                    ProductQuan.add("1");
                    ProductPrices.add(prices1);
                    ProductSizes.add(sizes);
                    ProductUnit.add(unit);
                    ProductDesc.add(desc);
                    ProductTerm.add(term);
                    ProductCode.add(code);
                    Productcatogery.add(Catogeryid);



                    Log.d("","ProductSizes "+ProductSizes);
                    Log.d("","ProductPrices "+ProductPrices);


                    productCounterAdapter = new CounterAdapter(OfflineCounter.this, ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID,ProductPrices,ProductSizes,ProductUnit,ProductDesc,ProductTerm,ProductCode);
                    productCounterAdapter.notifyDataSetChanged();
                    ProductsGrid.setAdapter(productCounterAdapter);

                }


            }

        }

    }

    private void getaddcode() {
        arrayListitem = dbAddItemHelpers.getaddedproduct(UserID);

        arrayListitem.size();
        Log.d("","arrayListitem"+arrayListitem.size());


        if(arrayListitem.size()==0)
        {
            countText.setVisibility(View.GONE);

        }
        else {
            countText.setVisibility(View.VISIBLE);
            countText.setText( ""+arrayListitem.size());

        }

    }


    public void addtocart(String productid, String productname, String image, String pacK_price, int qty, Float total, String PAcksize, String packsize, String price, String pacK_unit, String packunit, String userID, String productCode) {

        arrayListitem = dbAddItemHelpers.extesprodutcs(productid,PAcksize);

        Log.d("","arrayListitem"+arrayListitem.size());
        if(arrayListitem.size()==0)
        {
            ItemInCardModel recordingItem = new ItemInCardModel(productid,productname,image,pacK_price,qty,total,PAcksize,"","",pacK_unit,"",userID,productCode);
            dbAddItemHelpers.addItemIncart(recordingItem);
        }
        else {
            Float pricess= ((arrayListitem.get(0).getUnit_total()));
            int qtys= ((arrayListitem.get(0).getQty()));

            total=total+pricess;
            qty=qtys+1;
            Log.d("","total"+total);
            Log.d("","qty"+qty);

            ItemInCardModel recordingItem = new ItemInCardModel(productid,productname,image,pacK_price,qty,total,PAcksize,"","",pacK_unit,"",userID,productCode);
            dbAddItemHelpers.updatesameItemIncart(recordingItem);


        }

      /*  ItemInCardModel recordingItem = new ItemInCardModel(productid,productname,image,pacK_price,qty,total,PAcksize,"","",pacK_unit,"",userID,productCode);
        dbAddItemHelpers.addItemIncart(recordingItem);
        Log.d("","addItemIncart");

        Toast.makeText(getApplicationContext(),"Product Add to card",Toast.LENGTH_SHORT).show();
      */  getaddcode();
    }

    private void getAllCatogery() {
        arrayListcatogery = dbCatogeryHelpers.getallcatogery();

        CategoryID.clear();
        CategoryName.clear();
        CategoryImage.clear();

        Log.d("","arrayListcatogeryssss"+arrayListcatogery.size());

        for(int i = 0;i<arrayListcatogery.size();i++) {
            String categoryIDString = arrayListcatogery.get(i).getCatogerId();
            String categoryNameString = arrayListcatogery.get(i).getCatogeryname();
            String categoriesImageString = arrayListcatogery.get(i).getCatogeryimage();

            CategoryID.add(categoryIDString);
            CategoryName.add(categoryNameString);

            Log.d("","ImageString"+categoriesImageString);
            CategoryImage.add("http://microlanpos.com/demo/uploads/categoryimg/" + categoriesImageString);

            categoryAdapter = new CategoryAdapter(OfflineCounter.this, CategoryID, CategoryName, CategoryImage,UserID);
            categoriesrecy.setAdapter(categoryAdapter);


        }

    }


    private void getproduct()
    {

        arrayListAudios = dbHelper.getallproduct();

        ProductID.clear();
        ProductName.clear();
        ProductPrice.clear();
        ProductImage.clear();
        ProductQuan.clear();
        ProductPrices .clear();
        ProductSizes .clear();
        ProductUnit .clear();
        ProductDesc .clear();
        ProductTerm .clear();
        ProductCode .clear();
        Productcatogery .clear();

    for(int i = 0;i<arrayListAudios.size();i++) {
        String Catogeryid = arrayListAudios.get(i).getCatogeryid();
        String productIDString = arrayListAudios.get(i).getProduct_id();
        String productNameString = arrayListAudios.get(i).getProduct_name();
        String price = arrayListAudios.get(i).getPrice();
        String productImageString = arrayListAudios.get(i).getImage_name();
        String prices1 = arrayListAudios.get(i).getPrice1();
        String sizes = arrayListAudios.get(i).getPack_size1();
        String unit = arrayListAudios.get(i).getUnit_name();
        String desc = arrayListAudios.get(i).getDescription();
        String term = arrayListAudios.get(i).getTerms_conditions();
        String code = arrayListAudios.get(i).getHsn_code();

        ProductID.add(productIDString);
        ProductName.add(productNameString);
        ProductPrice.add("₹" + price);
        ProductImage.add("http://microlanpos.com/demo/products/" + productImageString);
        ProductQuan.add("1");
        ProductPrices.add(prices1);
        ProductSizes.add(sizes);
        ProductUnit.add(unit);
        ProductDesc.add(desc);
        ProductTerm.add(term);
        ProductCode.add(code);
        Productcatogery.add(Catogeryid);



        productCounterAdapter = new CounterAdapter(OfflineCounter.this, ProductID, ProductName, ProductPrice, ProductImage, ProductQuan, UserID,ProductPrices,ProductSizes,ProductUnit,ProductDesc,ProductTerm,ProductCode);
        productCounterAdapter.notifyDataSetChanged();
        ProductsGrid.setAdapter(productCounterAdapter);


    }

}









}