package com.microlan.rushhandingoffline.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.microlan.rushhandingoffline.BaseURL.ApiClient;
import com.microlan.rushhandingoffline.BaseURL.ApiInterface;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.ForgototpResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.microlan.rushhandingoffline.Activities.SplashScreen.encrypt;


public class ForgotPassword extends AppCompatActivity {
    String reg_email;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    Button Forgot,forgotemail;
    TextView wait;
    LinearLayout Emaillayout,Passwordlayout;
    EditText Newpassword,Confirmpassword,Email,otp;
    String user_id;
    int otpresopnce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);

        reg_email = sharedPreferences.getString("userEmail", encrypt(""));
        Email=findViewById(R.id.email);
        Newpassword=findViewById(R.id.newpassword);
        Confirmpassword=findViewById(R.id.confirmpassword);



        Forgot=findViewById(R.id.forgotok);
        forgotemail=findViewById(R.id.forgotemail);
        Emaillayout=findViewById(R.id.layout);
        Passwordlayout=findViewById(R.id.passwordlayout);
        otp=findViewById(R.id.otp);


        forgotemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendotp(Email.getText().toString());

            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int opts= Integer.parseInt(otp.getText().toString());
                if(otp.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Otp", Toast.LENGTH_SHORT).show();
                }
                if(opts!=(otpresopnce))
                {
                    Log.d("otpresopnce","otp.getText().toString()"+otp.getText().toString());
                    Toast.makeText(getApplicationContext(),"Enter Otp is not Valide", Toast.LENGTH_SHORT).show();
                }
                else if(Newpassword.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter New Password", Toast.LENGTH_SHORT).show();

                }
                else if(Confirmpassword.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Confirm Password", Toast.LENGTH_SHORT).show();

                }
                else if(!Confirmpassword.getText().toString().equals(Newpassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"New Password and  Confirm Password is not Equal ", Toast.LENGTH_SHORT).show();

                }
                else {
                    //
                    Changepassword(user_id,Confirmpassword.getText().toString());
                }
            }
        });

    }

    private void Changepassword(String user_id, String password) {
        progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        Retrofit r = ApiClient.getClient();
        ApiInterface api = r.create(ApiInterface.class);
        Call<ResponseBody> signup_responseCall=api.newforgot(user_id,password);
        signup_responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Password Change Sucessfull", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Try Again ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendotp(String email) {
        progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        //progressDialog.setMax(100);
        progressDialog.show();

        Retrofit r = ApiClient.getClient();
        ApiInterface api = r.create(ApiInterface.class);

        Call<ForgototpResponse> signup_responseCall=api.forgot(email);
        signup_responseCall.enqueue(new Callback<ForgototpResponse>() {
            @Override
            public void onResponse(Call<ForgototpResponse> call, Response<ForgototpResponse> response) {
                progressDialog.cancel();
                try{
                    String status = response.body().getStatus();
                    Log.d("dfdfd","dfdfdf"+status);
                    if (status.equals("1")) {
                        otpresopnce = response.body().getOtpCode();
                        user_id = response.body().getUserId();
                        Log.d("dssdd", "asdsa" + otpresopnce);
                        Emaillayout.setVisibility(View.GONE);
                        Passwordlayout.setVisibility(View.VISIBLE);


                    }
                    else {

                        Toast.makeText(getApplicationContext(),"Enter Email Id is not Register with Us ", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ForgototpResponse> call, Throwable t) {
                progressDialog.cancel();

            }
        });
    }


}
