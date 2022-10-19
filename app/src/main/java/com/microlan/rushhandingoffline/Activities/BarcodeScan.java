package com.microlan.rushhandingoffline.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.microlan.rushhandingoffline.Adapters.ProductsAdapter;
import com.microlan.rushhandingoffline.DB.AllProductDBHelper;
import com.microlan.rushhandingoffline.DB.ItemInCartDBHelper;
import com.microlan.rushhandingoffline.Helper.DatabaseHelper;
import com.microlan.rushhandingoffline.OfflineModel.AllProductModel;
import com.microlan.rushhandingoffline.OfflineModel.ItemInCardModel;
import com.microlan.rushhandingoffline.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.decrypt;
import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;

public class BarcodeScan extends AppCompatActivity /*implements BarcodeReader.BarcodeReaderListener*/  {
    TextView BarcodeText;
    ArrayList<String> ItemSrBarcode, ItemSrNumber, ItemName, ItemQuantity, Quan;
    int ItemSr = 1, ItemQuan = 0;
    ProductsAdapter productsAdapter;
    GridView ProductGrid;
    Button AddProductsButton;
    DatabaseHelper databaseHelper;
    Boolean AddData = false;
    ArrayList<String> ProductName, BarcodeNumber, ProductPrice;
    ImageView addbarcodename;
    ArrayList<String> ScanSize,ScanPrice,ScanStock;

    ArrayList<AllProductModel> arrayListAudios;
    AllProductDBHelper dbHelper;
    ItemInCartDBHelper dbAddItemHelpers;

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    String intentData = "",ScanData="";
    ArrayList<ItemInCardModel> arrayListitem;
    String Intro = "", UserID, session_id;
    SharedPreferences sharedPreferences;
    ImageView down,back,cart;
    RelativeLayout card;
    TextView countText;

    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
     */   setContentView(R.layout.activity_barcode_scan);
//        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        Intro = sharedPreferences.getString("Intro", encrypt("No"));
        UserID = sharedPreferences.getString("id", "");
        session_id = sharedPreferences.getString("session_id", "");
        Intro = decrypt(Intro);



        surfaceView=findViewById(R.id.surfaceView);
        btnAction=findViewById(R.id.btnAction);
        txtBarcodeValue=findViewById(R.id.txtBarcodeValue);
        dbHelper = new AllProductDBHelper(getApplicationContext());
        dbAddItemHelpers = new ItemInCartDBHelper(getApplicationContext());

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        ProductName = new ArrayList<String>();
        BarcodeNumber = new ArrayList<String>();
        ProductPrice = new ArrayList<String>();


        ScanSize = new ArrayList<String>();
        ScanPrice = new ArrayList<String>();
        ScanStock = new ArrayList<String>();


        card=findViewById(R.id.card);
        countText=findViewById(R.id.countText);
        back=findViewById(R.id.back);

       getaddcode();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ItemInCartOffline.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OfflineCounter.class);
                startActivity(intent);
                finish();
            }
        });



        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));

            }
        });


//my comment
        initialiseDetectorsAndSources();


    }



    private void addproducttocart() {
        ScanPrice.clear();
        ScanSize.clear();
        ScanStock.clear();

        arrayListAudios = dbHelper.getscanproduct(intentData);

        if (arrayListAudios.size() == 0) {
            Toast.makeText(getApplicationContext(), "Product not found", Toast.LENGTH_SHORT).show();

        } else {

            String Catogeryid = arrayListAudios.get(0).getCatogeryid();
            final String productIDString = arrayListAudios.get(0).getProduct_id();
            final String productNameString = arrayListAudios.get(0).getProduct_name();
            String price = arrayListAudios.get(0).getPrice();
            final String productImageString = arrayListAudios.get(0).getImage_name();
            String prices1 = arrayListAudios.get(0).getPrice1();
            String sizes = arrayListAudios.get(0).getPack_size1();
            final String unit = arrayListAudios.get(0).getUnit_name();
            String desc = arrayListAudios.get(0).getDescription();
            String term = arrayListAudios.get(0).getTerms_conditions();
            final String code = arrayListAudios.get(0).getHsn_code();
            String Stock = arrayListAudios.get(0).getStock_qty();

            Log.d("", "sizesss  " + sizes);
            Log.d("", "sizesss  " + Stock);
            Log.d("", "sizes" + prices1);

            final String scanprice, scansize;

            String[] res = Stock.split("[,]");

            for (String name : res) {
                ScanStock.add(name);
            }

            String[] pic = prices1.split("[,]");

            for (String name : pic) {

                ScanPrice.add(name);
            }

            String[] siz = sizes.split("[,]");

            for (String name : siz) {
                ScanSize.add(name);
            }


            if (ScanStock.get(1).equals("31531100")) {
                scanprice = ScanPrice.get(0);
                scansize = ScanSize.get(0);
            } else {
                scanprice = ScanPrice.get(1);
                scansize = ScanSize.get(1);

            }


            addtocart(productIDString, productNameString, "https://papasmart.online/products/" + productImageString, scanprice, 1, Float.valueOf(scanprice), scansize, "", "", unit, "", UserID, code);

        }

    }






  private void initialiseDetectorsAndSources() {

        try {
            Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
            barcodeDetector = new BarcodeDetector.Builder(BarcodeScan.this)
                    .setBarcodeFormats(Barcode.ALL_FORMATS)
                    .build();



            cameraSource = new CameraSource.Builder(this, barcodeDetector)
                    .setRequestedPreviewSize(1920, 1080)
                    .setAutoFocusEnabled(true) //you should add this feature
                    .build();

            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(BarcodeScan.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                            cameraSource.start(surfaceView.getHolder());
                        } else {
                            ActivityCompat.requestPermissions(BarcodeScan.this, new
                                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                    Log.d("","cameraSource.stop()");
                    cameraSource.stop();
                }
            });


            barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {
                    Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                    Log.d("","barcodes"+barcodes.size());
                    if (barcodes.size() != 0) {
                        txtBarcodeValue.post(new Runnable() {
                            @Override
                            public void run() {

                                intentData = barcodes.valueAt(0).displayValue;
                                Log.d("","intentData"+intentData);
                                if(intentData != "") {
                                    cameraSource.stop();

                                    toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (ActivityCompat.checkSelfPermission(BarcodeScan.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                                try {
                                                    cameraSource.start(surfaceView.getHolder());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                ActivityCompat.requestPermissions(BarcodeScan.this, new
                                                        String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                                            }
                                            // cameraSource.start(surfaceView.getHolder());

                                        }
                                    }, 5000);

                                    //  callApi(barcode_number);

                                    txtBarcodeValue.setText("Add in Cart");
                                    txtBarcodeValue.setTextColor(Color.parseColor("#008000"));



                                    addproducttocart();
                                }
                                else {

                                }

                            }


                            // }
                        });
                    }
                }
            });
        }catch (Exception ex){
            Toast.makeText(this, "qr error"+ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void addtocart(String productid, String productname, String image, String pacK_price, int qty, Float total, String PAcksize, String packsize, String price, String pacK_unit, String packunit, String userID, String productCode) {

        ItemInCardModel recordingItem = new ItemInCardModel(productid,productname,image,pacK_price,qty,total,PAcksize,"","",pacK_unit,"",userID,productCode);
        dbAddItemHelpers.addItemIncart(recordingItem);
        Log.d("","addItemIncart");
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Toast.makeText(getApplicationContext(),"Product Add to card",Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(2000);
        }

        getaddcode();
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

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }







    @Override
    public void onBackPressed() {

        Intent intent=new Intent(getApplicationContext(),OfflineCounter.class);
        startActivity(intent);
        finish();
    }

}
