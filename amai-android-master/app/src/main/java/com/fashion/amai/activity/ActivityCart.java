package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fashion.amai.R;
import com.fashion.amai.adapter.AddToCartAdapter;
import com.fashion.amai.adapter.MyRecyclerViewAdapter;
import com.fashion.amai.model.MyMeasurementPojo;
import com.fashion.amai.utils.InfiniteScrollProvider;
import com.fashion.amai.utils.OnLoadMoreListener;
import com.fashion.amai.utils.SimpleDividerItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityCart extends BaseActivity implements OnLoadMoreListener {
    View progressFrame;
    RecyclerView recyclerView;
    private AddToCartAdapter adapter;
    private AppCompatCheckBox main_check_box;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("My Cart");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        main_check_box = findViewById(R.id.main_check_box);


        recyclerView = findViewById(R.id.myMeasurement_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
          recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
           recyclerView.setHasFixedSize(true);
        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView,this);

        adapter = new AddToCartAdapter(this);

        recyclerView.setAdapter(adapter);

        MyMeasurementPojo myMeasurementPojo = new MyMeasurementPojo("10", "20");
        MyMeasurementPojo myMeasurementPojo02 = new MyMeasurementPojo("10", "20");
        MyMeasurementPojo myMeasurementPojo03 = new MyMeasurementPojo("10", "20");
        MyMeasurementPojo myMeasurementPojo04 = new MyMeasurementPojo("10", "20");
        MyMeasurementPojo myMeasurementPojo05 = new MyMeasurementPojo("10", "20");
        MyMeasurementPojo myMeasurementPojo06 = new MyMeasurementPojo("10", "20");
        List<MyMeasurementPojo> mData = new ArrayList<>();
        mData.add(myMeasurementPojo);
        mData.add(myMeasurementPojo02);
        mData.add(myMeasurementPojo03);
        mData.add(myMeasurementPojo04);
        mData.add(myMeasurementPojo05);
        mData.add(myMeasurementPojo06);
        adapter.addAll(mData);
    }

    private void populateRecyclerView() {


    }

    @Override
    public void onLoadMore() {

    }

}
