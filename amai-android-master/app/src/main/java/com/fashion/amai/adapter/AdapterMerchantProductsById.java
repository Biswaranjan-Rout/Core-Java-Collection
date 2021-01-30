package com.fashion.amai.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ProductDetailsActivity;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMerchantProductsById extends RecyclerView.Adapter<AdapterMerchantProductsById.CustomViewHolder> {
    private Context context;
    private List<MerchantProductsByMerchantID.Datum>  listMerchantProductById;

    public AdapterMerchantProductsById(Context context, List<MerchantProductsByMerchantID.Datum>  listMerchantProductById) {
        this.context = context;
        this.listMerchantProductById = listMerchantProductById;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_name, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Picasso.get().load(listMerchantProductById.get(position).getCoverImage()).into(holder.ivItemImage);

        holder.txtName.setText(listMerchantProductById.get(position).getProductName());
        holder.txtPrice.setText(String.valueOf(listMerchantProductById.get(position).getPrice()));
        holder.txtNetPrice.setText(listMerchantProductById.get(position).getNetPrice());
        holder.txtDiscount.setText(listMerchantProductById.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return listMerchantProductById.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivItemImage;
        TextView txtName,txtPrice, txtNetPrice, txtDiscount;

        @SuppressLint("ClickableViewAccessibility")
        CustomViewHolder(View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.img_product_our_design);
            txtName = itemView.findViewById(R.id.tv_product_name_our_design);
           txtPrice = itemView.findViewById(R.id.tv_price);
            txtNetPrice = itemView.findViewById(R.id.tv_net_price_our_design);
            txtDiscount = itemView.findViewById(R.id.tv_discount_our_design);
           ivItemImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int  position  = getAdapterPosition();
            Intent intentMerchantProducts = new Intent(context, ProductDetailsActivity.class);
            intentMerchantProducts.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentMerchantProducts.putExtra(Constants.PRODUCT_ID, listMerchantProductById.get(position).getId());
            intentMerchantProducts.putExtra(Constants.IMAGE_URL, String.valueOf(listMerchantProductById.get(position).getCoverImage()));
            context.startActivity(intentMerchantProducts);
        }
    }

}
