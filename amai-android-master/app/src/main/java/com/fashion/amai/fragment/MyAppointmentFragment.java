package com.fashion.amai.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.adapter.AdapterAppointments;
import com.fashion.amai.model.BookAppointment;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.model.MyDesignsAppointmentPojo;
import com.fashion.amai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyAppointmentFragment extends BaseFragment {
    private RecyclerView recyclerAppointments;
    private LinearLayoutManager linearLayoutManager;
    private AdapterAppointments adapterMyDesignsTab1;
    private List<MyDataModal> myDataModalList;
    private List<BookAppointment.Data> appointmentData;
    private List<MyDesignsAppointmentPojo> myDesignsAppointmentData;
    private View appointment_progressbar;
    private String bookedStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_appointments, container, false);
    }

    public static MyAppointmentFragment newInstance(String status) {
        MyAppointmentFragment fragment = new MyAppointmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BOOKED_APPOINTMENT, status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            bookedStatus = getArguments().getString(Constants.BOOKED_APPOINTMENT);
        }

        myDataModalList = new ArrayList<>();
        recyclerAppointments = view.findViewById(R.id.recycler_designs_for_appointments);
        appointment_progressbar = view.findViewById(R.id.appointment_progressbar);
        linearLayoutManager = new LinearLayoutManager(getContext());


        appointment_progressbar.setVisibility(View.VISIBLE);
        getAllApointments();

    }

    private void getAllApointments() {

        retrofit2.Call<BookAppointment> call = merchantApiInterface.getAllAppointments(bookedStatus);
        call.enqueue(new retrofit2.Callback<BookAppointment>() {
            @Override
            public void onResponse(retrofit2.Call<BookAppointment> call, retrofit2.Response<BookAppointment> response) {
                try{
                    if(response.code()==200){

                        appointmentData = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(appointmentData.size()>0){

                            adapterMyDesignsTab1 = new AdapterAppointments(getContext(), appointmentData, preferences);
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



}
