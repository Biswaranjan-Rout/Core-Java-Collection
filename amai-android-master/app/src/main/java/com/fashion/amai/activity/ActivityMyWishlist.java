package com.fashion.amai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.adapter.AdapterMyWishlistTabs;
import com.fashion.amai.R;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.WishlistPojo;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;

public class ActivityMyWishlist extends BaseActivity implements View.OnClickListener {
    private AdapterMyWishlistTabs adapterMyWishlistTabs;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wishlist);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        adapterMyWishlistTabs = new AdapterMyWishlistTabs(getSupportFragmentManager());
        viewPager.setAdapter(adapterMyWishlistTabs);
        tabLayout.setupWithViewPager(viewPager);

        toolbar = (Toolbar) findViewById(R.id.include_toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("My Wishlist");


    }

    @Override
    public void onClick(View v) {
    }


}
