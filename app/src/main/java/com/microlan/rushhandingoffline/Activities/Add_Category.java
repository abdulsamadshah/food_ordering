package com.microlan.rushhandingoffline.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.microlan.rushhandingoffline.DB.AllCatogeryDBHelper;
import com.microlan.rushhandingoffline.DB.Utils;
import com.microlan.rushhandingoffline.OfflineModel.AllCatogeryModel;
import com.microlan.rushhandingoffline.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Add_Category extends AppCompatActivity {

    EditText catogeryname;
    ImageView image_load,image_loads;
    Button addcatogery,addcatogeryss;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "StoreImageActivity";
    private Uri selectedImageUri;
    AllCatogeryDBHelper dbCatogeryHelpers;
    String imagename;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category);
        getSupportActionBar().setTitle("Add Categroy");
        dbCatogeryHelpers = new AllCatogeryDBHelper(getApplicationContext());

        addcatogery=findViewById(R.id.addcatogery);
        image_load=findViewById(R.id.image_load);
        catogeryname=findViewById(R.id.catogeryname);
        image_loads=findViewById(R.id.image_loads);
        addcatogeryss=findViewById(R.id.addcatogeryss);

        image_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();

            }
        });
        addcatogery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageInDB();
            }
        });

        addcatogeryss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imgFile = new  File(path);
                Uri filePath = Uri.fromFile(imgFile);

                Log.d("","imgFile"+path);
                Log.d("","imgFile"+imgFile);
                Log.d("","filePath"+filePath);
                try {
                    Bitmap bipmap = MediaStore.Images.Media.getBitmap(Add_Category.this.getContentResolver(), Uri.fromFile(imgFile));

                    image_loads.setImageBitmap(bipmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(imgFile.exists()){

                    Log.d("","imgFiledd"+imgFile);

                }

            }
        });
    }

    void openImageChooser() {
        Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);

      /*  Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
   */ }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                selectedImageUri = data.getData();

                 path= String.valueOf(data.getData());

                 //imagename=getRealPathFromURI(selectedImageUri);
                imagename=path.substring(path.lastIndexOf("/")+1);

                Log.d("","imagename"+selectedImageUri);
                 Log.d("","imagename"+imagename);
                // Log.d("","filename"+filename);
               /* File f = new File(data.getData());
                String imageName = f.getName();
               */ if (null != selectedImageUri) {
                    image_load.setImageURI(selectedImageUri);

                  //  image_loads.setImageBitmap(bipmap);

                }
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    boolean saveImageInDB() {
/*AllCatogeryModel recordingItem = new AllCatogeryModel(categoryIDString,categoryNameString,categoriesImageString);

                                    dbCatogeryHelpers.addcatogery(recordingItem);
*/
        Log.d("","error ");
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = new byte[0];
            try {
                inputData = Utils.getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String ab= String.valueOf(selectedImageUri);
            Log.d("","iamne url "+ab);
            AllCatogeryModel recordingItem = new AllCatogeryModel(catogeryname.getText().toString(),path);

            dbCatogeryHelpers.addcatogery(recordingItem);

            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       /*  String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+catogeryname.getText().toString()+".jpeg";
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] bytesImage = byteArrayOutputStream.toByteArray();
*/
        return true;

    }


}

/*package store_image_in_sqlite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import tutorials.coderzheaven.com.androidtutorialprojects.R;

public class StoreImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "StoreImageActivity";

    private Button btnOpenGallery, btnSaveImage, btnLoadImage;
    private TextView tvStatus;
    private AppCompatImageView imgView, imgLoaded;
    private Uri selectedImageUri;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_image_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the views...
        btnOpenGallery = findViewById(R.id.btnSelectImage);
        btnSaveImage = findViewById(R.id.btnSaveImage);
        btnLoadImage = findViewById(R.id.btnLoadImage);
        imgLoaded = findViewById(R.id.loadedImg);
        imgView = findViewById(R.id.imgView);
        tvStatus = findViewById(R.id.tvStatus);

        btnOpenGallery.setOnClickListener(this);
        btnSaveImage.setOnClickListener(this);
        btnLoadImage.setOnClickListener(this);

        // Create the Database helper object
        dbHelper = new DBHelper(this);

    }

    void showMessage(final String message) {
        tvStatus.post(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText(message);
            }
        });
    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imgView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnOpenGallery)
            openImageChooser();

        if (v == btnSaveImage) {
            // Saving to Database...
            if (saveImageInDB()) {
                showMessage("Image Saved in Database...");
            }
        }

        if (v == btnLoadImage)
            loadImageFromDB();
    }

    boolean saveImageInDB() {

        try {
            dbHelper.open();
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            byte[] inputData = Utils.getBytes(iStream);
            dbHelper.insertImage(inputData);
            dbHelper.close();
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            dbHelper.close();
            return false;
        }

    }

    void loadImageFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.open();
                    final byte[] bytes = dbHelper.retreiveImageFromDB();
                    dbHelper.close();
                    // Show Image from DB in ImageView
                    imgLoaded.post(new Runnable() {
                        @Override
                        public void run() {
                            imgLoaded.setImageBitmap(Utils.getImage(bytes));
                            showMessage("Image Loaded from Database");
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
                    dbHelper.close();
                }
            }
        }).start();
    }
}*/
