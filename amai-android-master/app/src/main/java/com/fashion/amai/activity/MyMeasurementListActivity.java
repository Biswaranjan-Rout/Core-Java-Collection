package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fashion.amai.R;
import com.fashion.amai.adapter.MyRecyclerViewAdapter;
import com.fashion.amai.model.MeasurementPojoResponse;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.MyMeasurementPojo;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.InfiniteScrollProvider;
import com.fashion.amai.utils.MySharedPreferences;
import com.fashion.amai.utils.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyMeasurementListActivity extends BaseActivity implements OnLoadMoreListener {

    View progressFrame;
    RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private View product_progressbar;
    private Button add_measurement;
    private List<MeasurementPojoResponse.Data> measurementPojoResponse;


    private static final int PAGE_START = 0;
    private static final int PER_PAGE = 10;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_measurement_list);

        getSupportActionBar().setTitle("My Measurements");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        add_measurement = findViewById(R.id.add_measurement);
        add_measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyMeasurementListActivity.this,MyMeasurementDetailsActivity.class);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.myMeasurement_list);
        product_progressbar = findViewById(R.id.my_measurement_progressbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        //   recyclerView.setHasFixedSize(true);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView,this);

        adapter = new MyRecyclerViewAdapter(this);

        recyclerView.setAdapter(adapter);

        //if null then set layout to add measurement

        if(adapter.getItemCount() == 0){



        }

        getMeasurement();

      //  progressFrame.setVisibility(View.VISIBLE);

    }

    private void getMeasurement() {

        retrofit2.Call<MeasurementPojoResponse> call = merchantApiInterface.getMeasurementList();
        call.enqueue(new retrofit2.Callback<MeasurementPojoResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MeasurementPojoResponse> call, retrofit2.Response<MeasurementPojoResponse> response) {
                product_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                       // listMerchantProductById = response.body().getData();
                        int size = response.body().getData().size();

                      //  MySharedPreferences.registerMeasurementId(preferences, String.valueOf(response.body().getData().get(0).getId()));

                        measurementPojoResponse = response.body().getData();


                        Log.d("*****","Here in block 2");
                        if(response.body().getData().size()>0){
                            add_measurement.setVisibility(View.GONE);
                            adapter.addAll(response.body().getData());
                            // gridView.setAdapter(adapter);
                            //totalItems = listMerchantProductById.size();
                            product_progressbar.setVisibility(View.GONE);
                            // setGridViewListender();

                        }else {
                            product_progressbar.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        }
                    }else if(response.code()==201){
                        product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementPojoResponse> call, Throwable t) {
                t.printStackTrace();
                product_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

   /* private void getMeasurement02() {

        retrofit2.Call<MeasurementPojoResponse> call = merchantApiInterface.getMeasurementList(
                String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MeasurementPojoResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MeasurementPojoResponse> call, retrofit2.Response<MeasurementPojoResponse> response) {
                product_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();

                        measurementPojoResponse = response.body().getData();


                        Log.d("*****","Here in block 2");
                        if(response.body().getData().size()>0){

                            adapter.addAll(response.body().getData());
                            // gridView.setAdapter(adapter);
                            //totalItems = listMerchantProductById.size();
                            product_progressbar.setVisibility(View.GONE);
                            // setGridViewListender();

                        }else {
                            product_progressbar.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        }
                    }else if(response.code()==201){
                        product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MeasurementPojoResponse> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    */

    @Override
    public void onLoadMore() {

        if (measurementPojoResponse.size() == 10){
            try {
                product_progressbar.setVisibility(View.VISIBLE);

                currentPage += 1;
               // getMeasurement02();

                //  loadNextPage();
                // isLoading = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

    }
}
