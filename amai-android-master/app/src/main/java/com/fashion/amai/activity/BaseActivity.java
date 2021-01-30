package com.fashion.amai.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fashion.amai.R;
import com.fashion.amai.custom.ApiClient;
import com.fashion.amai.interfaces.AuthInterface;
import com.fashion.amai.interfaces.ForgotInterface;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.interfaces.MyDesignAppointmentInterface;
import com.fashion.amai.interfaces.PaymentInterface;
import com.fashion.amai.interfaces.ProductInterface;
import com.fashion.amai.model.ErrorResponse;
import com.fashion.amai.model.ForgotPassPojo;
import com.fashion.amai.model.RefreshTokenPojo;
import com.fashion.amai.model.Register;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.MenuConverter;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.paytm.pgsdk.PaytmPGService;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity {

    protected SharedPreferences preferences;

    protected Toolbar toolbar;

    protected int cart_count;

    protected ForgotInterface forgotInterface;
    protected MyDesignAppointmentInterface myDesignAppointmentInterface;
    protected AuthInterface.Auth authApiInterface;
    protected MerchantInterface.Merchant merchantApiInterface;
    protected ProductInterface.Product productApiInterface;
    protected PaymentInterface paymentInterface;
    boolean isSuccess = false;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        preferences = getSharedPreferences(Constants.CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

        authApiInterface  = ApiClient.getClient().create(AuthInterface.Auth.class);
        forgotInterface =  ApiClient.getClient().create(ForgotInterface.class);
        //myDesignAppointmentInterface = ApiClient.getClient().create(MyDesignAppointmentInterface.class);

        if (MySharedPreferences.getToken(preferences)!=null){
            paymentInterface  = ApiClient.getPaymentClient(MySharedPreferences.getToken(preferences)).create(PaymentInterface.class);
            merchantApiInterface  = ApiClient.getMerchantClient(MySharedPreferences.getToken(preferences)).create(MerchantInterface.Merchant.class);
            productApiInterface  = ApiClient.getProductClient(MySharedPreferences.getToken(preferences)).create(ProductInterface.Product.class);
            myDesignAppointmentInterface = ApiClient.getMerchantClient(MySharedPreferences.getToken(preferences)).create(MyDesignAppointmentInterface.class);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        // invalidateOptionsMenu();

        super.onPause();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(layoutResId);

        toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        invalidateOptionsMenu();

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu_personalise, menu);
      //  getMenuInflater().inflate(R.menu.top_menu_personalise, menu);
        MenuItem menuItem = menu.findItem(R.id.shopping_nav);

        if (MySharedPreferences.getCart(preferences)==null){
            cart_count = 0;
        } else {
            cart_count = Integer.parseInt(MySharedPreferences.getCart(preferences));
        }
        menuItem.setIcon(MenuConverter.convertLayoutToImage(getBaseContext(), cart_count,R.drawable.ic_action_shopping_bag));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notify_nav) {

            Toast.makeText(getBaseContext(), "Notify", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.like_nav) {

            startActivity(new Intent(this, ActivityMyWishlist.class));

            // Do something
            return true;
        }
        if (id == R.id.shopping_nav) {

          //  startActivity(new Intent(this, ActivityCart.class));
            startActivity(new Intent(this, ActivityCartSample.class));


            //  Toast.makeText(ActivityHome.this, "Shopping", Toast.LENGTH_SHORT).show();

            // Do something
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void getErrorResponse(Response<?> response, Context context) {
        String errorMessage = null;
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ErrorResponse>() {}.getType();
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);

            errorMessage = errorResponse.getMessage();

            showErrorDialog("Error", errorMessage, context);


        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected void showErrorDialog(String title, String description, Context context){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(description);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();

        //showToast(String.valueOf(response.code()));


    }

    protected void getRefreshToken(){


        JsonObject design = new JsonObject();

        try {

            design.addProperty("user_name", MySharedPreferences.getUsername(preferences));
            design.addProperty("refresh_token", MySharedPreferences.getRefreshToken(preferences));

        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        retrofit2.Call<RefreshTokenPojo> call = merchantApiInterface.getRefreshToken(design);
        call.enqueue(new retrofit2.Callback<RefreshTokenPojo>() {
            @Override
            public void onResponse(retrofit2.Call<RefreshTokenPojo> call, retrofit2.Response<RefreshTokenPojo> response) {
                //home_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){

                        MySharedPreferences.registerToken(preferences, response.body().getToken());
                        ApiClient.makeRetrofitNull();
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //home_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();


                }
            }

            @Override
            public void onFailure(Call<RefreshTokenPojo> call, Throwable t) {
                t.printStackTrace();
              //  home_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }



        });

    }
}
