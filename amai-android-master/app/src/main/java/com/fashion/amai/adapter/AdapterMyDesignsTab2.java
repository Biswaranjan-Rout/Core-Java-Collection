package com.fashion.amai.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.fragment.MyDesignsCheckout;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.interfaces.MyDesignCheckoutInterface;
import com.fashion.amai.interfaces.UpdateCheckoutInterface;
import com.fashion.amai.model.CheckedOutPojo;
import com.fashion.amai.model.ClearCheckoutPojo;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;
import com.fashion.amai.model.WishlistPojo;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AdapterMyDesignsTab2 extends RecyclerView.Adapter<AdapterMyDesignsTab2.CustomViewHolder> {
    private Context context;
    private List<CheckedOutPojo.Data> myDataModalList;
    private MerchantInterface.Merchant merchantApiInterface;
    private MyDesignCheckoutInterface myDesignCheckoutInterface;
    private UpdateCheckoutInterface updateCheckoutInterface;


    public AdapterMyDesignsTab2(Context context, MerchantInterface.Merchant merchantApiInterface, MyDesignCheckoutInterface myDesignCheckoutInterface, UpdateCheckoutInterface updateCheckoutInterface) {
        this.context = context;
        myDataModalList = new ArrayList<>();
        this.merchantApiInterface = merchantApiInterface;
        this.myDesignCheckoutInterface = myDesignCheckoutInterface;
        this.updateCheckoutInterface = updateCheckoutInterface;
    }

    public void addAll(List<CheckedOutPojo.Data> myDataModalList) {
        for (CheckedOutPojo.Data result : myDataModalList) {
            add(result);
            notifyDataSetChanged();
        }
    }

    public void add(CheckedOutPojo.Data r) {
        myDataModalList.add(r);

    }


    public void clear() {
        myDataModalList.clear();
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_2_my_designs, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Picasso.get().load(myDataModalList.get(position).getProductImage()).into(holder.ivItem);
        holder.tvItemName.setText(myDataModalList.get(position).getProductName());
        holder.tvPrice.setText("₹ " + myDataModalList.get(position).getPrice());
        holder.tvOffPrice.setText("₹" + myDataModalList.get(position).getDiscount());
        holder.tvOffPercent.setText("20" + "% off");
        holder.tvQuantity.setText("Qty : " + "1");

        holder.remove_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double doubleNumber = myDataModalList.get(position).getDiscount();
                int discount = (int) doubleNumber;

                updateCheckoutInterface.updateTotal(myDataModalList.get(position).getPrice(),discount);



                deleteCheckout(myDataModalList.get(position).getProduct_id() );

                myDataModalList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, myDataModalList.size());

                if (myDataModalList.size()==0){
                    myDesignCheckoutInterface.hideTotal();
                }
            }
        });

        holder.add_to_wishlist_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // todo add to wishlist
                JsonObject design = new JsonObject();
                try {
                    design.addProperty("item_type", "product");
                    design.addProperty("item_id", myDataModalList.get(position).getProduct_id());
                } catch (JsonIOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                addToWishlist(design);

                deleteCheckout(myDataModalList.get(position).getProduct_id() );

                myDataModalList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, myDataModalList.size());

            }
        });


    }

    @Override
    public int getItemCount() {
        return myDataModalList == null ? 0 : myDataModalList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;
        TextView tvItemName, tvPrice, tvOffPrice, tvOffPercent, tvQuantity;
        LinearLayout add_to_wishlist_checkout, remove_checkout;

        CustomViewHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.iv_itemImage);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvOffPrice = itemView.findViewById(R.id.tv_off_amount);
            tvOffPrice.setPaintFlags(tvOffPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvOffPercent = itemView.findViewById(R.id.tv_off_percent);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            add_to_wishlist_checkout = itemView.findViewById(R.id.add_to_wishlist_checkout);
            remove_checkout = itemView.findViewById(R.id.remove_checkout);
        }

    }

    private void addToWishlist(JsonObject wishlist) {
        retrofit2.Call<WishlistPojo> call = merchantApiInterface.addToWishlist(wishlist);
        call.enqueue(new retrofit2.Callback<WishlistPojo>() {
            @Override
            public void onResponse(retrofit2.Call<WishlistPojo> call, retrofit2.Response<WishlistPojo> response) {
                try{
                    if(response.code()==200){
                        // listMerchantProductById = response.body().getData();
                        Log.d("*****","Here in block 2");


                        // isWishlisted = true;

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

    private void deleteCheckout(int id) {

        retrofit2.Call<ClearCheckoutPojo> call = merchantApiInterface.deleteCheckout(id);
        call.enqueue(new retrofit2.Callback<ClearCheckoutPojo>() {
            @Override
            public void onResponse(retrofit2.Call<ClearCheckoutPojo> call, retrofit2.Response<ClearCheckoutPojo> response) {
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
            public void onFailure(Call<ClearCheckoutPojo> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

}
