package com.fashion.amai.scrollingpagerindicator;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityOurDesignsProduct;
import com.fashion.amai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Olifer
 */
public class PartScreenFullBannerAdapter extends RecyclerView.Adapter<PartScreenFullBannerAdapter.ViewHolder> {
    private List<Integer> images;
    View view;


    private int count;
    private final int screenWidth;

    public PartScreenFullBannerAdapter(int count, int screenWidth) {
        images = new ArrayList<>();

        this.count = count;
        this.screenWidth = screenWidth;
    }

    public void addAll(List<Integer> listPages) {
        for (Integer result : listPages) {
            add(result);
            notifyDataSetChanged();
        }
    }

    public void add(Integer r) {
        images.add(r);

    }

    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.part_screen_page, parent, false);
       // view.getLayoutParams().width = screenWidth / 2;

        ImageView label = view.findViewById(R.id.demo_page_label);
        ImageView label2 = view.findViewById(R.id.demo_page_label2);

        label.setOnClickListener(new View.OnClickListener() {
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
        label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsProduct.class);
                intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Designer Name");
                intentActivityProducts.putExtra(Constants.MERCHANT_ID, 11);
                intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique");
                v.getContext().startActivity(intentActivityProducts);
            }
        });


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.title.setText(String.valueOf(position));

        holder.demo_page_label.setImageDrawable(view.getResources().getDrawable(images.get(position)));

    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();

        // return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView demo_page_label;

        ViewHolder(View itemView) {
            super(itemView);
            demo_page_label = itemView.findViewById(R.id.demo_page_label);
        }
    }
}
