package com.fashion.amai.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityOurDesignsProduct;
import com.fashion.amai.model.MerchantStoreData;
import com.fashion.amai.model.Movie;
import com.fashion.amai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class OurDesignProductsAdapter extends RecyclerView.Adapter<OurDesignProductsAdapter.MyViewHolder> {

    private List<Integer> images;
    View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_product_our_design;

        public MyViewHolder(View view) {
            super(view);
            img_product_our_design = (ImageView) view.findViewById(R.id.img_product_our_design);
        }
    }

    public void addAll(List<Integer> moveResults) {
        for (Integer result : moveResults) {
            add(result);
            notifyDataSetChanged();
        }
    }

    public void add(Integer r) {
        images.add(r);

    }


    public OurDesignProductsAdapter(List<Integer> moviesList) {
        this.images = new ArrayList<>();
        this.images = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.img_product_our_design.setImageDrawable(itemView.getResources().getDrawable(images.get(position)));

        holder.img_product_our_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsProduct.class);
                intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Designer Name");
                intentActivityProducts.putExtra(Constants.MERCHANT_ID, 11);
                intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "merchants");
                v.getContext().startActivity(intentActivityProducts);
            }
        });

      //  Movie movie = moviesList.get(position);
      //  holder.title.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();

        // return images.size();
    }
}
