package com.fashion.amai.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMyDesignsTab3 extends RecyclerView.Adapter<AdapterMyDesignsTab3.CustomViewHolder> {
    private Context context;
    private List<MyDataModal> myDataModalList;

    public AdapterMyDesignsTab3(Context context, List<MyDataModal> myDataModalList) {
        this.context = context;
        this.myDataModalList = myDataModalList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_3_my_designs, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Picasso.get().load(myDataModalList.get(position).getImageUrl()).into(holder.ivItem);
        holder.tvItemName.setText(myDataModalList.get(position).getItemName());
        holder.tvDeliveryDate.setText("Expected delivery by\n" + myDataModalList.get(position).getDate());
        holder.tvProgressPercent.setText(myDataModalList.get(position).getOffPercent()+"%");
        holder.progressPercent.setProgress(Integer.valueOf(myDataModalList.get(position).getOffPercent()));
        if(Integer.valueOf(myDataModalList.get(position).getOffPercent())<70){
            holder.progressPercent.getProgressDrawable().setColorFilter(context.getResources().getColor(android.R.color.holo_orange_light), PorterDuff.Mode.SRC_IN);
            holder.tvProgressPercent.setTextColor(context.getResources().getColor(android.R.color.holo_orange_light));
        }
        if(Integer.valueOf(myDataModalList.get(position).getOffPercent())<45){
            holder.progressPercent.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            holder.tvProgressPercent.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return myDataModalList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;
        ProgressBar progressPercent;
        TextView tvItemName, tvDeliveryDate, tvProgressPercent;

        CustomViewHolder(View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.iv_itemImage);
            progressPercent = itemView.findViewById(R.id.progress_percent);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvDeliveryDate = itemView.findViewById(R.id.tv_delivery_date);
            tvProgressPercent = itemView.findViewById(R.id.tv_progress_percent);
        }
    }
}
