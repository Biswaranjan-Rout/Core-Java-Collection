package com.fashion.amai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fashion.amai.R;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.MerchantProductsByMerchantID;

import retrofit2.Call;

public class ActivityResponse extends BaseActivity implements View.OnClickListener {
    private TextView tv_details_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        tv_details_link = findViewById(R.id.tv_details_link);

        tv_details_link.setOnClickListener(this);

        clearDesigns();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityResponse.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        getSupportActionBar().setTitle("Confirmation Page");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_details_link:{
                Intent intent = new Intent(ActivityResponse.this, ActivityMyAppointments.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }
    }

    private void clearDesigns() {

        retrofit2.Call<ClearSelectedDesignsPojo> call = merchantApiInterface.clearDesigns();
        call.enqueue(new retrofit2.Callback<ClearSelectedDesignsPojo>() {
            @Override
            public void onResponse(retrofit2.Call<ClearSelectedDesignsPojo> call, retrofit2.Response<ClearSelectedDesignsPojo> response) {
                try{
                    if(response.code()==200){

                        Log.d("*****","Here in block 2");

                            // progressDialog.dismiss();

                    }else if(response.code()==201){
                       // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                       // showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                      //  product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                  //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ClearSelectedDesignsPojo> call, Throwable t) {
                t.printStackTrace();
              //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ActivityResponse.this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
