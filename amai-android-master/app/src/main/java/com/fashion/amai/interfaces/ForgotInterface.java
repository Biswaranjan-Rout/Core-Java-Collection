package com.fashion.amai.interfaces;
import com.fashion.amai.model.ForgotPassPojo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ForgotInterface {


        @GET("api/v1/users/{username}/reset-password")
        Call<ForgotPassPojo.ForgotResponse> getUserIdForgort(@Path("username") String username);


}
