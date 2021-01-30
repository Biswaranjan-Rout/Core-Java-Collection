package com.fashion.amai.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fashion.amai.DateTime.Pico;
import com.fashion.amai.DateTime.codec.Type;
import com.fashion.amai.DateTime.helper.PicoListener;
import com.fashion.amai.adapter.AdapterDate;
import com.fashion.amai.adapter.AdapterTime;
import com.fashion.amai.model.ActivitySchedulePojo;
import com.fashion.amai.model.AppointmentData;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.DateModel;
import com.fashion.amai.model.TimeModel;
import com.fashion.amai.R;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.MySharedPreferences;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class ActivitySchedule extends BaseActivity implements View.OnClickListener {
    private GridLayoutManager gridLayoutManagerDate, gridLayoutManagerTime;
    private AdapterDate adapterDate;
    private AdapterTime adapterTime;
  //  private ImageView ivBack;
    private TextView booking_Date_is;
    private Button btnBookNow, booking_date_dialog, btn_book_now;

    private View schedule_progressBar;

    private String bookingTime;
    private String bookingDate;
    private String bookingAddress;
    private String appointmentId;
    AlertDialog.Builder alertDialogBuilder;
    
    private String slotTime;

    TextView slot_01, slot_02, slot_03, slot_04;
    boolean slot_01_boolean = false, slot_02_boolean = false, slot_03_boolean = false, slot_04_boolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

     //   toolbar.setNavigationIcon(R.drawable.icon_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(ActivitySchedule.this, ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

               */
              finish();

            }
        });
        getSupportActionBar().setTitle("Book Appointments");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        bookingAddress = intent.getStringExtra(Constants.SEND_ADDRESS);
        appointmentId = intent.getStringExtra(Constants.APPOINTMENT_ID);

        slot_01 = findViewById(R.id.slot_01);
        slot_02 = findViewById(R.id.slot_02);
        slot_03 = findViewById(R.id.slot_03);
        slot_04 = findViewById(R.id.slot_04);
        btnBookNow = findViewById(R.id.btn_book_now);
        schedule_progressBar = findViewById(R.id.schedule_progressBar);
        booking_Date_is = findViewById(R.id.booking_Date_is);
        booking_date_dialog = findViewById(R.id.booking_date_dialog);
        btnBookNow.setOnClickListener(this);

        slot_01.setOnClickListener(this);
        slot_02.setOnClickListener(this);
        slot_03.setOnClickListener(this);
        slot_04.setOnClickListener(this);
        booking_date_dialog.setOnClickListener(this);


        schedule_progressBar.setVisibility(View.GONE);

        gridLayoutManagerDate = new GridLayoutManager(ActivitySchedule.this,3);
        gridLayoutManagerTime = new GridLayoutManager(ActivitySchedule.this,3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_book_now:{

                if (bookingAddress!=null){
                    if (bookingDate != null){

                        JsonObject design = new JsonObject();
                        schedule_progressBar.setVisibility(View.VISIBLE);

                        getSlotValue();

                        if (slotTime==null){

                            Toast.makeText(ActivitySchedule.this, "Slot Time not selected", Toast.LENGTH_SHORT).show();
                        } else {

                            bookAppointment(design);
                        }

                    }

                } else if (bookingDate != null){

                        JsonObject design = new JsonObject();
                        schedule_progressBar.setVisibility(View.VISIBLE);

                    getSlotValue();


                    updateAppointment(design);


                }



              //  startActivity(new Intent(ActivitySchedule.this, ActivityResponse.class));
                break;
            }

            case R.id.booking_date_dialog:{

                Pico pico = new Pico(ActivitySchedule.this, null, Type.CALENDAR);
                pico.setPicoListener(new PicoListener() {
                    @Override
                    public void result(Calendar calendar) {

                        Date current  = new Date();

                        Date selectedDate = calendar.getTime();

                        if (selectedDate.before(current)){
                            SameDateDialog02();

                        } else {

                            String date = Pico.formatDate(calendar);


                            String humanDate = Pico.humanDate(calendar);

                            //String set_time = Pico.formatTime(calendar);

                            booking_Date_is.setText(humanDate);
                            bookingDate = date;

                            booking_Date_is.setVisibility(View.VISIBLE);
                        }



                    }
                });
                pico.show();
                break;

               // startActivity(new Intent(ActivitySchedule.this, ActivityResponse.class));
            }
            case R.id.slot_01:{

                if (slot_01_boolean){
                    slot_01_boolean = false;
                    slot_01.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_01.setBackground(getResources().getDrawable(R.drawable.rectangle));

                } else {

                    clearOtherSlots();
                    slot_01_boolean = true;
                    slot_01.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_01.setBackground(getResources().getDrawable(R.drawable.rectangle_color));
                }
                break;
            }

            case R.id.slot_02:{

                if (slot_02_boolean){

                    slot_02_boolean = false;
                    slot_02.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_02.setBackground(getResources().getDrawable(R.drawable.rectangle));


                } else {

                    clearOtherSlots();
                    slot_02_boolean = true;

                    slot_02.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_02.setBackground(getResources().getDrawable(R.drawable.rectangle_color));
                }
                break;
            }

            case R.id.slot_03:{

                if (slot_03_boolean){

                    slot_03_boolean = false;
                    slot_03.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_03.setBackground(getResources().getDrawable(R.drawable.rectangle));


                } else {

                    clearOtherSlots();
                    slot_03_boolean = true;
                    slot_03.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_03.setBackground(getResources().getDrawable(R.drawable.rectangle_color));
                }
                break;
            }
            case R.id.slot_04:{

                if (slot_04_boolean){

                    slot_04_boolean = false;
                    slot_04.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_04.setBackground(getResources().getDrawable(R.drawable.rectangle));


                } else {

                    clearOtherSlots();
                    slot_04_boolean = true;
                    slot_04.setTextColor(getResources().getColor(R.color.colorBlack));
                    slot_04.setBackground(getResources().getDrawable(R.drawable.rectangle_color));
                }
                break;
            }
        }
    }

    private void SameDateDialog02(){
        alertDialogBuilder = new AlertDialog.Builder(ActivitySchedule.this);
        alertDialogBuilder.setTitle("Old Date Selected");
        alertDialogBuilder.setMessage("Choose Different Date for Appointment");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                booking_Date_is.setText(" ");
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }



    private void updateAppointment(JsonObject design) {
        try {
            design.addProperty("apn_id", appointmentId);
            design.addProperty("booked_date", String.valueOf(bookingDate /* + " " + bookingTime */));
            design.addProperty("slot", slotTime);


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
                schedule_progressBar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){

                        schedule_progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(ActivitySchedule.this, ActivityResponse.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

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
            public void onFailure(Call<ClearSelectedDesignsPojo> call, Throwable t) {
                t.printStackTrace();
                   schedule_progressBar.setVisibility(View.GONE);

                // activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void bookAppointment(JsonObject design) {
        try {
            design.addProperty("address", String.valueOf(bookingAddress));
            design.addProperty("booked_date", String.valueOf(bookingDate));
            design.addProperty("slot", slotTime);

        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sendBookingSlot(design);
    }

    private void getSlotValue() {
        if (slot_01_boolean){
            slotTime = "9am-11am";
        } else if (slot_02_boolean){
            slotTime = "11am-1pm";
        } else if (slot_03_boolean){
            slotTime = "1pm-3pm";
        } else if ((slot_04_boolean)){
            slotTime = "3pm-5pm";

        }
    }

    private void clearOtherSlots(){
        if (slot_02_boolean){
            slot_02_boolean = false;
            slot_02.setTextColor(getResources().getColor(R.color.colorBlack));
            slot_02.setBackground(getResources().getDrawable(R.drawable.rectangle));
        }

        if (slot_01_boolean){
            slot_01_boolean = false;
            slot_01.setTextColor(getResources().getColor(R.color.colorBlack));
            slot_01.setBackground(getResources().getDrawable(R.drawable.rectangle));
        }

        if (slot_03_boolean){
            slot_03_boolean = false;
            slot_03.setTextColor(getResources().getColor(R.color.colorBlack));
            slot_03.setBackground(getResources().getDrawable(R.drawable.rectangle));
        }

        if (slot_04_boolean){
            slot_04_boolean = false;
            slot_04.setTextColor(getResources().getColor(R.color.colorBlack));
            slot_04.setBackground(getResources().getDrawable(R.drawable.rectangle));
        }
    }



    private void sendBookingSlot(JsonObject design) {
        retrofit2.Call<ActivitySchedulePojo> call = merchantApiInterface.addAppointmentTime( design);
        call.enqueue(new retrofit2.Callback<ActivitySchedulePojo>() {
            @Override
            public void onResponse(retrofit2.Call<ActivitySchedulePojo> call, retrofit2.Response<ActivitySchedulePojo> response) {
                //  Log.d("Design Error", retrofit2.Response.error(400))
                try{
                    if(response.code()==200){

                        schedule_progressBar.setVisibility(View.GONE);


                        //   startActivity(new Intent(ActivitySelectAddress.this, ActivitySchedule.class));

                        Intent intent = new Intent(ActivitySchedule.this, ActivityResponse.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        Log.d("*****","Here in block 2");


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
            public void onFailure(Call<ActivitySchedulePojo> call, Throwable t) {
                t.printStackTrace();
                schedule_progressBar.setVisibility(View.GONE);

                // activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }
}
