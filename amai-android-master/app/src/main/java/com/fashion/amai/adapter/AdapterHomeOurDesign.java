package com.fashion.amai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.R;
import com.fashion.amai.model.Movie;
import com.fashion.amai.scrollingpagerindicator.FullScreenBannerAdapter;
import com.fashion.amai.scrollingpagerindicator.FullScreenPartBannerAdapter;
import com.fashion.amai.scrollingpagerindicator.PartScreenBannerAdapter;
import com.fashion.amai.scrollingpagerindicator.PartScreenFullBannerAdapter;
import com.fashion.amai.scrollingpagerindicator.ScrollingPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeOurDesign extends RecyclerView.Adapter<AdapterHomeOurDesign.MyViewHolder> {

    private List<Movie> moviesList;
    View itemView;
    private int getWidthScreen;
    private OurDesignProductsAdapter ourDesignProductsAdapter;
    List<Integer> images;
    List<Integer> images02;
    public static int LOOPS_COUNT = 1000;


    public class MyViewHolder extends RecyclerView.ViewHolder {
       // public ImageView imageView;
        public ImageView emptyView;
        public RecyclerView recycler_our_recyclerview;

        public MyViewHolder(View view) {
            super(view);
            recycler_our_recyclerview = (RecyclerView) view.findViewById(R.id.recycler_our_recyclerview);
            emptyView = (ImageView) view.findViewById(R.id.emptyView);
        }
    }

    public AdapterHomeOurDesign(List<Movie> moviesList, int getWidthScreen) {

        this.moviesList = moviesList;
        this.getWidthScreen = getWidthScreen;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_our_design, parent, false);

        ViewPager pager = itemView.findViewById(R.id.pager_full_screen);
        FullScreenBannerAdapter pagerAdapter = new FullScreenBannerAdapter(8);
        pager.setAdapter(pagerAdapter);

        int pagerAdapterIndex = pagerAdapter.getCount();


        images = new ArrayList<>();
        images.add(R.drawable.our_design_01);
        images.add(R.drawable.our_design_02);
        images.add(R.drawable.our_design_03);
        images.add(R.drawable.our_design_04);
        images.add(R.drawable.our_design_05);

        images02 = new ArrayList<>();
        images02.add(R.drawable.products1);
        images02.add(R.drawable.products3);
        images02.add(R.drawable.products4);
        images02.add(R.drawable.products6);
        images02.add(R.drawable.products4);
        images02.add(R.drawable.products4);

        pagerAdapter.addAll(images);

        ScrollingPagerIndicator pagerIndicator = itemView.findViewById(R.id.pager_indicator);
        pagerIndicator.attachToPager(pager);


        // Setup RecyclerView with indicator
        // One page will occupy 1/3 of screen width

        ViewPager pagerpartScreen = itemView.findViewById(R.id.recycler_part_pager);
        FullScreenPartBannerAdapter adapter02 = new FullScreenPartBannerAdapter(8);
        pagerpartScreen.setAdapter(adapter02);
        adapter02.addAll(images);

        ScrollingPagerIndicator pagerIndicator02 = itemView.findViewById(R.id.recycler_indicator);
        pagerIndicator02.attachToPager(pagerpartScreen);

        pagerIndicator.setVisibleDotCount(5);
        pagerIndicator02.setVisibleDotCount(5);
        pagerAdapter.setCount(10); // full screen add_to_cart_image
        adapter02.setCount(5);

        int fullScreenPagerAdapterindex = adapter02.getCount();

        if(fullScreenPagerAdapterindex >= 4)
        {

            for(int i = 0 ; i<=1000 ; i++) {
                adapter02.addAll(images);
                pagerAdapter.addAll(images);
            }


        }
        /*if(pagerAdapterIndex >= 4 )
        {

            for(int i = 0 ; i<=1000 ; i++)


        }*/
        ourDesignProductsAdapter = new OurDesignProductsAdapter(images02);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        if (position==0){
            holder.emptyView.setVisibility(View.VISIBLE);
        } else {
            holder.emptyView.setVisibility(View.GONE);

        }

        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(itemView.getContext());
        holder.recycler_our_recyclerview.setLayoutManager(new GridLayoutManager(itemView.getContext(), 2));
        holder.recycler_our_recyclerview.setHasFixedSize(true);
        holder.recycler_our_recyclerview.addItemDecoration(new DividerItemDecoration(itemView.getContext(), LinearLayoutManager.VERTICAL));
        holder.recycler_our_recyclerview.setItemAnimator(new DefaultItemAnimator());
        holder.recycler_our_recyclerview.setAdapter(ourDesignProductsAdapter);


       // holder.imageView.setImageDrawable(itemView.getResources().getDrawable(R.drawable.homep1));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
