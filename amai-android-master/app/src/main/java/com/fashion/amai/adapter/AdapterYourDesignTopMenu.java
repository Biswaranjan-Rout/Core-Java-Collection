package com.fashion.amai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.model.ModelMenuTopHome;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterYourDesignTopMenu extends RecyclerView.Adapter<AdapterYourDesignTopMenu.CustomViewHolder> {
    private Context context;
    private List<ModelMenuTopHome> modelMenuTopHomeList;

    public AdapterYourDesignTopMenu(Context context, List<ModelMenuTopHome> modelMenuTopHomeList) {
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

        return modelMenuTopHomeList == null ? 0 : modelMenuTopHomeList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvItem;
        CircleImageView ivItemCover;
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

                    Toast.makeText(v.getContext(), "Message", Toast.LENGTH_LONG).show();
                    break;
                }
                case 1:{
                    //Women
                    break;
                }
                case 2:{
                    //Kids
                    break;
                }
            }
        }
    }

}
