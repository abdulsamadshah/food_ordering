package com.microlan.rushhandingoffline.BaseURL;

import com.microlan.rushhandingoffline.model.AnnouncementResponse;
import com.microlan.rushhandingoffline.model.CompanySetting;
import com.microlan.rushhandingoffline.model.ForgototpResponse;
import com.microlan.rushhandingoffline.model.UserAddressResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("getCustomerAddress")
    Call<UserAddressResponse> getaddress(@Query("user_id") String user_id);

    @GET("notification")
    Call<AnnouncementResponse> getnoti();


    @POST("submit_forgot_password")
    Call<ResponseBody> newforgot(@Query("user_id") String user_id, @Query("password") String password);

    @POST("send_otp_forgot_password")
    Call<ForgototpResponse> forgot(@Query("email_address") String email_address);

    @POST("getCompanyLogo")
    Call<CompanySetting> companysetting();

}


