package com.fashion.amai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fashion.amai.R;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.utils.Constants;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class ActivityAppointmentsCancelled extends BaseActivity implements View.OnClickListener {
    private TextView tv_details_link;
    private int status;
    private String appointmentId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_cancelled);

        Intent intent = getIntent();
        appointmentId = intent.getStringExtra(Constants.APPOINTMENT_ID);
        status = intent.getIntExtra(Constants.STATUS, 0);

        tv_details_link = findViewById(R.id.tv_details_link);

        tv_details_link.setOnClickListener(this);

        clearDesigns();

        JsonObject design = new JsonObject();
       // schedule_progressBar.setVisibility(View.VISIBLE);

        updateAppointmentWithStatus(design);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAppointmentsCancelled.this, ActivityAppointmentsOld.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        getSupportActionBar().setTitle("Appointment Cancellation");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_details_link:{
                Intent intent = new Intent(ActivityAppointmentsCancelled.this, ActivityAppointmentsOld.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    private void updateAppointmentWithStatus(JsonObject design) {
        try {
            design.addProperty("apn_id", appointmentId);
            //  design.addProperty("booked_date", String.valueOf(bookingDate /* + " " + bookingTime */));
            //  design.addProperty("slot", slotTime);
            design.addProperty("status", status);

        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sendBookingUpdate(design);
    }

    private void sendBookingUpdate(JsonObject design) {
        retrofit2.Call<ClearSelectedDesignsPojo> call = merchantApiInterface.updateAppointmentTime( design);
        call.enqueue(new retrofit2.Callback<ClearSelectedDesignsPojo>() {
            @Override
            public void onResponse(retrofit2.Call<ClearSelectedDesignsPojo> call, retrofit2.Response<ClearSelectedDesignsPojo> response) {
                //  Log.d("Design Error", retrofit2.Response.error(400))
                try{
                    if(response.code()==200){

                       // schedule_progressBar.setVisibility(View.GONE);

                        //   startActivity(new Intent(ActivitySelectAddress.this, ActivitySchedule.class));

                    }else if(response.code()==201){

                      //  schedule_progressBar.setVisibility(View.GONE);


                        //  SelectDesignPojo.DesignData selectDesignPojo = response.body().getData();
                        Log.d("*****","Here in block 2");

                        //   activity_details_progressBar.setVisibility(View.GONE);

                    }else if(response.code()==401){
                     //   schedule_progressBar.setVisibility(View.GONE);

                        //   showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //    activity_details_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                  //  schedule_progressBar.setVisibility(View.GONE);

                    // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //  activity_details_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ClearSelectedDesignsPojo> call, Throwable t) {
                t.printStackTrace();
             //   schedule_progressBar.setVisibility(View.GONE);

                // activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
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

        Intent intent = new Intent(ActivityAppointmentsCancelled.this, ActivityAppointmentsOld.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
