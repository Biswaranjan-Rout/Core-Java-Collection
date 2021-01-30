package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.fashion.amai.R;
import com.fashion.amai.adapter.AdapterHome;
import com.fashion.amai.adapter.AdapterHomeOurDesign;
import com.fashion.amai.adapter.AdapterMenuTopHome;
import com.fashion.amai.adapter.AdapterOurDesign;
import com.fashion.amai.adapter.AdapterYourDesignTopMenu;
import com.fashion.amai.model.ModelMenuTopHome;
import com.fashion.amai.model.Movie;
import com.fashion.amai.scrollingpagerindicator.FullScreenBannerAdapter;
import com.fashion.amai.scrollingpagerindicator.PartScreenBannerAdapter;
import com.fashion.amai.scrollingpagerindicator.ScrollingPagerIndicator;
import com.fashion.amai.utils.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityOurDesigns extends BaseActivity {
    private RecyclerView recyclerMenuTop, our_design_recyclerview;
    private List<ModelMenuTopHome> modelMenuTopHomeList;
    private AdapterOurDesign AdapterYourDesignTopMenu;
    private LinearLayoutManager linearLayoutManagerMenuTop;
    private AdapterHomeOurDesign mAdapter;
    private int getScreenWidth;

    private List<Movie> movieList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_designs);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setTitle("Our Designs");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerMenuTop = findViewById(R.id.recycler_menu_top_our_designs);
        our_design_recyclerview = findViewById(R.id.our_design_recyclerview);

        getScreenWidth = getScreenWidth();

        mAdapter = new AdapterHomeOurDesign(movieList, getScreenWidth);

        // vertical RecyclerView
        // keep adapter_home.xmlidth to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep adapter_home.xmlidth to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        our_design_recyclerview.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        our_design_recyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        our_design_recyclerview.setItemAnimator(new DefaultItemAnimator());

        our_design_recyclerview.setAdapter(mAdapter);


        initRecyclerMenuTop();

        prepareMovieData();


        our_design_recyclerview.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });




    }


    private int getScreenWidth() {
        @SuppressWarnings("ConstantConditions")
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        return screenSize.x;
    }

    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", R.drawable.home_img, "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", R.drawable.home_img, "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", R.drawable.home_img, "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", R.drawable.home_img, "2015");
        movieList.add(movie);


        // notify adapter about data set changes
        // so that it will render the list with new data
        mAdapter.notifyDataSetChanged();
    }




    private void initRecyclerMenuTop() {
        modelMenuTopHomeList = new ArrayList<>();
        AdapterYourDesignTopMenu = new AdapterOurDesign(ActivityOurDesigns.this, modelMenuTopHomeList);
        linearLayoutManagerMenuTop = new LinearLayoutManager(ActivityOurDesigns.this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerMenuTop.setLayoutManager(linearLayoutManagerMenuTop);
        recyclerMenuTop.setAdapter(AdapterYourDesignTopMenu);
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.manish_arora), "Manish Arora", 1));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.manish_malhotra), "Manish Malhotra", 2));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.masaba_gupta), "Masaba Gupta", 3));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.prabal_gurung), "Prabal Gurung", 4));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.ritu_kumar), "Ritu Kumar", 5));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.rohit_bal), "Rohit Bal", 6));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.sabyasachi_mukherji), "Sabyasachi Mukherji", 6));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.tarun_tahiliani), "Tarun Tahiliani", 6));
        modelMenuTopHomeList.add(new ModelMenuTopHome(getResources().getDrawable(R.drawable.wendell_rodricks), "Wendell Rodricks", 6));
        AdapterYourDesignTopMenu.notifyDataSetChanged();

    }


    private void hideViews() {

        recyclerMenuTop.animate().translationY(-recyclerMenuTop.getHeight()-120).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        recyclerMenuTop.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

}
