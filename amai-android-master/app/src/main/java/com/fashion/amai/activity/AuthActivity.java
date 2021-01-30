package com.fashion.amai.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fashion.amai.R;
import com.fashion.amai.custom.ApiClient;
import com.fashion.amai.interfaces.ForgotInterface;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.interfaces.ProductInterface;
import com.fashion.amai.model.ForgotPassPojo;
import com.fashion.amai.model.Login;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.CustomUtils;
import com.fashion.amai.utils.MySharedPreferences;
import com.fashion.amai.utils.PrefKeys;
import com.fashion.amai.model.Register;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout layoutRegister;
    private LinearLayout layoutLogin;
    private LinearLayout layoutForgot;
    private EditText editTextUserName, editTextEmail, editTextMobNo, editTextPassword, editTextEmailAndMob, editTextPasswordLogin,editTextUserIdForgot;
    private Button btnRegister, btnLogin , btnForgot;
    private TextView tvSignUp, tvLogin,tvForgot;
    private CustomUtils  utils  = new CustomUtils();
    private Boolean status;
    private String token;

    private View login_progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for full screen activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        // inti view
        intiView();

        // set Listener
        setListener();

        login_progress = findViewById(R.id.auth_progress);
        login_progress.setVisibility(View.GONE);
    }

    private void setListener() {
        tvSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvForgot.setOnClickListener(this);

//        btnForgot.setOnClickListener(this);

    }

    private void intiView() {
        layoutRegister  = (LinearLayout) findViewById(R.id.layout_register_screen);
        layoutLogin  = (LinearLayout) findViewById(R.id.layout_login_screen);
        layoutForgot = (LinearLayout) findViewById(R.id.layout_forgot_screen);


        // for register screen
       editTextUserName  = findViewById(R.id.editText_username_reg);
       editTextEmail  = findViewById(R.id.edit_email_id_reg);
       editTextMobNo  = findViewById(R.id.editText_mob_no_reg);
       editTextPassword  = findViewById(R.id.editText_pass_reg);
       btnRegister  = findViewById(R.id.btn_reg);
       tvLogin  = findViewById(R.id.tv_login);
       tvForgot = findViewById(R.id.forgot_password);


       // for login screen
        editTextEmailAndMob  = findViewById(R.id.editText_mob_no_login);
        editTextPasswordLogin = findViewById(R.id.editText_pass_login);
        btnLogin  = findViewById(R.id.btn_login);
        tvSignUp  = findViewById(R.id.tv_signUp);

        //for Forgot Screen
        editTextUserIdForgot = findViewById(R.id.editText_userId_forgot);
        btnForgot = findViewById(R.id.btn_forgot);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_signUp:
                layoutLogin.setVisibility(View.GONE);
                layoutRegister.setVisibility(View.VISIBLE);
                break;


            case R.id.tv_login:
                layoutLogin.setVisibility(View.VISIBLE);
                layoutRegister.setVisibility(View.GONE);
                break;


            case R.id.btn_login:

                if(utils.isNetworkConnected(getApplicationContext())){

                    login_progress.setVisibility(View.VISIBLE);
                    layoutForgot.setVisibility(View.GONE);
                    signIn(editTextEmailAndMob.getText().toString(), editTextPasswordLogin.getText().toString());
                }else {
                    Toast.makeText(AuthActivity.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                }

            case R.id.forgot_password:
                if(utils.isNetworkConnected(getApplicationContext())) {
                    layoutLogin.setVisibility(View.GONE);
                    layoutForgot.setVisibility(View.VISIBLE);
                    btnForgot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            forgotPass();
                        }
                    });

                }
                else {
                    Toast.makeText(AuthActivity.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                }



                break;


            case R.id.btn_reg:

                    if(utils.isNetworkConnected(getApplicationContext())){

                        login_progress.setVisibility(View.VISIBLE);
                        signUp();
                    }else {
                        Toast.makeText(AuthActivity.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();

                    }

                break;

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void signIn(String userName, String pass) {
        HashMap<String, String> mapObject   = new HashMap<>();
        mapObject.put(PrefKeys.USER_NAME, userName);
        mapObject.put(PrefKeys.PASSWORD,  pass);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(mapObject)).toString());

       // AuthInterface.Auth apiInterface  = ApiClient.getClient().create(AuthInterface.Auth.class);
        Call<Login.Response> call = authApiInterface.signInToServer(PrefKeys.CONTENT_TYPE_APP_JSON, body);
        call.enqueue(new Callback<Login.Response>() {
            @Override
            public void onResponse(Call<Login.Response> call, Response<Login.Response> response) {
                switch (response.code()) {
                    case 200:

                        ApiClient.makeRetrofitNull();
                        login_progress.setVisibility(View.GONE);
                        token = response.body().getData().getToken();
                        Integer userId = response.body().getData().getId();

                        MySharedPreferences.registerProfileImage(preferences, response.body().getData().getProfile_image());
                        MySharedPreferences.registerRefreshToken(preferences, response.body().getData().getRefresh_token());
                        MySharedPreferences.registerToken(preferences, token);
                        MySharedPreferences.registerUserId(preferences, String.valueOf(userId));
                        MySharedPreferences.registerUsername(preferences, response.body().getData().getUserName());

                        merchantApiInterface = ApiClient.getMerchantClient(MySharedPreferences.getToken(preferences)).create(MerchantInterface.Merchant.class);

                        productApiInterface = ApiClient.getProductClient(MySharedPreferences.getToken(preferences)).create(ProductInterface.Product.class);

                        Intent homeIntent = new Intent(AuthActivity.this, ActivityHome.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);
                        finish();

                        break;
                    case 201:

                        login_progress.setVisibility(View.GONE);
                        layoutForgot.setVisibility(View.GONE);

                      //  showDialog("Error in Login", getErrorResponse(response));


                       // String error = response.errorBody().toString();


                        break;
                    case 401:

                        login_progress.setVisibility(View.GONE);
                        layoutForgot.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);

                        break;
                    case 404:

                        login_progress.setVisibility(View.GONE);
                        layoutForgot.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);



                        break;
                    case 422:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);

                        break;
                    case 400:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);

                        break;

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Login.Response> call, Throwable t) {
                t.printStackTrace();
                login_progress.setVisibility(View.GONE);

            }
        });

    }

    private void forgotPass() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.USERS_API).
                        addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        ForgotInterface forgotInterface = retrofit.create(ForgotInterface.class);

        Call<ForgotPassPojo.ForgotResponse> forgotResponseCall = forgotInterface.getUserIdForgort(editTextUserIdForgot.getText().toString());
        forgotResponseCall.enqueue(new Callback<ForgotPassPojo.ForgotResponse>() {

            @Override
            public void onResponse(Call<ForgotPassPojo.ForgotResponse> forgotResponseCall, Response<ForgotPassPojo.ForgotResponse> response2) {


                if (response2.code() == 200) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AuthActivity.this);
                    alertDialogBuilder.setTitle("Done");
                    alertDialogBuilder.setMessage("A password reset link has been sent to your registered email!");
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            layoutForgot.setVisibility(View.GONE);
                            layoutLogin.setVisibility(View.VISIBLE);


                        }
                    });
                    alertDialogBuilder.show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AuthActivity.this);
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("User not found!");
                    alertDialog.setCancelable(true);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            layoutForgot.setVisibility(View.GONE);
                            layoutLogin.setVisibility(View.VISIBLE);


                        }
                    });
                    alertDialog.show();

                }
               /* switch (response2.code()) {
                    case 200:

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AuthActivity.this);
                        alertDialogBuilder.setTitle("Done");
                        alertDialogBuilder.setMessage("A password reset link has been sent to your registered email!");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                layoutForgot.setVisibility(View.GONE);
                                layoutLogin.setVisibility(View.VISIBLE);


                            }
                        });
                        alertDialogBuilder.show();

                    case 201: {

//                        String error = response2.errorBody().toString();
                    }
                    case 404: {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AuthActivity.this);
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("User not found!");
                        alertDialog.setCancelable(true);
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

//
                                *//*layoutForgot.setVisibility(View.GONE);
                                layoutLogin.setVisibility(View.VISIBLE);*//*


                            }
                        });
                        alertDialog.show();*/

            }

            @Override
            public void onFailure(Call<ForgotPassPojo.ForgotResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AuthActivity.this, "Error ", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void signUp() {

        JsonObject design = new JsonObject();

        try {
            design.addProperty("user_name", editTextUserName.getText().toString().trim());
            design.addProperty("password", editTextPassword.getText().toString().trim());
            design.addProperty("email", editTextEmail.getText().toString().trim());
            design.addProperty("contact_number", editTextMobNo.getText().toString().trim());
            design.addProperty("role", "user");

        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


     //   RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JsonObject(mapObject)).toString());
        retrofit2.Call<Register.Response> call = authApiInterface.signUpToServer(design);
        call.enqueue(new Callback<Register.Response>() {
            @Override
            public void onResponse(Call<Register.Response> call, Response<Register.Response> response) {
                switch (response.code()) {
                    case 200:

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AuthActivity.this);
                        alertDialogBuilder.setTitle("Login Successfull");
                        alertDialogBuilder.setMessage("Click Ok to login");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                MySharedPreferences.registerUsername(preferences, editTextUserName.getText().toString());
                                MySharedPreferences.registerPassword(preferences, editTextPassword.getText().toString());

                                // savePrefs(PrefKeys.USERNAME, editTextUserName.getText().toString());
                                //savePrefs(PrefKeys.PASSWORD , editTextPassword.getText().toString());

                                signIn(MySharedPreferences.getUsername(preferences),  MySharedPreferences.getPassword(preferences));

                            }
                        });
                        alertDialogBuilder.show();

                        login_progress.setVisibility(View.GONE);

                        break;
                    case 201:

                        login_progress.setVisibility(View.GONE);

                        String error = response.errorBody().toString();


                        break;
                    case 401:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);


                        break;
                    case 404:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);



                        break;
                    case 422:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);

                        break;
                    case 400:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);

                        break;
                    case 500:

                        login_progress.setVisibility(View.GONE);

                        getErrorResponse(response, AuthActivity.this);

                        break;

                }
            }
            @Override
            public void onFailure(retrofit2.Call<Register.Response> call, Throwable t) {
                t.printStackTrace();
                login_progress.setVisibility(View.GONE);



            }
        });

    }
}
