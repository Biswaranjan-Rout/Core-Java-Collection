package com.fashion.amai.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.adapter.AdapterMyWishlist;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;
import com.fashion.amai.model.MyWishListDesigns;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.DataKeys;

import java.util.ArrayList;
import java.util.List;

public class WishListAll extends BaseFragment {
    private RecyclerView recyclerAll;
    private GridLayoutManager gridLayoutManager;
    private AdapterMyWishlist adapterMyWishlist;
    private List<MyDataModal> myDataModalList;
    private View mywishlist_product_progressbar;
    private String wishListType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_1_my_wishlist, container, false);
    }


    public static WishListAll newInstance(String name) {
        WishListAll fragment = new WishListAll();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.WISHLIST_BOUTIQUE, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            wishListType = getArguments().getString(Constants.WISHLIST_BOUTIQUE);
        }

        myDataModalList = new ArrayList<>();
        recyclerAll = view.findViewById(R.id.recycler_all);
        mywishlist_product_progressbar = view.findViewById(R.id.mywishlist_product_progressbar);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);

            getWishlistDesigns01();
    }

    private void getWishlistDesigns01(){

        retrofit2.Call<MyWishListDesigns> call = merchantApiInterface.getUserWishlist(wishListType);
        call.enqueue(new retrofit2.Callback<MyWishListDesigns>() {
            @Override
            public void onResponse(retrofit2.Call<MyWishListDesigns> call, retrofit2.Response<MyWishListDesigns> response) {

                mywishlist_product_progressbar.setVisibility(View.GONE);

                if(response.code()==200){

                    adapterMyWishlist = new AdapterMyWishlist(getActivity(), response.body().getData(), merchantApiInterface, wishListType);
                    recyclerAll.setLayoutManager(gridLayoutManager);
                    recyclerAll.setAdapter(adapterMyWishlist);


                }else if(response.code()==201){

                }else if(response.code() == 401){
                    //here we can get the error
                    Log.d("****","Errormain");

                } else if (response.code() == 404){

                }
            }

            @Override
            public void onFailure(retrofit2.Call<MyWishListDesigns> call, Throwable t) {
                t.printStackTrace();
                //  showToast(ProductActivityOld.SOME_THING_WENT_WRONG);


            }
        });
    }

}
