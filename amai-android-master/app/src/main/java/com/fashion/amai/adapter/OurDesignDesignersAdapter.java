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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityOurDesignsProduct;
import com.fashion.amai.activity.ActivityOurDesignsProductDetailsNew;
import com.fashion.amai.activity.ProductDetailsActivity;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.Dessert;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.SelectDesignPojo;
import com.fashion.amai.model.UnSelectPojo;
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

public class OurDesignDesignersAdapter extends RecyclerView.Adapter<OurDesignDesignersAdapter.ViewHolder> {
    private List<MerchantProductsByMerchantID.Datum> dataSet = new ArrayList<>();
    private Context mContext;
    private AppCompatActivity appCompatActivity;
    private MerchantInterface.Merchant productApiInterface;
    private boolean isSelected = false;
    private boolean isWishlisted = false;
    private  boolean isWished = false;
    private View view;


    private ArrayList<Dessert> items;

    public OurDesignDesignersAdapter(Context context){
        mContext = context;
    }

    public void addAll(List<MerchantProductsByMerchantID.Datum> moveResults, MerchantInterface.Merchant productApiInterface, AppCompatActivity appCompatActivity) {
        for (MerchantProductsByMerchantID.Datum result : moveResults) {
            add(result);
            notifyDataSetChanged();
        }
        this.productApiInterface = productApiInterface;
        this.appCompatActivity = appCompatActivity;
    }

    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    public void add(MerchantProductsByMerchantID.Datum r) {
        dataSet.add(r);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (dataSet.get(position).isIs_selected()){
            holder.select_design_icon.setVisibility(View.VISIBLE);
            isSelected = true;

        } else {
            holder.select_design_icon.setVisibility(View.GONE);
            isSelected = false;
        }

        if (dataSet.get(position).isIs_wishlisted()){
            holder.add_to_wishlist.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.like_heart));
            isWishlisted = true;

        } else {
            holder.add_to_wishlist.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.icon_heart));
            isWishlisted = false;
        }


        holder.add_to_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isWishlisted){
                    //   unselectDesign(dataSet.get(position).getId().toString());
                    isWishlisted = false;
                    holder.add_to_wishlist.setImageDrawable(view.getResources().getDrawable(R.drawable.icon_heart, null));
                } else {
                    isWishlisted = true;
                    JsonObject design = new JsonObject();
                    try {
                        design.addProperty("item_type", "product");
                        design.addProperty("item_id", dataSet.get(position).getId());

                    } catch (JsonIOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    addToWishlist(design);
                    holder.add_to_wishlist.setImageDrawable(view.getResources().getDrawable(R.drawable.like_heart, null));

                }




            }
        });

        holder.like_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSelected){
                    unselectDesign(dataSet.get(position).getId().toString());
                    isSelected = false;
                    holder.select_design_icon.setImageDrawable(view.getResources().getDrawable(R.drawable.unselected, null));
                } else {
                    isSelected = true;
                    JsonObject design = new JsonObject();
                    try {
                        design.addProperty("product_id", dataSet.get(position).getId());
                    } catch (JsonIOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    selectDesign(design);
                    holder.select_design_icon.setImageDrawable(view.getResources().getDrawable(R.drawable.tick, null));

                }
            }
        });



        Glide.with(mContext)
                .load(String.valueOf(dataSet.get(position).getCoverImage()))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.white)))
                .into(holder.ivItemImage);


        holder.ivItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMerchantProducts = new Intent(mContext, ActivityOurDesignsProductDetailsNew.class);
                intentMerchantProducts.putExtra(Constants.PRODUCT_ID,  String.valueOf(dataSet.get(position).getId()));
                intentMerchantProducts.putExtra(Constants.IMAGE_URL, String.valueOf(dataSet.get(position).getCoverImage()));
              /*  v.getContext().startActivity(intentMerchantProducts);

                Intent intentActivityProducts = new Intent(view.getContext(), ActivityOurDesignsProduct.class);
                intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Designer Name");
                intentActivityProducts.putExtra(Constants.MERCHANT_ID, 24);
                intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique"); */
                //  view.getContext().startActivityForResult(intentMerchantProducts);
                appCompatActivity.startActivityForResult(intentMerchantProducts, 1);

            }
        });

        holder.txtName.setText(dataSet.get(position).getProductName());
        holder.txtPrice.setText(String.valueOf(dataSet.get(position).getPrice()));
        holder.txtNetPrice.setText(dataSet.get(position).getNetPrice());
        holder.txtDiscount.setText(dataSet.get(position).getDiscount());
    }

    private void addToWishlist(JsonObject wishlist) {
        Call<WishlistPojo> call = productApiInterface.addToWishlist(wishlist);
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

    private void unselectDesign(String productId) {
        Call<UnSelectPojo> call = productApiInterface.unselectproduct(String.valueOf(productId));
        call.enqueue(new retrofit2.Callback<UnSelectPojo>() {
            @Override
            public void onResponse(Call<UnSelectPojo> call, retrofit2.Response<UnSelectPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();
                        Log.d("*****","Here in block 2");

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
            public void onFailure(Call<UnSelectPojo> call, Throwable t) {
                t.printStackTrace();
                // showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

    private void selectDesign(JsonObject design) {
        Call<SelectDesignPojo> call = productApiInterface.selectDesign( design);
        call.enqueue(new retrofit2.Callback<SelectDesignPojo>() {
            @Override
            public void onResponse(Call<SelectDesignPojo> call, retrofit2.Response<SelectDesignPojo> response) {

                //  Log.d("Design Error", retrofit2.Response.error(400))
                try{
                    if(response.code()==200){

                        Log.d("*****","Here in block 2");


                    }else if(response.code()==201){

                        SelectDesignPojo.DesignData selectDesignPojo = response.body().getData();
                        Log.d("*****","Here in block 2");

                    }else if(response.code()==401){
                        //   showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        // activity_details_progressBar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    // showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    //  activity_details_progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SelectDesignPojo> call, Throwable t) {
                t.printStackTrace();
                //    activity_details_progressBar.setVisibility(View.GONE);
                //showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItemImage;
        ImageView add_to_wishlist;
        ImageView select_design_icon;
        TextView txtName;
        TextView txtPrice;
        TextView txtNetPrice;
        TextView txtDiscount;
        FrameLayout like_button_back;


        public ViewHolder(View view) {
            super(view);

            like_button_back = itemView.findViewById(R.id.like_button_back_our_design);
            ivItemImage = itemView.findViewById(R.id.img_product_our_design);
            add_to_wishlist = itemView.findViewById(R.id.add_to_wishlist_our_design);
            select_design_icon = itemView.findViewById(R.id.select_design_icon);
            txtName = itemView.findViewById(R.id.tv_product_name_our_design);
            txtPrice = itemView.findViewById(R.id.tv_price);
            txtNetPrice = itemView.findViewById(R.id.tv_net_price_our_design);
            txtDiscount = itemView.findViewById(R.id.tv_discount_our_design);




        }
    }
}
