package com.fashion.amai.activity;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fashion.amai.adapter.MerchantDetailsAdapter;
import com.fashion.amai.R;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.DetailsPojo;
import com.fashion.amai.model.WishlistPojo;
import com.fashion.amai.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MerchantDetailsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivStore, merchant_wish_list;
    private RadioGroup radioGroup;
    private LinearLayout layoutNeedMore;
    private ViewPager viewPager;
    private int [] arrayListDesigner  = new int[]{R.drawable.image_blazer, R.drawable.image_blazer,
            R.drawable.image_blazer, R.drawable.image_blazer };
    private String imageUrl, merchant_type;
    private int merchant_id;
    List<String> list  = new ArrayList<>();
    private LinearLayout layoutContactShortly;
    private MerchantDetailsAdapter adapter;
    private View details_progress;
    private List<String> imageList;
    private boolean isWishlisted = false;

    private DetailsPojo merchant_Response;

    private TextView tv_name, tv_owner_name, tv_address1, tv_address2, tv_rating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Details Page");

        Intent intent = getIntent();
        imageUrl  = intent.getStringExtra(Constants.IMAGE_URL);
        merchant_id = intent.getIntExtra(Constants.MERCHANT_ID,0);
        merchant_type = intent.getStringExtra(Constants.MERCHANT_TYPE);

        // inti view

        imageList = new ArrayList<>();

        imageList.add(imageUrl);
        imageList.add(imageUrl);
        imageList.add(imageUrl);

        intiView();



        getMerchantById();

    }

    private void intiView() {
        TabLayout tabLayout =  findViewById(R.id.tabDots_details);
        viewPager = findViewById(R.id.view_pager_details);
        details_progress = findViewById(R.id.details_progress);
        tv_name = findViewById(R.id.tv_name);
        tv_rating = findViewById(R.id.tv_rating);
        merchant_wish_list = findViewById(R.id.merchant_wish_list);
        tv_owner_name = findViewById(R.id.tv_owner_name);
        tv_address1 = findViewById(R.id.tv_address1);
        tv_address2 = findViewById(R.id.tv_address2);

        adapter = new MerchantDetailsAdapter(getApplicationContext(), imageList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);
        radioGroup  = findViewById(R.id.radio_group_details);
        layoutNeedMore  = findViewById(R.id.layout_need_more);
        layoutContactShortly  = findViewById(R.id.layout_descr_more_info);
        layoutNeedMore.setOnClickListener(this);
        radioGroup.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_btn_book_call) {
                Intent intentActivityTimeSlot = new Intent(MerchantDetailsActivity.this, ActivitySchedule.class);
                intentActivityTimeSlot.putExtra("radio","bookCall");
                startActivity(intentActivityTimeSlot);
            } else if (checkedId == R.id.radio_btn_book_appointment) {
                Intent intentActivityTimeSlot = new Intent(MerchantDetailsActivity.this, ActivitySelectAddress.class);
                intentActivityTimeSlot.putExtra("radio","bookAppointment");
                startActivity(intentActivityTimeSlot);                }
        });

        merchant_wish_list.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_need_more:
               // radioGroup.setVisibility(View.VISIBLE);
                layoutContactShortly.setVisibility(View.VISIBLE);
                break;
            case R.id.merchant_wish_list:
               // radioGroup.setVisibility(View.VISIBLE);
               // layoutContactShortly.setVisibility(View.VISIBLE);

                if (isWishlisted){
                    deleteWishList(merchant_type, merchant_id);
                    isWishlisted = false;
                    merchant_wish_list.setImageDrawable(MerchantDetailsActivity.this.getResources().getDrawable(R.drawable.icon_heart, null));
                } else {
                    isWishlisted = true;
                    JsonObject design = new JsonObject();
                    try {
                        design.addProperty("item_type", merchant_type);
                        design.addProperty("item_id", merchant_id);
                    } catch (JsonIOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    addToWishlist(design);

                    merchant_wish_list.setImageDrawable(MerchantDetailsActivity.this.getResources().getDrawable(R.drawable.like_heart, null));

                 //   holder.add_to_wishlist.setImageDrawable(v.getResources().getDrawable(R.drawable.like_heart, null));

                }

                break;
        }

    }

    private void deleteWishList(String type, int id) {

        Call<ClearSelectedDesignsPojo> call = merchantApiInterface.deleteWishList(type, id);
        call.enqueue(new retrofit2.Callback<ClearSelectedDesignsPojo>() {
            @Override
            public void onResponse(Call<ClearSelectedDesignsPojo> call, retrofit2.Response<ClearSelectedDesignsPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();


                        Log.d("*****","Here in block 2");

                    }else if(response.code()==201){
                        // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //  product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    // product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ClearSelectedDesignsPojo> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }


    private void addToWishlist(JsonObject wishlist) {
        Call<WishlistPojo> call = merchantApiInterface.addToWishlist(wishlist);
        call.enqueue(new retrofit2.Callback<WishlistPojo>() {
            @Override
            public void onResponse(Call<WishlistPojo> call, retrofit2.Response<WishlistPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();
                        Log.d("*****","Here in block 2");


                        isWishlisted = true;

                        //select_design_icon
                    }
                    else if(response.code()==201){
                        // product_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        // showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        //product_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WishlistPojo> call, Throwable t) {
                t.printStackTrace();
                // showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void getMerchantById() {

        retrofit2.Call<DetailsPojo> call = merchantApiInterface.getMerchantById(String.valueOf(merchant_id));
        call.enqueue(new retrofit2.Callback<DetailsPojo>() {
            @Override
            public void onResponse(retrofit2.Call<DetailsPojo> call, retrofit2.Response<DetailsPojo> response) {
                try{
                    if(response.code()==200){

                        merchant_Response = response.body();
                       // adapter.addAll(response.body());

                        tv_name.setText(response.body().getData().getOwnerName());
                        tv_owner_name.setText(response.body().getData().getAddress());
                        tv_address1.setText(response.body().getData().getAddress());

                        tv_rating.setText(response.body().getData().getRating());
                        details_progress.setVisibility(View.GONE);

                        if (merchant_Response.getData().isIs_wishlisted()){
                            merchant_wish_list.setImageDrawable(MerchantDetailsActivity.this.getResources().getDrawable(R.drawable.like_heart));
                            isWishlisted = true;

                        } else {
                            merchant_wish_list.setImageDrawable(MerchantDetailsActivity.this.getResources().getDrawable(R.drawable.icon_heart));
                            isWishlisted = false;
                        }

                    }else if(response.code()==201){
                        details_progress.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        // showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        details_progress.setVisibility(View.GONE);
                    }else if(response.code()==404){
                        // showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        details_progress.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                 //   showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    details_progress.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DetailsPojo> call, Throwable t) {
                t.printStackTrace();
               // showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }
}
