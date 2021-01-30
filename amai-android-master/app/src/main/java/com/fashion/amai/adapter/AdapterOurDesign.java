package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityOurDesignsDesigners;
import com.fashion.amai.model.ModelMenuTopHome;
import com.fashion.amai.utils.Constants;

import java.util.List;

public class AdapterOurDesign extends RecyclerView.Adapter<AdapterOurDesign.CustomViewHolder> {
    private Context context;
    private List<ModelMenuTopHome> modelMenuTopHomeList;

    public AdapterOurDesign(Context context, List<ModelMenuTopHome> modelMenuTopHomeList) {
        this.context = context;
        this.modelMenuTopHomeList = modelMenuTopHomeList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_menu_our_design, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
      holder.ivItemCover.setImageDrawable(modelMenuTopHomeList.get(position).getItemImage());
      holder.tvItemTitle.setText(modelMenuTopHomeList.get(position).getItemTitle());
    }

    @Override
    public int getItemCount() {
        return modelMenuTopHomeList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivItemCover;
        TextView tvItemTitle;

        CustomViewHolder(View itemView) {
            super(itemView);
            ivItemCover = itemView.findViewById(R.id.iv_item_cover);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);

            ivItemCover.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (getAdapterPosition()){
                case 0:{

                    Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsDesigners.class);
                    intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Manish Arora");
                    intentActivityProducts.putExtra(Constants.MERCHANT_ID, 24);
                    intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                    intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique");
                    v.getContext().startActivity(intentActivityProducts);

                  //  v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));
                    break;
                }
                case 1:{
                    Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsDesigners.class);
                    intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Manish Malhotra");
                    intentActivityProducts.putExtra(Constants.MERCHANT_ID, 24);
                    intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                    intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique");
                    v.getContext().startActivity(intentActivityProducts);
                    //Women
                    break;
                }
                case 2:{
                    Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsDesigners.class);
                    intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Masaba Gupta");
                    intentActivityProducts.putExtra(Constants.MERCHANT_ID, 24);
                    intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                    intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique");
                    v.getContext().startActivity(intentActivityProducts);
                    //Kids
                    break;
                }
            }
        }
    }

}
