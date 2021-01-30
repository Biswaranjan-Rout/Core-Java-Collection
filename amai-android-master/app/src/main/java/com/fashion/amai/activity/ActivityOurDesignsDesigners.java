package com.fashion.amai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.fashion.amai.BuildConfig;
import com.fashion.amai.R;
import com.fashion.amai.adapter.OurDesignDesignersAdapter;
import com.fashion.amai.model.GetAllProducts;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.FetchAddressIntentService;
import com.fashion.amai.utils.InfiniteScrollProvider;
import com.fashion.amai.utils.OnLoadMoreListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ActivityOurDesignsDesigners extends BaseActivity  implements View.OnClickListener, OnLoadMoreListener {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recyclerView;
    private Menu collapseMenu;
    private List<GetAllProducts.ResponseObject> listProductFromApi  = new ArrayList<>();
    private List<MerchantProductsByMerchantID.Datum>  listMerchantProductById;
    private static final int PAGE_START = 0;
    private static final int PER_PAGE = 10;
    private int currentPage = PAGE_START;
    private int totalItems;
    public String merchatType, merchantName;
    public int merchant_id;
    public String imageUrl;
    private ImageView cv_viewDetails;
    private View product_progressbar;
    private LinearLayout layoutProceedMeasurement, layoutBookAppointment, layoutBannerSelect;
    private RadioGroup radioGroup;



    private ImageView cvViewDetails;
    private boolean appBarExpanded = true;

    OurDesignDesignersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_design_product);

        Intent intent  = getIntent();
        merchantName =intent.getStringExtra(Constants.MERCHANT_NAME);
        merchant_id =intent.getIntExtra(Constants.MERCHANT_ID,0);
        imageUrl   = intent.getStringExtra(Constants.IMAGE_URL);
        merchatType   = intent.getStringExtra(Constants.MERCHANT_TYPE);


        cvViewDetails = findViewById(R.id.cv_viewDetails);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        recyclerView = (RecyclerView) findViewById(R.id.scrollableview);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_arrow_back));



        if (merchantName!=null){
            collapsingToolbar.setTitle(merchantName);
        } else {
            collapsingToolbar.setTitle("Products");

        }

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedtoolbar);


        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.white)))
                .into(cvViewDetails);

        product_progressbar = findViewById(R.id.product_progressbar);

        product_progressbar.setVisibility(View.VISIBLE);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new OurDesignDesignersAdapter(getBaseContext());
        recyclerView.setAdapter(adapter);

        InfiniteScrollProvider infiniteScrollProvider=new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView,this);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){
                    appBarExpanded = false;

                }else{
                    appBarExpanded = true;
                }
                invalidateOptionsMenu();
            }
        });

        intiView();
        getMerchantProductsByMerchantId();
    }



    private void intiView() {

        layoutProceedMeasurement  = findViewById(R.id.layout_proceed_with_measurement);
        layoutBookAppointment  = findViewById(R.id.layout_book_appointment);
        layoutBannerSelect  = findViewById(R.id.layout_banner_select);
        radioGroup = findViewById(R.id.radio_group_product);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_btn_book_call) {
                Intent intentActivityTimeSlot = new Intent(this, ActivitySchedule.class);
                intentActivityTimeSlot.putExtra("radio","bookCall");
                startActivity(intentActivityTimeSlot);
            } else if (checkedId == R.id.radio_btn_book_appointment) {
                Intent intentActivityTimeSlot = new Intent(this, ActivitySelectAddress.class);
                intentActivityTimeSlot.putExtra("radio","bookAppointment");
                startActivity(intentActivityTimeSlot);                }
        });
        layoutBookAppointment.setOnClickListener(this);
        layoutProceedMeasurement.setOnClickListener(this);

        //ivMenuDrawer.setOnClickListener(this);
        cvViewDetails.setOnClickListener(this);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(collapseMenu != null && (!appBarExpanded || collapseMenu.size() != 1)){
            //collapsed
            collapseMenu.add("Add")
                    .setIcon(R.drawable.icon_arrow)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{

        }
        return super.onPrepareOptionsMenu(collapseMenu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cv_viewDetails:
                Intent  intent  = new Intent(this, MerchantDetailsActivity.class);
                intent.putExtra(Constants.IMAGE_URL, imageUrl);
                intent.putExtra(Constants.MERCHANT_ID, merchant_id);
                startActivity(intent);
                break;
            case  R.id.layout_book_appointment:
                radioGroup.setVisibility(View.VISIBLE);
                layoutBannerSelect.setVisibility(View.GONE);
                break;
            case R.id.layout_proceed_with_measurement:
                Intent intent02  = new Intent(this, MyMeasurementDetailsActivity.class);
                intent02.putExtra(Constants.IMAGE_URL, imageUrl);
                intent02.putExtra(Constants.MERCHANT_TYPE, merchatType);
                intent02.putExtra(Constants.MERCHANT_ID, merchant_id);
                //intent02.putExtra(Constants.MEASUREMENT_ID, 26);
                startActivity(intent02);

                break;

        }
    }
    /*  private void hideViews() {

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) layoutFooterBottom.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        recyclerMenuTop.animate().translationY(-recyclerMenuTop.getHeight()-120).setInterpolator(new AccelerateInterpolator(2));

        layoutFooterBottom.animate().translationY(layoutFooterBottom.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        recyclerMenuTop.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        layoutFooterBottom.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }*/

    private void getMerchantProductsByMerchantId() {

        // todo get products page

        retrofit2.Call<MerchantProductsByMerchantID.Response> call = productApiInterface.getMerchantProductsByMerchantID(String.valueOf(merchant_id), Constants.READYMADE,
                String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MerchantProductsByMerchantID.Response>() {
            @Override
            public void onResponse(retrofit2.Call<MerchantProductsByMerchantID.Response> call, retrofit2.Response<MerchantProductsByMerchantID.Response> response) {
                product_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        listMerchantProductById = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(listMerchantProductById.size()>0){

                            adapter.addAll(response.body().getData(), merchantApiInterface, ActivityOurDesignsDesigners.this);
                            // gridView.setAdapter(adapter);
                            totalItems = listMerchantProductById.size();
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
            public void onFailure(Call<MerchantProductsByMerchantID.Response> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void getMerchantProductsByMerchantId02() {

        retrofit2.Call<MerchantProductsByMerchantID.Response> call = productApiInterface.getMerchantProductsByMerchantID(String.valueOf(merchant_id),Constants.READYMADE,
                String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MerchantProductsByMerchantID.Response>() {
            @Override
            public void onResponse(retrofit2.Call<MerchantProductsByMerchantID.Response> call, retrofit2.Response<MerchantProductsByMerchantID.Response> response) {
                product_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        listMerchantProductById = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(listMerchantProductById.size()>0){

                            adapter.addAll(response.body().getData(), merchantApiInterface, ActivityOurDesignsDesigners.this);
                            totalItems = listMerchantProductById.size();
                            product_progressbar.setVisibility(View.GONE);

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
                    // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    product_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MerchantProductsByMerchantID.Response> call, Throwable t) {
                t.printStackTrace();
                // showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    @Override
    public void onLoadMore() {
        if (listMerchantProductById.size() == 10){
            try {
                product_progressbar.setVisibility(View.VISIBLE);

                currentPage += 1;
                getMerchantProductsByMerchantId02();

                //  loadNextPage();
                // isLoading = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1)
        {
            currentPage = 0;
            adapter.clear();
            getMerchantProductsByMerchantId();
            // String message=data.getStringExtra("MESSAGE");
            //  textView1.setText(message);
        }
    }


}


