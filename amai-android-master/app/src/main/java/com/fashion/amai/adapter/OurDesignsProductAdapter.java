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
import com.fashion.amai.activity.ActivityOurDesignsProductDetailsNew;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.WishlistPojo;
import com.fashion.amai.utils.Constants;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class OurDesignsProductAdapter extends BaseAdapter {
    private List<MerchantProductsByMerchantID.Datum> dataSet = new ArrayList<>();
    private Context mContext;

    private MerchantInterface.Merchant merchantApiInterface;

    private String merchantType;

   // private AnimationDrawable animationDrawable,AniDraw;
    private int flag = 0;

    private boolean isWishlisted = false;
    private  boolean isWished = false;

    public OurDesignsProductAdapter(Context context, String merchantType, MerchantInterface.Merchant merchantApiInterface) {
        mContext = context;
        this.merchantType = merchantType;
        this.merchantApiInterface = merchantApiInterface;
    }

    public void addAll(List<MerchantProductsByMerchantID.Datum> moveResults) {
        for (MerchantProductsByMerchantID.Datum result : moveResults) {
            add(result);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    public void add(MerchantProductsByMerchantID.Datum r) {
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
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_results_our_designs,
                    null);
        }

        ImageView img_product = itemView.findViewById(R.id.img_product_our_design);
        TextView tv_product_name= itemView.findViewById(R.id.tv_product_name_our_design);
        TextView tv_product_des= itemView.findViewById(R.id.tv_product_des_our_design);
        TextView tv_net_price= itemView.findViewById(R.id.tv_net_price_our_design);

        TextView tv_price= itemView.findViewById(R.id.tv_price);

        TextView tv_discount= itemView.findViewById(R.id.tv_discount_our_design);

        FrameLayout like_button_back= itemView.findViewById(R.id.like_button_back_our_design);

        RelativeLayout select_design_back = itemView.findViewById(R.id.select_design_back);

        ImageView add_to_wishlist = itemView.findViewById(R.id.add_to_wishlist_our_design);
        ImageView select_design_icon = itemView.findViewById(R.id.select_design_icon);

        select_design_icon.setVisibility(View.GONE);


//        animationDrawable = (AnimationDrawable) boutique_like.getDrawable();


        Glide.with(mContext)
                .load(String.valueOf(dataSet.get(position).getCoverImage()))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(itemView.getContext(), R.color.white)))
                .into(img_product);

        tv_product_name.setText(dataSet.get(position).getProductName());
        tv_product_des.setText(dataSet.get(position).getProductName());
        tv_product_des.setText(dataSet.get(position).getProductName());

        if (dataSet.get(position).isIs_wishlisted()){
            add_to_wishlist.setImageDrawable(itemView.getResources().getDrawable(R.drawable.like_heart));
            isWishlisted = true;

        } else {
            add_to_wishlist.setImageDrawable(itemView.getResources().getDrawable(R.drawable.icon_heart));
            isWishlisted = false;
        }


     //   layoutResultItem.setOnClickListener(this);

        like_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWishlisted){
                    deleteWishList(merchantType, dataSet.get(position).getId());
                    isWishlisted = false;
                    add_to_wishlist.setImageDrawable(v.getResources().getDrawable(R.drawable.icon_heart, null));
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
                    add_to_wishlist.setImageDrawable(v.getResources().getDrawable(R.drawable.like_heart, null));

                }


              //  boutique_like.setImageDrawable(v.getResources().getDrawable(R.drawable.icon_heart, null));
            }
        });

        select_design_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///   Log.d("****",""+positon);
             //   Intent intentActivityProducts = new Intent(mContext, ProductActivityOld.class);
                Intent intentMerchantProducts = new Intent(mContext, ActivityOurDesignsProductDetailsNew.class);
                intentMerchantProducts.putExtra(Constants.PRODUCT_ID,  String.valueOf(dataSet.get(position).getId()));
                intentMerchantProducts.putExtra(Constants.IMAGE_URL, String.valueOf(dataSet.get(position).getCoverImage()));
                v.getContext().startActivity(intentMerchantProducts);
            }
        });
        return itemView;
    }

    private void deleteWishList(String type, int id) {

        Call<ClearSelectedDesignsPojo> call = merchantApiInterface.deleteWishList(type, id);
        call.enqueue(new retrofit2.Callback<ClearSelectedDesignsPojo>() {
            @Override
            public void onResponse(Call<ClearSelectedDesignsPojo> call, retrofit2.Response<ClearSelectedDesignsPojo> response) {
                try{
                    if(response.code()==200){
                        // MerchantProductsByMerchantID.Response = response.body().getData();


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
                        // MerchantProductsByMerchantID.Response = response.body().getData();
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


}
