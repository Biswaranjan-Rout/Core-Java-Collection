package com.fashion.amai.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fashion.amai.R;
import com.fashion.amai.adapter.AdapterMyAppointmentTabs;
import com.fashion.amai.model.CheckedOutPojo;
import com.fashion.amai.model.MyDesignsAppointmentPojo;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ActivityMyAppointments extends BaseActivity implements View.OnClickListener {
    private AdapterMyAppointmentTabs adapterMyAppointmentTabs;
    private List<MyDesignsAppointmentPojo> myDataModalList;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        tabLayout = findViewById(R.id.tabs_my_appoint);
        viewPager = findViewById(R.id.view_pager_my_appoint);

        adapterMyAppointmentTabs = new AdapterMyAppointmentTabs(getSupportFragmentManager());
        viewPager.setAdapter(adapterMyAppointmentTabs);
        tabLayout.setupWithViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.include_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               /* finish();*/
                Intent intent = new Intent(v.getContext(), ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("My Appointments");
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, ActivityHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

    }
}
