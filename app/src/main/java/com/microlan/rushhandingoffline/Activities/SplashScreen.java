package com.microlan.rushhandingoffline.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;
import com.karan.churi.PermissionManager.PermissionManager;
import com.microlan.rushhandingoffline.Adapters.NotificationAdapter;
import com.microlan.rushhandingoffline.BaseURL.ApiClient;
import com.microlan.rushhandingoffline.BaseURL.ApiInterface;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.AnnouncementResponse;
import com.microlan.rushhandingoffline.model.CompanyLogoItem;
import com.microlan.rushhandingoffline.model.CompanySetting;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    SharedPreferences sharedPreferences;
    String RegistrationStat, permissionStat;
    PermissionManager permissionManager;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    ImageView logoimg;
    String company_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        permissionStat = sharedPreferences.getString("permissionStat", encrypt("NotGranted"));
        RegistrationStat = sharedPreferences.getString("Registered", ("No"));
        company_logo = sharedPreferences.getString("company_logo","");
        permissionStat = decrypt(permissionStat);
        RegistrationStat = decrypt(RegistrationStat);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        logoimg=findViewById(R.id.logoimg);


        Log.d("","RegistrationStat"+RegistrationStat);
       // if (!isOnline()) {

        if (Build.VERSION.SDK_INT >= 23){
            if (permissionStat.equals("Granted"))
            {

                if (RegistrationStat.equals("Yes")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                                                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                                                    startActivity(mainIntent);
                                                    finish();
                                                }
                                            }, SPLASH_DISPLAY_LENGTH);
                                        } else {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {


                                                    Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                                                    startActivity(mainIntent);
                                                    finish();
                                                }
                                            }, SPLASH_DISPLAY_LENGTH);
                                        }

                                    } else {
                                        permissionManager = new PermissionManager() {};
                                        permissionManager.checkAndRequestPermissions(SplashScreen.this);
                                    }
                                }


        else {
                                    if (RegistrationStat.equals("Yes")) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                                                startActivity(mainIntent);
                                                finish();
                                            }
                                        }, SPLASH_DISPLAY_LENGTH);
                                    } else {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                                                startActivity(mainIntent);
                                                finish();
                                            }
                                        }, SPLASH_DISPLAY_LENGTH);
                                    }
                                       }





    }

    public static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);

        ArrayList<String> granted = permissionManager.getStatus().get(0).granted;
        ArrayList<String> denied = permissionManager.getStatus().get(0).denied;

        for (String item:granted)
        {
            SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
            editor.putString("permissionStat", encrypt("Granted"));
            editor.putString("BaseURL" , "http://microlan.co.in/doc/api/");
            editor.apply();
            Intent intent = getIntent();
            finish();
            startActivity(intent);


            //goNextPage();
        }

        for (String item:denied)
        {
            SharedPreferences.Editor editor = getSharedPreferences("myPref", MODE_PRIVATE).edit();
            editor.putString("permissionStat", encrypt("NotGranted"));
            editor.apply();
        }
    }


}
