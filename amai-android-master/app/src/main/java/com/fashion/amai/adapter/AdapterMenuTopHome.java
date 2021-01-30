package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.activity.ActivityOurDesigns;
import com.fashion.amai.activity.ActivityOurDesignsProduct;
import com.fashion.amai.activity.MerchantActivity;
import com.fashion.amai.model.ModelMenuTopHome;
import com.fashion.amai.R;

import java.util.List;

public class AdapterMenuTopHome extends RecyclerView.Adapter<AdapterMenuTopHome.CustomViewHolder> {
    private Context context;
    private List<ModelMenuTopHome> modelMenuTopHomeList;

    public AdapterMenuTopHome(Context context, List<ModelMenuTopHome> modelMenuTopHomeList) {
        this.context = context;
        this.modelMenuTopHomeList = modelMenuTopHomeList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_menu_top_home, parent, false);
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
        CardView cvItem;
        ImageView ivItemCover;
        TextView tvItemTitle;

        CustomViewHolder(View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.cv_item);
            ivItemCover = itemView.findViewById(R.id.iv_item_cover);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            cvItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (getAdapterPosition()){
                case 0:{

                    /*v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));*/
                    v.getContext().startActivity(new Intent(v.getContext(), MerchantActivity.class));
                    break;
                }
                case 1:{
                    //Women
                    v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesigns.class));

                    break;
                }
                case 2:{
                    //Kids
                    v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));

                    break;
                }
                case 3:{

                    v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));

                    break;
                }
                case 4:{

                    v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));

                    break;
                }
                case 5:{

                    v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));

                    break;
                }
                case 6:{

                    v.getContext().startActivity(new Intent(v.getContext(), ActivityOurDesignsProduct.class));

                    break;
                }
            }
        }
    }

}
