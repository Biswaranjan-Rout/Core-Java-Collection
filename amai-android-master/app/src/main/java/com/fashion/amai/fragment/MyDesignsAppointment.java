package com.fashion.amai.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.activity.ProductActivity;
import com.fashion.amai.adapter.AdapterMyDesignsTab1;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;
import com.fashion.amai.model.MyDesignsModel;
import com.fashion.amai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyDesignsAppointment extends BaseFragment {
    private RecyclerView recyclerAppointments;
    private LinearLayoutManager linearLayoutManager;
    private AdapterMyDesignsTab1 adapterMyDesignsTab1;
    private List<MyDataModal> myDataModalList;
    private List<MyDesignsModel.Data> dataList;
    private View design_progressbar;
    private  String bookingStatus,rescheduleStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_1_my_designes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDataModalList = new ArrayList<>();
        recyclerAppointments = view.findViewById(R.id.recycler_designs_for_appointments);
        design_progressbar = view.findViewById(R.id.design_progressbar);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        bookingStatus= Constants.BOOKED_APPOINTMENT;

        design_progressbar.setVisibility(View.VISIBLE);
        getMyDesigns();

    }

    private void getMyDesigns() {

        retrofit2.Call<MyDesignsModel> call = merchantApiInterface.getMyDesigns(bookingStatus );
        call.enqueue(new retrofit2.Callback<MyDesignsModel>() {
            @Override
            public void onResponse(retrofit2.Call<MyDesignsModel> call, retrofit2.Response<MyDesignsModel> response) {
                try{
                    if(response.code()==200){
                        dataList = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(dataList.size()>0){

                            adapterMyDesignsTab1 = new AdapterMyDesignsTab1(getActivity(), dataList, merchantApiInterface);
                            recyclerAppointments.setLayoutManager(linearLayoutManager);
                            recyclerAppointments.setAdapter(adapterMyDesignsTab1);

                            design_progressbar.setVisibility(View.GONE);

                        }else {
                            design_progressbar.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        }
                    }else if(response.code()==201){
                        design_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        design_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    design_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MyDesignsModel> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                design_progressbar.setVisibility(View.GONE);
                Log.d("*****","Here in block 6");
            }

        });
    }
}
