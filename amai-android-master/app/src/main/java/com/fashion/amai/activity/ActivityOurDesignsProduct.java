package com.fashion.amai.activity;

import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fashion.amai.R;
import com.fashion.amai.adapter.OurDesignsProductAdapter;
import com.fashion.amai.custom.PageViewModel;
import com.fashion.amai.fragment.GenderBottomDialogFragment;
import com.fashion.amai.fragment.SortBottomDialogFragment;
import com.fashion.amai.model.GetStoreDetail;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.utils.AddorRemoveCallbacks;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.DataKeys;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ActivityOurDesignsProduct extends BaseActivity implements GenderBottomDialogFragment.ItemClickListener, SortBottomDialogFragment.ItemClickListener, View.OnClickListener, AddorRemoveCallbacks {
    private PageViewModel pageViewModel;
    private GridView boutiqueGridview;
    private GridLayoutManager gridLayoutManager;
    private OurDesignsProductAdapter adapterResults;
    private BottomSheetDialog bottomSheetDialog;

    private MerchantProductsByMerchantID.Response listMerchantProductById;


    private View product_progressbar;
    private List<GetStoreDetail.ResponseObject> listBoutique;
    private List<String> listImage  = new ArrayList<>();

    private LinearLayout layoutGender, layoutSort, layoutFilter;

    private static final int PAGE_START = 0;
    private static final int PER_PAGE = 10;
    private int currentPage = PAGE_START;
    private int totalItems;

    private String merchantName, imageUrl, merchatType;
    private int merchant_id;

    //private String merchantType;

    List<MerchantProductsByMerchantID.Datum> data;

    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_designs_product_details);

        boutiqueGridview = findViewById(R.id.recycler_results);
        product_progressbar = findViewById(R.id.personalise_progressbar);

        Intent intent  = getIntent();
        merchantName =intent.getStringExtra(Constants.MERCHANT_NAME);
        merchant_id =intent.getIntExtra(Constants.MERCHANT_ID,11);
        imageUrl   = intent.getStringExtra(Constants.IMAGE_URL);
        merchatType   = intent.getStringExtra(Constants.MERCHANT_TYPE);

        adapterResults = new OurDesignsProductAdapter(this, DataKeys.MERCHANT_BOUTIQUE, merchantApiInterface);


        getSupportActionBar().setTitle("Products Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // merchantType = getArguments().getString(DataKeys.MERCHANT_BOUTIQUE);


        layoutGender = findViewById(R.id.layout_gender);
        layoutSort = findViewById(R.id.layout_sort);
        layoutFilter = findViewById(R.id.layout_filter);
        bottomSheetDialog = new BottomSheetDialog(this);

        layoutGender.setOnClickListener(this);
        layoutSort.setOnClickListener(this);
        layoutFilter.setOnClickListener(this);

        getMerchantByType();


    }

    private void getMerchantByType() {

        retrofit2.Call<MerchantProductsByMerchantID.Response> call = productApiInterface.getMerchantProductsByMerchantID(String.valueOf(merchant_id),  Constants.READYMADE ,
                String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MerchantProductsByMerchantID.Response>() {
            @Override
            public void onResponse(retrofit2.Call<MerchantProductsByMerchantID.Response> call, retrofit2.Response<MerchantProductsByMerchantID.Response> response) {
                product_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        listMerchantProductById = response.body();

                        Log.d("*****","Here in block 2");
                        if(listMerchantProductById.getData().size()>0){

                            adapterResults.addAll(response.body().getData());
                            boutiqueGridview.setAdapter(adapterResults);

                            data = response.body().getData();
                            totalItems = response.body().getData().size();
                            setGridViewListender();

                           // adapter.addAll(response.body().getData(), merchantApiInterface, ProductActivity.this);
                            // gridView.setAdapter(adapter);
                            totalItems = listMerchantProductById.getData().size();
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
                product_progressbar.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }


    private void getMerchantByType02() {

        retrofit2.Call<MerchantProductsByMerchantID.Response> call = productApiInterface.getMerchantProductsByMerchantID(String.valueOf(merchant_id),Constants.READYMADE,
                String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MerchantProductsByMerchantID.Response>() {
            @Override
            public void onResponse(retrofit2.Call<MerchantProductsByMerchantID.Response> call, retrofit2.Response<MerchantProductsByMerchantID.Response> response) {
                product_progressbar.setVisibility(View.GONE);

                try{
                    if(response.code()==200){
                        listMerchantProductById = response.body();

                        Log.d("*****","Here in block 2");
                        if(listMerchantProductById.getData().size()>0){

                            adapterResults.addAll(response.body().getData());
                         //   boutiqueGridview.setAdapter(adapterResults);

                            data = response.body().getData();
                            totalItems = response.body().getData().size();
                            setGridViewListender();

                            // adapter.addAll(response.body().getData(), merchantApiInterface, ProductActivity.this);
                            // gridView.setAdapter(adapter);
                           // totalItems = listMerchantProductById.getData().size();
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

    private void setGridViewListender(){

        boutiqueGridview.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount >= totalItems){

                    if (data.size() == 10){
                        try {
                            currentPage += 1;
                            product_progressbar.setVisibility(View.VISIBLE);

                            getMerchantByType02();
                            //  mPresenter.getListUserJoinedGroups02( key_user_age, "1",String.valueOf(currentPage));
                            //    getUserJoinedGroups(MySharedPreferences.getUserId(preferences), key_user_age, "1",currentPage);
                            //  loadNextPage();
                            totalItems += 10;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        //    Timber.d("nothing");
                    }

                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.layout_gender: {

                GenderBottomDialogFragment addPhotoBottomDialogFragment = GenderBottomDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getSupportFragmentManager(), GenderBottomDialogFragment.TAG);


              /*  view = getLayoutInflater().inflate(R.layout.bottom_sheet_gender, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

               */
                break;
            }
            case R.id.layout_sort: {

                SortBottomDialogFragment addPhotoBottomDialogFragment = SortBottomDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getSupportFragmentManager(), SortBottomDialogFragment.TAG);


             /*   view = getLayoutInflater().inflate(R.layout.bottom_sheet_sort, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

              */
                break;
            }
        }


    }

    @Override
    public void onItemClick (String item){

        Toast.makeText(this, "Our Design Page " + item, Toast.LENGTH_LONG).show();

    }

    @Override public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetDialog.isShowing()) {

                bottomSheetDialog.cancel();
            }

        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onAddProduct() {

    }

    @Override
    public void onRemoveProduct() {

    }
}
