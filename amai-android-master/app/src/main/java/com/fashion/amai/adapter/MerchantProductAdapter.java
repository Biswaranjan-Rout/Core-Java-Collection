package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fashion.amai.R;
import com.fashion.amai.activity.ProductActivity;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.Dessert;
import com.fashion.amai.model.MerchantStoreData;
import com.fashion.amai.model.WishlistPojo;
import com.fashion.amai.utils.Constants;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by root on 28/05/17.
 */

public class MerchantProductAdapter extends RecyclerView.Adapter<MerchantProductAdapter.ViewHolder> {
    private List<MerchantStoreData> dataSet = new ArrayList<>();
    private Context mContext;
    private AppCompatActivity appCompatActivity;
    private MerchantInterface.Merchant merchantApiInterface;
    private String merchantType;


    private boolean isSelected = false;
    private boolean isWishlisted = false;
    private  boolean isWished = false;
    private View view;


    private ArrayList<Dessert> items;

    public MerchantProductAdapter(Context context, String merchantType, MerchantInterface.Merchant merchantApiInterface) {
        mContext = context;
        this.merchantType = merchantType;
        this.merchantApiInterface = merchantApiInterface;
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
    public void onBindViewHolder(MerchantProductAdapter.ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(String.valueOf(dataSet.get(position).getStoreImage()))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(mContext, R.color.white)))
                .into(holder.img_product);

        holder.tv_product_name.setText(dataSet.get(position).getName());
        holder.tv_product_des.setText(dataSet.get(position).getAddress1());
        holder.tv_product_des.setText(dataSet.get(position).getAddress2());

        if (dataSet.get(position).isIs_wishlisted()){
            holder.add_to_wishlist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_heart));
            isWishlisted = true;

        } else {
            holder.add_to_wishlist.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_heart));
            isWishlisted = false;
        }


        //   layoutResultItem.setOnClickListener(this);

        holder.like_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWishlisted){
                    deleteWishList(merchantType, dataSet.get(position).getId());
                    isWishlisted = false;
                    holder.add_to_wishlist.setImageDrawable(v.getResources().getDrawable(R.drawable.icon_heart, null));
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
                    holder.add_to_wishlist.setImageDrawable(v.getResources().getDrawable(R.drawable.like_heart, null));

                }


                //  boutique_like.setImageDrawable(v.getResources().getDrawable(R.drawable.icon_heart, null));
            }
        });

        holder.select_design_back.setOnClickListener(new View.OnClickListener() {
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
                /*
                Intent intentMerchantProducts = new Intent(mContext, ProductDetailsActivity.class);
                intentMerchantProducts.putExtra(Constants.PRODUCT_ID,  String.valueOf(dataSet.get(position).getId()));
                intentMerchantProducts.putExtra(Constants.IMAGE_URL, String.valueOf(dataSet.get(position).getStoreImage()));
                v.getContext().startActivity(intentMerchantProducts);

                 */
            }
        });


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.designer_layout, parent, false);

//        animationDrawable = (AnimationDrawable) boutique_like.getDrawable();


        return new ViewHolder(view);
    }




    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_product;
        TextView tv_product_name;
        TextView tv_product_des;
        TextView tv_net_price;
        TextView tv_price;
        TextView tv_discount;
        FrameLayout like_button_back;
        ImageView add_to_wishlist;

        RelativeLayout select_design_back;

        public ViewHolder(View view) {
            super(view);
            img_product = itemView.findViewById(R.id.img_product_our_design);
            tv_product_name= itemView.findViewById(R.id.tv_product_name_our_design);
            tv_product_des= itemView.findViewById(R.id.tv_product_des_our_design);
            tv_net_price= itemView.findViewById(R.id.tv_net_price_our_design);
            tv_price= itemView.findViewById(R.id.tv_price);
            tv_discount= itemView.findViewById(R.id.tv_discount_our_design);

            like_button_back= itemView.findViewById(R.id.like_button_back_our_design);

            select_design_back = itemView.findViewById(R.id.select_design_back);

            add_to_wishlist = itemView.findViewById(R.id.add_to_wishlist_our_design);

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
}
