package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.fashion.amai.adapter.IntroScreenPagerAdapter;
import com.fashion.amai.model.ModelProductDetail;
import com.fashion.amai.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class IntroScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;

    int [] introScreenArray  = new int []{R.drawable.page2, R.drawable.page3, R.drawable.page5, R.drawable.page6};
    private TabLayout tabLayout;
    private ArrayList<ModelProductDetail> listProduct= new ArrayList<>();
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listProduct.add(new ModelProductDetail("https://amaifilestorage123.s3.us-east-2.amazonaws.com/products/1.jpg",   "Affordable fashion  at your fingertips", "Show up yourself"));
        listProduct.add(new ModelProductDetail("https://amaifilestorage123.s3.us-east-2.amazonaws.com/products/2.jpg",   "Search from a plethora of Boutiques and Designers", "Get personalized products"));
        listProduct.add(new ModelProductDetail("https://amaifilestorage123.s3.us-east-2.amazonaws.com/products/3.jpg",   "Customize online at AMAI  with complete convenience", "Design your own"));
        listProduct.add(new ModelProductDetail("https://amaifilestorage123.s3.us-east-2.amazonaws.com/products/4.jpg",   "24x7 Online Fashion & Lifestyle Store for everyone", "Enjoy the look"));
        setContentView(R.layout.activity_intro_screen);
        viewPager = findViewById(R.id.view_pager_intro_pager);
        btnNext = findViewById(R.id.btn_next);
        tabLayout = findViewById(R.id.tabDots_details);
        viewPager.setAdapter(new IntroScreenPagerAdapter(getApplicationContext(), listProduct,  introScreenArray));
        tabLayout.setupWithViewPager(viewPager, true);
        btnNext.setOnClickListener(this);


    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
             case R.id.btn_next:
                 viewPager.setCurrentItem(getItem(+1), true);
                 if(viewPager.getCurrentItem()+1==listProduct.size()){
                     startActivity(new Intent(this, AuthActivity.class));
                   //  startActivity(new Intent(this, ActivityHome.class));
                 }
                 break;
        }
    }
}
