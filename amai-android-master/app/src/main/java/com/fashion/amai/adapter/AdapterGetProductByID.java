package com.fashion.amai.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.model.GetProductById;
import com.squareup.picasso.Picasso;

public class AdapterGetProductByID extends RecyclerView.Adapter<AdapterGetProductByID.CustomViewHolder> {
        private Context context;
        private GetProductById.ResponseObject getPRoductById;

    public AdapterGetProductByID(Context context, GetProductById.ResponseObject  getPRoductById) {
            this.context = context;
            this.getPRoductById = getPRoductById;
        }

        @Override
        public AdapterGetProductByID.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_designer_details, parent, false);
            return new AdapterGetProductByID.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AdapterGetProductByID.CustomViewHolder holder, int position) {
            Picasso.get().load(String.valueOf(getPRoductById.getData().getImages())).into(holder.ivItemImage);
            holder.txtName.setText(getPRoductById.getData().getProductName());
            holder.txtPrice.setText(String.valueOf(getPRoductById.getData().getPrices()));
            //holder.txtNetPrice.setText((Integer)listMerchantProductById.get(position).getNetPrice());
            holder.txtDiscount.setText(String.valueOf(getPRoductById.getData().getDiscount()));
        }

        @Override
        public int getItemCount() {
        return getItemCount();
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
                //  txtNetPrice = itemView.findViewById(R.id.tv_net_price);
                txtDiscount = itemView.findViewById(R.id.tv_discount_our_design);
                ivItemImage.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                int  position  = getAdapterPosition();
                Log.d("position**",""+position);
                String url=String.valueOf(getPRoductById.getData().getImages());
                Log.d("URL**",""+url);
                /*Intent intentMerchantProducts = new Intent(context, ProductDetailsActivity.class);
                intentMerchantProducts.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentMerchantProducts.putExtra("Position",position);
                intentMerchantProducts.putExtra("imageUrl", url);
                context.startActivity(intentMerchantProducts);*/
            }
        }

    }
