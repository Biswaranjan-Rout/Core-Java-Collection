package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ProductDetailsActivity;
import com.fashion.amai.activity.MerchantDetailsActivity;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.MyWishListDesigns;
import com.fashion.amai.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;

public class AdapterMyWishlist extends RecyclerView.Adapter<AdapterMyWishlist.CustomViewHolder> {
    private Context context;
    private List<MyWishListDesigns.Data> myDataModalList;
    private MerchantInterface.Merchant merchantApiInterface;
    private String merchantType;

    public AdapterMyWishlist(Context context, List<MyWishListDesigns.Data> myDataModalList, MerchantInterface.Merchant merchantApiInterface, String merchantType) {
        this.context = context;
        this.myDataModalList = myDataModalList;
        this.merchantApiInterface = merchantApiInterface;
        this.merchantType = merchantType;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_designs_move_to_bag, parent, false);


        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Picasso.get().load(myDataModalList.get(position).getProductImage()).into(holder.ivItemImage);

        holder.wishlist_product_name.setText(myDataModalList.get(position).getProductName());

        holder.layout_result_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (merchantType.equals(Constants.WISHLIST_PRODUCT)) {
                    Intent intent = new Intent(v.getContext(), ProductDetailsActivity.class);
                    intent.putExtra(Constants.PRODUCT_ID,  String.valueOf(myDataModalList.get(position).getId()));
                    intent.putExtra(Constants.IMAGE_URL, String.valueOf(myDataModalList.get(position).getProductImage()));
                    v.getContext().startActivity(intent);

                } else if (merchantType.equals(Constants.WISHLIST_ALL)) {

                    if (myDataModalList.get(position).getType().equals(Constants.WISHLIST_PRODUCT)){
                        Intent intent = new Intent(v.getContext(), ProductDetailsActivity.class);
                        intent.putExtra(Constants.PRODUCT_ID,  String.valueOf(myDataModalList.get(position).getId()));
                        intent.putExtra(Constants.IMAGE_URL, String.valueOf(myDataModalList.get(position).getProductImage()));
                        v.getContext().startActivity(intent);

                    } else {

                        Intent intentActivityProducts = new Intent(v.getContext(), MerchantDetailsActivity.class);
                        intentActivityProducts.putExtra(Constants.MERCHANT_ID, myDataModalList.get(position).getId());
                        intentActivityProducts.putExtra(Constants.IMAGE_URL, myDataModalList.get(position).getProductImage());
                        v.getContext().startActivity(intentActivityProducts);
                    }


                }

                    else {

                        Intent intentActivityProducts = new Intent(v.getContext(), MerchantDetailsActivity.class);
                        intentActivityProducts.putExtra(Constants.MERCHANT_ID, myDataModalList.get(position).getId());
                        intentActivityProducts.putExtra(Constants.IMAGE_URL, myDataModalList.get(position).getProductImage());
                        v.getContext().startActivity(intentActivityProducts);


                }
                //else if ()


            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataModalList == null ? 0 : myDataModalList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage, delete_wishlist_item;
        TextView wishlist_move_to_bag, wishlist_product_name;
        RelativeLayout layout_result_item;

        CustomViewHolder(View itemView) {
            super(itemView);
            layout_result_item = itemView.findViewById(R.id.layout_result_item);
            ivItemImage = itemView.findViewById(R.id.iv_items);
            delete_wishlist_item = itemView.findViewById(R.id.delete_wishlist_item);
            wishlist_product_name = itemView.findViewById(R.id.wishlist_product_name);
            wishlist_move_to_bag = itemView.findViewById(R.id.wishlist_move_to_bag);

            delete_wishlist_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deleteWishList(merchantType, myDataModalList.get(getAdapterPosition()).getId());

                    myDataModalList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), myDataModalList.size());
                }
            });

            wishlist_move_to_bag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Add to Bag", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void deleteWishList(String type, int id) {

        retrofit2.Call<ClearSelectedDesignsPojo> call = merchantApiInterface.deleteWishList(type, id);
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

}
