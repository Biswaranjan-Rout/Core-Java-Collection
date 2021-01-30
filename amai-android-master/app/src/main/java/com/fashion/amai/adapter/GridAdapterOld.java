package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.fashion.amai.R;
import com.fashion.amai.activity.ProductDetailsActivity;
import com.fashion.amai.interfaces.MerchantInterface;
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

public class GridAdapterOld extends BaseAdapter {
    private List<MerchantProductsByMerchantID.Datum> dataSet = new ArrayList<>();
    private Context mContext;
    private AppCompatActivity appCompatActivity;
    private MerchantInterface.Merchant productApiInterface;
    private boolean isSelected = false;
    private boolean isWishlisted = false;
    private  boolean isWished = false;

    public GridAdapterOld(Context context) {
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
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_product_name,
                    null);
        }
        ImageView ivItemImage = itemView.findViewById(R.id.img_product_our_design);
        ImageView add_to_wishlist = itemView.findViewById(R.id.add_to_wishlist_our_design);
        ImageView select_design_icon = itemView.findViewById(R.id.select_design_icon);
        TextView txtName = itemView.findViewById(R.id.tv_product_name_our_design);
        TextView txtPrice = itemView.findViewById(R.id.tv_price);
        TextView txtNetPrice = itemView.findViewById(R.id.tv_net_price_our_design);
        TextView txtDiscount = itemView.findViewById(R.id.tv_discount_our_design);

        if (dataSet.get(position).isIs_selected()){
            select_design_icon.setVisibility(View.VISIBLE);
            isSelected = true;

        } else {
            select_design_icon.setVisibility(View.GONE);
            isSelected = false;
        }

        if (dataSet.get(position).isIs_wishlisted()){
            add_to_wishlist.setImageDrawable(itemView.getResources().getDrawable(R.drawable.like_heart));
            isWishlisted = true;

        } else {
            add_to_wishlist.setImageDrawable(itemView.getResources().getDrawable(R.drawable.icon_heart));
            isWishlisted = false;
        }


        add_to_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isWishlisted){
                 //   unselectDesign(dataSet.get(position).getId().toString());
                    isWishlisted = false;
                    add_to_wishlist.setImageDrawable(view.getResources().getDrawable(R.drawable.icon_heart, null));

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
                    add_to_wishlist.setImageDrawable(view.getResources().getDrawable(R.drawable.like_heart, null));

                }




            }
        });

        select_design_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isSelected){
                    unselectDesign(dataSet.get(position).getId().toString());
                    isSelected = false;
                    select_design_icon.setImageDrawable(view.getResources().getDrawable(R.drawable.unselected, null));
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
                    select_design_icon.setImageDrawable(view.getResources().getDrawable(R.drawable.tick, null));

                }
            }
        });



        Glide.with(mContext)
                .load(String.valueOf(dataSet.get(position).getCoverImage()))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(itemView.getContext(), R.color.white)))
                .into(ivItemImage);





        ivItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMerchantProducts = new Intent(view.getContext(), ProductDetailsActivity.class);
                intentMerchantProducts.putExtra(Constants.PRODUCT_ID,  String.valueOf(dataSet.get(position).getId()));
                intentMerchantProducts.putExtra(Constants.IMAGE_URL, String.valueOf(dataSet.get(position).getCoverImage()));
              //  view.getContext().startActivityForResult(intentMerchantProducts);
                appCompatActivity.startActivityForResult(intentMerchantProducts, 1);

            }
        });

        txtName.setText(dataSet.get(position).getProductName());
        txtPrice.setText(String.valueOf(dataSet.get(position).getPrice()));
        txtNetPrice.setText(dataSet.get(position).getNetPrice());
        txtDiscount.setText(dataSet.get(position).getDiscount());


        return itemView;
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

    private void selectDesign(JsonObject design) {

        retrofit2.Call<SelectDesignPojo> call = productApiInterface.selectDesign( design);
        call.enqueue(new retrofit2.Callback<SelectDesignPojo>() {
            @Override
            public void onResponse(retrofit2.Call<SelectDesignPojo> call, retrofit2.Response<SelectDesignPojo> response) {

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

    private void unselectDesign(String productId) {


        retrofit2.Call<UnSelectPojo> call = productApiInterface.unselectproduct(String.valueOf(productId));
        call.enqueue(new retrofit2.Callback<UnSelectPojo>() {
            @Override
            public void onResponse(retrofit2.Call<UnSelectPojo> call, retrofit2.Response<UnSelectPojo> response) {
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

}
