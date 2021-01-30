package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.fashion.amai.R;
import com.fashion.amai.activity.ProductActivity;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.MerchantStoreData;
import com.fashion.amai.model.WishlistPojo;
import com.fashion.amai.utils.Constants;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PersonaliseAdapter extends BaseAdapter {
    private List<MerchantStoreData> dataSet = new ArrayList<>();
    private Context mContext;

    private MerchantInterface.Merchant productApiInterface;

    private String merchantType;

   // private AnimationDrawable animationDrawable,AniDraw;
    private int flag = 0;

    private boolean isWishlisted = false;
    private  boolean isWished = false;

    public PersonaliseAdapter(Context context, String merchantType, MerchantInterface.Merchant productApiInterface) {
        mContext = context;
        this.merchantType = merchantType;
        this.productApiInterface = productApiInterface;
    }

    public void addAll(List<MerchantStoreData> moveResults) {
        for (MerchantStoreData result : moveResults) {
            add(result);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    public void add(MerchantStoreData r) {
        dataSet.add(r);

    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_results,
                    null);
        }

        TextView tv_name= itemView.findViewById(R.id.tv_lay_name);
        TextView tv_add_1= itemView.findViewById(R.id.tv_add_1);
        TextView tv_add_2= itemView.findViewById(R.id.tv_add_2);
        FrameLayout like_button_back= itemView.findViewById(R.id.like_button_back_our_design);

        RelativeLayout layoutResultItem = itemView.findViewById(R.id.layout_result_item);

        ImageView ivBoutique = itemView.findViewById(R.id.iv_items_boutique);
        ImageView boutique_like = itemView.findViewById(R.id.boutique_like);

        Glide.with(mContext)
                .load(String.valueOf(dataSet.get(position).getStoreImage()))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(itemView.getContext(), R.color.white)))
                .into(ivBoutique);

        tv_name.setText(dataSet.get(position).getName());
        tv_add_1.setText(dataSet.get(position).getAddress1());
        tv_add_2.setText(dataSet.get(position).getAddress2());

        if (dataSet.get(position).isIs_wishlisted()){
            boutique_like.setImageDrawable(itemView.getResources().getDrawable(R.drawable.like_heart));
            isWishlisted = true;

        } else {
            boutique_like.setImageDrawable(itemView.getResources().getDrawable(R.drawable.icon_heart));
            isWishlisted = false;
        }

        like_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWishlisted){
                    deleteWishList(merchantType, dataSet.get(position).getId());
                    isWishlisted = false;
                    boutique_like.setImageDrawable(v.getResources().getDrawable(R.drawable.icon_heart, null));
                } else {
                    isWishlisted = true;
                    JsonObject design = new JsonObject();
                    try {
                        design.addProperty("item_type", merchantType);
                        design.addProperty("item_id", dataSet.get(position).getId());
                    } catch (JsonIOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    addToWishlist(design);
                    boutique_like.setImageDrawable(v.getResources().getDrawable(R.drawable.like_heart, null));
                }
            }
        });

        layoutResultItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///   Log.d("****",""+positon);
             //   Intent intentActivityProducts = new Intent(mContext, ProductActivityOld.class);
                Intent intentActivityProducts = new Intent(mContext, ProductActivity.class);
                intentActivityProducts.putExtra(Constants.MERCHANT_NAME, dataSet.get(position).getName());
                intentActivityProducts.putExtra(Constants.MERCHANT_ID, dataSet.get(position).getId());
                intentActivityProducts.putExtra(Constants.IMAGE_URL, dataSet.get(position).getStoreImage());
                intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, merchantType);
                v.getContext().startActivity(intentActivityProducts);
            }
        });
        return itemView;
    }


    private void deleteWishList(String type, int id) {

        retrofit2.Call<ClearSelectedDesignsPojo> call = productApiInterface.deleteWishList(type, id);
        call.enqueue(new retrofit2.Callback<ClearSelectedDesignsPojo>() {
            @Override
            public void onResponse(retrofit2.Call<ClearSelectedDesignsPojo> call, retrofit2.Response<ClearSelectedDesignsPojo> response) {
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
        retrofit2.Call<WishlistPojo> call = productApiInterface.addToWishlist(wishlist);
        call.enqueue(new retrofit2.Callback<WishlistPojo>() {
            @Override
            public void onResponse(retrofit2.Call<WishlistPojo> call, retrofit2.Response<WishlistPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();

                        Log.d("*****","Here in block 2");

                        isWishlisted = true;
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


}
