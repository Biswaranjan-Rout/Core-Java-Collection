package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fashion.amai.R;
import com.fashion.amai.adapter.AddToCartAdapter02;
import com.fashion.amai.model.AddToCartProduct;
import com.fashion.amai.model.CartPojo;
import com.fashion.amai.model.Register;
import com.fashion.amai.utils.AddToCartDBHelper;
import com.fashion.amai.utils.Checksum;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCartSample extends BaseActivity implements PaytmPaymentTransactionCallback {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    // private AddToCartDBHelper dbHelper;
    private AddToCartAdapter02 adapter;
    private String filter = "";
    private View cart_progressbar;
    private TextView checkout_button;
    private TextView cart_total_before_discount, total_cart_discount, cart_order_total,total_cart_checkout_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_sample);

        getSupportActionBar().setTitle("My bag");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize the variables
        checkout_button = (TextView) findViewById(R.id.checkout_button);
        cart_total_before_discount = (TextView) findViewById(R.id.cart_total_before_discount);
        total_cart_discount = (TextView) findViewById(R.id.total_cart_discount);
        cart_order_total = (TextView) findViewById(R.id.cart_order_total);
        total_cart_checkout_price = (TextView) findViewById(R.id.total_cart_checkout_price);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        cart_progressbar = findViewById(R.id.cart_progressbar);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        cart_progressbar.setVisibility(View.VISIBLE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        getAddtoCartProducts();

        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPaymentHash();
            }
        });
    }

    private void populaterecyclerView(CartPojo cartPojo){
      //  dbHelper = new AddToCartDBHelper(this, preferences);
        adapter = new AddToCartAdapter02(cartPojo.getData().getCartItems(), this, mRecyclerView, preferences);
        mRecyclerView.setAdapter(adapter);

        cart_total_before_discount.setText(String.valueOf(cartPojo.getData().getTotal_actualPrice()));
        total_cart_discount.setText(String.valueOf(cartPojo.getData().getTotal_discountPrice()));
        cart_order_total.setText(String.valueOf(cartPojo.getData().getTotal_netPrice()));
        total_cart_checkout_price.setText(String.valueOf(cartPojo.getData().getTotal_netPrice()));
    }

    /*
    {
	"product_id": 59,
	"product_quantity": 3,
	"order_detail":"New order",
	"order_type":1,
	"product_size":"s"
}
     */

    private void getPaymentHash() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("product_id", 59);
        jsonObject.addProperty("product_quantity", 3);
        jsonObject.addProperty("order_detail", "New order");
        jsonObject.addProperty("order_type", 1);
        jsonObject.addProperty("product_size", "s");

        //   RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JsonObject(mapObject)).toString());
        retrofit2.Call<Checksum> call = paymentInterface.getHash(jsonObject);
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {
                switch (response.code()) {
                    case 200:

                        gotoPaytmPage(response.body());

                       // populaterecyclerView(response.body());

                        cart_progressbar.setVisibility(View.GONE);

                        break;
                    case 201:

                        cart_progressbar.setVisibility(View.GONE);

                        String error = response.errorBody().toString();


                        break;
                    case 401:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);


                        break;
                    case 404:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);



                        break;
                    case 422:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);

                        break;
                    case 400:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);

                        break;
                    case 500:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);

                        break;

                }
            }
            @Override
            public void onFailure(retrofit2.Call<Checksum> call, Throwable t) {
                t.printStackTrace();
                cart_progressbar.setVisibility(View.GONE);
            }
        });

    }

    private void gotoPaytmPage(Checksum checksum){

            PaytmPGService Service = PaytmPGService.getStagingService("https://securegw-stage.paytm.in/theia/processTransaction");

            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("MID", checksum.getData().getChecksumGenData().getMID()); //MID provided by paytm
            paramMap.put("ORDER_ID", checksum.getData().getChecksumGenData().getORDER_ID());
            paramMap.put("CUST_ID", checksum.getData().getChecksumGenData().getCUST_ID());
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", "100");
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL" ,"https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
            paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            paramMap.put( "MOBILE_NO" , "7777777777");  // no need
            paramMap.put("CHECKSUMHASH" , checksum.getData().getChecksum());
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);

            Service.initialize(Order,null);
            Service.startPaymentTransaction(ActivityCartSample.this, true, true,
                    ActivityCartSample.this  );

    }


    private void getAddtoCartProducts() {

        //   RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JsonObject(mapObject)).toString());
        retrofit2.Call<CartPojo> call = merchantApiInterface.get_add_to_cart();
        call.enqueue(new Callback<CartPojo>() {
            @Override
            public void onResponse(Call<CartPojo> call, Response<CartPojo> response) {
                switch (response.code()) {
                    case 200:


                        populaterecyclerView(response.body());

                        cart_progressbar.setVisibility(View.GONE);

                        break;
                    case 201:

                        cart_progressbar.setVisibility(View.GONE);

                        String error = response.errorBody().toString();


                        break;
                    case 401:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);


                        break;
                    case 404:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);



                        break;
                    case 422:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);

                        break;
                    case 400:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);

                        break;
                    case 500:

                        cart_progressbar.setVisibility(View.GONE);

                        getErrorResponse(response, ActivityCartSample.this);

                        break;

                }
            }
            @Override
            public void onFailure(retrofit2.Call<CartPojo> call, Throwable t) {
                t.printStackTrace();
                cart_progressbar.setVisibility(View.GONE);



            }
        });

    }


    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

    }



    @Override
    protected void onResume() {
        super.onResume();
       // adapter.notifyDataSetChanged();
    }
}
