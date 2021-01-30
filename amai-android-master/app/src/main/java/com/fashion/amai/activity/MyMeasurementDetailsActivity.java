package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fashion.amai.R;
import com.fashion.amai.model.ActivitySchedulePojo;
import com.fashion.amai.model.MeasurementPojo;
import com.fashion.amai.model.MeasurementPojoResponse;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class MyMeasurementDetailsActivity extends BaseActivity {
    private EditText bust_round, waist_round, high_hip_round, hip_round, nape_to_waist,
            armhale_depth,shoulder_length, should_paint_to_paint, bicept_round, elbow_round, wrist_round, height;
    private LinearLayout confirm_measurements;
    private View schedule_progressBar;
    private int measurement_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_measurement);

        getSupportActionBar().setTitle("My Measurements");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        Intent intent  = getIntent();
        measurement_id =intent.getIntExtra(Constants.MEASUREMENT_ID,0);

        schedule_progressBar =  findViewById(R.id.my_measure_progressbar);
        confirm_measurements = (LinearLayout) findViewById(R.id.confirm_measurements);
        bust_round = findViewById(R.id.bust_round);
        waist_round = findViewById(R.id.waist_round);
        high_hip_round = findViewById(R.id.high_hip_round);
        hip_round = findViewById(R.id.hip_round);
        nape_to_waist = findViewById(R.id.nape_to_waist);
        armhale_depth = findViewById(R.id.armhale_depth);
        shoulder_length = findViewById(R.id.shoulder_length);
        should_paint_to_paint = findViewById(R.id.should_paint_to_paint);
        bicept_round = findViewById(R.id.bicept_round);
        elbow_round = findViewById(R.id.elbow_round);
        wrist_round = findViewById(R.id.wrist_round);
        height = findViewById(R.id.height);

        schedule_progressBar.setVisibility(View.VISIBLE);

        if (measurement_id!=0) {
            getMeasurement();
        } else {
            getMeasurementList();
        }

        confirm_measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                schedule_progressBar.setVisibility(View.VISIBLE);

                JsonObject design = new JsonObject();

                try {
                    design.addProperty("bust_round", Double.parseDouble(sendMeasure(bust_round.getText().toString().trim())) );
                    design.addProperty("waist_round",  Double.parseDouble(sendMeasure(waist_round.getText().toString().trim())));
                    design.addProperty("high_hip_round",  Double.parseDouble(sendMeasure(high_hip_round.getText().toString().trim())));
                    design.addProperty("hip_round",  Double.parseDouble(sendMeasure(hip_round.getText().toString().trim())));
                    design.addProperty("nape_to_waist",  Double.parseDouble(sendMeasure(nape_to_waist.getText().toString().trim())));
                    design.addProperty("armhale_depth",  Double.parseDouble(sendMeasure(armhale_depth.getText().toString().trim())));
                    design.addProperty("shoulder_length",  Double.parseDouble(sendMeasure(shoulder_length.getText().toString().trim())));
                    design.addProperty("shoulder_pain_to_paint",  Double.parseDouble(sendMeasure(should_paint_to_paint.getText().toString().trim())));
                    design.addProperty("bicep_round",  Double.parseDouble(sendMeasure(bicept_round.getText().toString().trim())));
                    design.addProperty("elbow_round",  Double.parseDouble(sendMeasure(elbow_round.getText().toString().trim())));
                    design.addProperty("wrist_round",  Double.parseDouble(sendMeasure(wrist_round.getText().toString().trim())));
                    design.addProperty("height",  Double.parseDouble(sendMeasure(height.getText().toString().trim())));


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sendBookingSlot(design);

            }
        });

    }

    private String sendMeasure(String value){
        if (value==null){
            return "0";
        } else {
            return String.valueOf(value);
        }
    }

    private void getMeasurementList() {

        retrofit2.Call<MeasurementPojoResponse> call = merchantApiInterface.getMeasurementList();
        call.enqueue(new retrofit2.Callback<MeasurementPojoResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MeasurementPojoResponse> call, retrofit2.Response<MeasurementPojoResponse> response) {
                schedule_progressBar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();
                        int size = response.body().getData().size();

                     //   MySharedPreferences.registerMeasurementId(preferences, String.valueOf(response.body().getData().get(0).getId()));

                    //    schedule_progressBar = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(response.body().getData().size()>0){

                            allEditextValuesFromList(response.body());

                            //adapter.addAll(response.body().getData());
                            // gridView.setAdapter(adapter);
                            //totalItems = listMerchantProductById.size();
                            schedule_progressBar.setVisibility(View.GONE);
                            // setGridViewListender();

                        }else {
                            schedule_progressBar.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        }
                    }else if(response.code()==201){
                        schedule_progressBar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        schedule_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    schedule_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementPojoResponse> call, Throwable t) {
                t.printStackTrace();
                schedule_progressBar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }


    private void getMeasurement() {
        retrofit2.Call<MeasurementPojo> call = merchantApiInterface.getMeasurementSingle(measurement_id);
        call.enqueue(new retrofit2.Callback<MeasurementPojo>() {
            @Override
            public void onResponse(retrofit2.Call<MeasurementPojo> call, retrofit2.Response<MeasurementPojo> response) {
                //  Log.d("Design Error", retrofit2.Response.error(400))
                schedule_progressBar.setVisibility(View.VISIBLE);

                try{
                    if(response.code()==200){

                        schedule_progressBar.setVisibility(View.GONE);
                        allEditextValues(response);


                    }else if(response.code()==201){

                        schedule_progressBar.setVisibility(View.GONE);


                        //  SelectDesignPojo.DesignData selectDesignPojo = response.body().getData();
                        Log.d("*****","Here in block 2");

                        //   activity_details_progressBar.setVisibility(View.GONE);

                    }else if(response.code()==401){
                        schedule_progressBar.setVisibility(View.GONE);

                        //   showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //    activity_details_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    schedule_progressBar.setVisibility(View.GONE);

                    // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //  activity_details_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementPojo> call, Throwable t) {
                t.printStackTrace();
                schedule_progressBar.setVisibility(View.GONE);

                // activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void allEditextValues(Response<MeasurementPojo> response) {

        bust_round.setText(networkValue(response.body().getData().getBustRound()));
        waist_round.setText(networkValue(response.body().getData().getWaistRound()));
        high_hip_round.setText(networkValue(response.body().getData().getHighHipRound()));
        hip_round.setText(networkValue(response.body().getData().getHipRound()));
        nape_to_waist.setText(networkValue(response.body().getData().getNapeToWaist()));
        armhale_depth.setText(networkValue(response.body().getData().getArmhaleDepth()));
        shoulder_length.setText(networkValue(response.body().getData().getShoulderLength()));
        should_paint_to_paint.setText(networkValue(response.body().getData().getShoulderPainToPaint()));
        bicept_round.setText(networkValue(response.body().getData().getBicepRound()));
        elbow_round.setText(networkValue(response.body().getData().getElbowround()));
        wrist_round.setText(networkValue(response.body().getData().getWristRound()));
        height.setText(networkValue(response.body().getData().getHeight()));
    }

    private void allEditextValuesFromList(MeasurementPojoResponse response) {

        bust_round.setText(networkValue(response.getData().get(0).getBustRound()));
        waist_round.setText(networkValue(response.getData().get(0).getWaistRound()));
        high_hip_round.setText(networkValue(response.getData().get(0).getHighHipRound()));
        hip_round.setText(networkValue(response.getData().get(0).getHipRound()));
        nape_to_waist.setText(networkValue(response.getData().get(0).getNapeToWaist()));
        armhale_depth.setText(networkValue(response.getData().get(0).getArmhaleDepth()));
        shoulder_length.setText(networkValue(response.getData().get(0).getShoulderLength()));
        should_paint_to_paint.setText(networkValue(response.getData().get(0).getShoulderPainToPaint()));
        bicept_round.setText(networkValue(response.getData().get(0).getBicepRound()));
        elbow_round.setText(networkValue(response.getData().get(0).getElbowround()));
        wrist_round.setText(networkValue(response.getData().get(0).getWristRound()));
        height.setText(networkValue(response.getData().get(0).getHeight()));
    }


    private String networkValue(String value){
        if (value==null){
            return " ";
        } else {
            return String.valueOf(value);
        }
    }

    private void sendBookingSlot(JsonObject design) {
        retrofit2.Call<MeasurementPojo> call = merchantApiInterface.addMeasurement( design);
        call.enqueue(new retrofit2.Callback<MeasurementPojo>() {
            @Override
            public void onResponse(retrofit2.Call<MeasurementPojo> call, retrofit2.Response<MeasurementPojo> response) {
                //  Log.d("Design Error", retrofit2.Response.error(400))
                try{
                    if(response.code()==200){

                        schedule_progressBar.setVisibility(View.GONE);

                        //   startActivity(new Intent(ActivitySelectAddress.this, ActivitySchedule.class));

                        Log.d("*****","Here in block 2");


                    }else if(response.code()==201){

                        schedule_progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(MyMeasurementDetailsActivity.this, MyMeasurementListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        MyMeasurementDetailsActivity.this.startActivity(intent);

                        finish();

                        //  SelectDesignPojo.DesignData selectDesignPojo = response.body().getData();
                        Log.d("*****","Here in block 2");

                        //   activity_details_progressBar.setVisibility(View.GONE);

                    }else if(response.code()==401){
                        schedule_progressBar.setVisibility(View.GONE);

                        //   showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //    activity_details_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    schedule_progressBar.setVisibility(View.GONE);

                    // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //  activity_details_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementPojo> call, Throwable t) {
                t.printStackTrace();
                schedule_progressBar.setVisibility(View.GONE);

                // activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }
}
