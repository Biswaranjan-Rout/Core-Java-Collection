package com.fashion.amai.interfaces;

import com.fashion.amai.model.AppointmentData;
import com.fashion.amai.model.Login;
import com.fashion.amai.model.ProfileDetail;
import com.fashion.amai.model.Register;
import com.fashion.amai.utils.ApiEndpoints;
import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AuthInterface {

    public interface Auth{
        // retrofit call for signUp the server
      //  @POST("api/v1/register")
      //  Call<Register.Response> signUpToServer(@Header( "Content-Type") String contentType,@Body RequestBody body);


        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.REGISTER)
        Call<Register.Response> signUpToServer(
                @Body JsonObject object);

        // retrofit call for signIn the server
        @POST("api/v1/login")
        Call<Login.Response> signInToServer(@Header( "Content-Type") String contentType,
                                   @Body RequestBody body);
    }

    public  interface User{

        // retrofit call for signIn the server
        @GET("api/v1/me")
        Call<ProfileDetail.MyProfileDetail> getProfile(@Header ("Content-Type") String contentType,
                                                       @Header("Authorization") String  auth);
    }
}
