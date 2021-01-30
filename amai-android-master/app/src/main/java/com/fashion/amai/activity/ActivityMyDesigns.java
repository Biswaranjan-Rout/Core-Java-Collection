package com.fashion.amai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.adapter.AdapterMyDesignsTab2;
import com.fashion.amai.adapter.AdapterMyDesignsTabs;
import com.fashion.amai.R;
import com.fashion.amai.adapter.PersonalizeSectionsPagerAdapter;
import com.fashion.amai.fragment.MyDesignsAppointment;
import com.fashion.amai.fragment.MyDesignsCheckout;
import com.fashion.amai.model.CheckedOutPojo;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.MyDesignsModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;

public class ActivityMyDesigns extends BaseActivity {
    private AdapterMyDesignsTabs adapterMyDesignsTabs;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_designs);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        getSupportActionBar().setTitle("My Designs");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapterMyDesignsTabs = new AdapterMyDesignsTabs(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapterMyDesignsTabs);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

    }
}
