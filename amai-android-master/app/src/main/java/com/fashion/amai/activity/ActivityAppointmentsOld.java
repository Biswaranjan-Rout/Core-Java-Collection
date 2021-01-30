package com.fashion.amai.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fashion.amai.R;
import com.fashion.amai.adapter.AdapterAppointments;
import com.fashion.amai.model.BookAppointment;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ActivityAppointmentsOld extends BaseActivity {
    private RecyclerView recyclerAppointments;
    private LinearLayoutManager linearLayoutManager;
    private AdapterAppointments adapterMyDesignsTab1;
    private List<MyDataModal> myDataModalList;
    private List<BookAppointment.Data> appointmentData;
    private View appointment_progressbar;
    private String appointmentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

    //    toolbar.setNavigationIcon(R.drawable.icon_menu);
        try {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityHome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }
            });
            getSupportActionBar().setTitle("Appointments");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            myDataModalList = new ArrayList<>();
            recyclerAppointments = findViewById(R.id.recycler_designs_for_appointments);
            appointment_progressbar = findViewById(R.id.appointment_progressbar);
            linearLayoutManager = new LinearLayoutManager(ActivityAppointmentsOld.this);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.BOOKED_APPOINTMENT,appointmentStatus );
            appointment_progressbar.setVisibility(View.VISIBLE);
            getAllApointments();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAllApointments() {

        retrofit2.Call<BookAppointment> call = merchantApiInterface.getAllAppointments(appointmentStatus);
        call.enqueue(new retrofit2.Callback<BookAppointment>() {
            @Override
            public void onResponse(retrofit2.Call<BookAppointment> call, retrofit2.Response<BookAppointment> response) {
                try{
                    if(response.code()==200){

                        appointmentData = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(appointmentData.size()>0){

                            adapterMyDesignsTab1 = new AdapterAppointments(ActivityAppointmentsOld.this, appointmentData, preferences);
                            recyclerAppointments.setLayoutManager(linearLayoutManager);
                            recyclerAppointments.setAdapter(adapterMyDesignsTab1);

                       //     adapterMyDesignsTab1.addAll(response.body().getData(), merchantApiInterface, ProductActivity.this);
                            // gridView.setAdapter(adapter);
                        //    totalItems = listMerchantProductById.size();
                            appointment_progressbar.setVisibility(View.GONE);
                            // setGridViewListender();

                        }else {
                            appointment_progressbar.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        }
                    }else if(response.code()==201){
                        appointment_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        appointment_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    appointment_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BookAppointment> call, Throwable t) {
                t.printStackTrace();
                appointment_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

    }


}
