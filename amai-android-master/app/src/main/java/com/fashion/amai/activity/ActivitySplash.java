package com.fashion.amai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fashion.amai.R;
import com.fashion.amai.custom.ApiClient;
import com.fashion.amai.interfaces.AuthInterface;
import com.fashion.amai.model.Login;
import com.fashion.amai.model.ProfileDetail;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;

public class ActivitySplash extends BaseActivity {
    private int SPLASH_TIME_OUT = 1000;
    private ApiClient apiClient ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        signIn();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentActivityHome = new Intent(ActivitySplash.this, ActivityHome.class);
                startActivity(intentActivityHome);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void signIn() {

            HashMap<String, String> mapObject   = new HashMap<>();
            mapObject.put("user_name", "naman2");
            mapObject.put("password", "pass");
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(mapObject)).toString());

            retrofit2.Call<Login.Response> call = authApiInterface.
                    signInToServer("application/json", body);
            call.enqueue(new retrofit2.Callback<Login.Response>() {
                @Override
                public void onResponse(retrofit2.Call<Login.Response> call, retrofit2.Response<Login.Response> response) {
                   String token = response.body().getData().getToken();
                    showToast(token);

                    getProfileDetail(token);
                }

                @Override
                public void onFailure(retrofit2.Call<Login.Response> call, Throwable t) {
                    t.printStackTrace();


                }
            });

    }

    private void getProfileDetail(String token) {
        AuthInterface.User userInterface = ApiClient.getClient()
                .create(AuthInterface.User.class);
        retrofit2.Call<ProfileDetail.MyProfileDetail> call = userInterface.
                getProfile("application/json", "Bearer "+token);
        call.enqueue(new retrofit2.Callback<ProfileDetail.MyProfileDetail>() {
            @Override
            public void onResponse(retrofit2.Call<ProfileDetail.MyProfileDetail> call, retrofit2.Response<ProfileDetail.MyProfileDetail> response) {
                int id = response.body().getId();
                showToast(String.valueOf(id));
            }
            @Override
            public void onFailure(retrofit2.Call<ProfileDetail.MyProfileDetail> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
