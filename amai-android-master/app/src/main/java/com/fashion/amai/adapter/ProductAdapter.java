package com.fashion.amai.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fashion.amai.model.GetProductById;
import com.fashion.amai.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context ctx;
    private GetProductById.Data list;
    String imageUrl ;
    private View view;

    public ProductAdapter(Context context, GetProductById.Data listProduct, String imageUrl) {
        this.ctx =context;
        this.list = listProduct;
        this.imageUrl  = imageUrl;
    }



    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_name, parent, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Glide.with(ctx)
                .load(String.valueOf(imageUrl))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.white)))
                .into(holder.imgProduct);


        holder.tvProductDetail.setText(list.getProductName());
        holder.tvProductDes.setText(list.getDescription());
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView imgProduct;
          TextView tvProductDetail, tvProductDes;

        @SuppressLint("ClickableViewAccessibility")
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct  = itemView.findViewById(R.id.img_product_our_design);
            tvProductDetail  = itemView.findViewById(R.id.tv_product_name_our_design);
            tvProductDes  = itemView.findViewById(R.id.tv_product_des_our_design);
            imgProduct.setOnClickListener(this);

}
        @Override
        public void onClick(View view) {
            /*Intent intentActivityProducts = new Intent(ctx, ProductDetailsActivity.class);
            intentActivityProducts.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentActivityProducts.putExtra("imageUrl", list.get(getAdapterPosition()).getImage());
            ctx.startActivity(intentActivityProducts);*/
        }
    }


}
