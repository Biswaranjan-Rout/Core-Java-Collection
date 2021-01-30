package com.fashion.amai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityFilters;
import com.fashion.amai.adapter.MerchantProductAdapter;
import com.fashion.amai.custom.PageViewModel;
import com.fashion.amai.model.GetStoreDetail;
import com.fashion.amai.model.MerchantData;
import com.fashion.amai.model.MerchantStoreData;
import com.fashion.amai.utils.DataKeys;
import com.fashion.amai.utils.HidingScrollListener;
import com.fashion.amai.utils.InfiniteScrollProvider;
import com.fashion.amai.utils.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalizeFragment extends BaseFragment implements OnLoadMoreListener,  GenderBottomDialogFragment.ItemClickListener, SortBottomDialogFragment.ItemClickListener {
    private PageViewModel pageViewModel;
    private RecyclerView recyclerview;
    private GridLayoutManager gridLayoutManager;
    private MerchantProductAdapter adapterResults;
    private View personalise_progressbar;
    private List<GetStoreDetail.ResponseObject> listBoutique;
    private List<String> listImage  = new ArrayList<>();

    private static final int PAGE_START = 0;
    private static final int PER_PAGE = 10;
    private int currentPage = PAGE_START;
    private int totalItems;
   // private LinearLayout layout_no_post;
   // private LinearLayout layout_error;
    private CardView footer_result;

    private LinearLayout layoutGender, layoutSort, layoutFilter;

    private String merchantType;

    List<MerchantStoreData> data;

    // No post parameters
    private TextView no_post_heading;
    private Button no_post_retry;

    // Error parameters
    private TextView error_message; // Sorry Couldn't fetch post
    private TextView error_txt_cause; // The server took too long to respond.
    private Button error_btn_retry; // Reload Button


    int index = 1;

    public static PersonalizeFragment newInstance(String name) {
        PersonalizeFragment fragment = new PersonalizeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataKeys.MERCHANT_BOUTIQUE, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        if (getArguments() != null) {
            merchantType = getArguments().getString(DataKeys.MERCHANT_BOUTIQUE);
        }

        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boutiques, container, false);
        personalise_progressbar = view.findViewById(R.id.personalise_progressbar);

        layoutGender = view.findViewById(R.id.layout_gender);
        layoutSort = view.findViewById(R.id.layout_sort);
        layoutFilter = view.findViewById(R.id.layout_filter);

        layoutGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GenderBottomDialogFragment addPhotoBottomDialogFragment = GenderBottomDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getFragmentManager(), GenderBottomDialogFragment.TAG);

            }
        });
        layoutSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SortBottomDialogFragment addPhotoBottomDialogFragment = SortBottomDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getFragmentManager(), SortBottomDialogFragment.TAG);
            }
        });
        layoutFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.getContext().startActivity(new Intent(v.getContext(), ActivityFilters.class));
            }
        });


     //   layout_error = view.findViewById(R.id.layout_error);
     //   layout_no_post = view.findViewById(R.id.layout_no_post);

        footer_result = (CardView) view.findViewById(R.id.footer_result);
        no_post_heading = view.findViewById(R.id.no_post_heading);
        no_post_retry = view.findViewById(R.id.no_post_retry); //button
        error_message = view.findViewById(R.id.error_message); // Sorry Couldn't fetch post
        error_txt_cause = view.findViewById(R.id.error_txt_cause); // The server took too long to respond.
        error_btn_retry = view.findViewById(R.id.error_btn_retry); // button

        adapterResults = new MerchantProductAdapter(getContext(), merchantType, merchantApiInterface);

        recyclerview = view.findViewById(R.id.recycler_results_personlize);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerview.setAdapter(adapterResults);

        InfiniteScrollProvider infiniteScrollProvider=new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerview,this);


        /*hideViews();
        showViews();*/
        getMerchantByType();


      //  imageView.setImageResource(R.drawable.inducesmilelog);




      /*  recyclerview.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

       */

        return view;
    }


    private void hideViews() {

        CardView.LayoutParams lp = (CardView.LayoutParams) footer_result.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;

        footer_result.animate().translationY(footer_result.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        footer_result.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }


    private void getMerchantByType(){

        retrofit2.Call<MerchantData> call = merchantApiInterface.getMerchantByType( merchantType,  String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MerchantData>() {
            @Override
            public void onResponse(retrofit2.Call<MerchantData> call, retrofit2.Response<MerchantData> response) {

                personalise_progressbar.setVisibility(View.GONE);

                if(response.code()==200){

                    if (response.body().getData().size()==0){
                        //layout_no_post.setVisibility(View.VISIBLE);


                    } else {
                        adapterResults.addAll(response.body().getData());
                        recyclerview.setAdapter(adapterResults);

                        data = response.body().getData();
                        totalItems = response.body().getData().size();

                     //   setGridViewListender();

                    }



                }else if(response.code()==201){

                }else if(response.code() == 401){
                    //here we can get the error
                    Log.d("****","Errormain");

                } else if (response.code() == 404){

                }
            }

            @Override
            public void onFailure(retrofit2.Call<MerchantData> call, Throwable t) {
                t.printStackTrace();

                personalise_progressbar.setVisibility(View.GONE);

                // showToast(ProductActivityOld.SOME_THING_WENT_WRONG);


            }
        });
    }

  /*  private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString("An Unexpected ");

        if (getActivity()!=null){
            if (!isNetworkConnected()) {
                errorMsg = getResources().getString(R.string.error_msg_no_internet);
            } else if (throwable instanceof TimeoutException) {
                errorMsg = getResources().getString(R.string.error_msg_timeout);
            }
        }


        return errorMsg;
    }

   */

    private void getMerchantByType02(){

        retrofit2.Call<MerchantData> call = merchantApiInterface.getMerchantByType( merchantType,  String.valueOf(currentPage), String.valueOf(PER_PAGE));
        call.enqueue(new retrofit2.Callback<MerchantData>() {
            @Override
            public void onResponse(retrofit2.Call<MerchantData> call, retrofit2.Response<MerchantData> response) {

                personalise_progressbar.setVisibility(View.GONE);

                if(response.code()==200){

                    adapterResults.addAll(response.body().getData());
                    totalItems = response.body().getData().size();


                }else if(response.code()==201){

                }else if(response.code() == 401){
                    //here we can get the error
                    Log.d("****","Errormain");

                } else if (response.code() == 404){

                }
            }

            @Override
            public void onFailure(retrofit2.Call<MerchantData> call, Throwable t) {
                t.printStackTrace();
              //  showToast(ProductActivityOld.SOME_THING_WENT_WRONG);


            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        if (data.size() == 10){
            try {
                personalise_progressbar.setVisibility(View.VISIBLE);

                currentPage += 1;
                getMerchantByType02();
                totalItems += 10;


                //  loadNextPage();
                // isLoading = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onItemClick(String item) {
        Toast.makeText(getContext(), "Merchant Page " + item, Toast.LENGTH_LONG).show();

    }
}