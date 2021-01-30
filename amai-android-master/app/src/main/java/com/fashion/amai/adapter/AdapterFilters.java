package com.fashion.amai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;

import java.util.List;

public class AdapterFilters extends RecyclerView.Adapter<AdapterFilters.CustomViewHolder> {
    private Context context;
    private List<String> listFilters;

    public AdapterFilters(Context context, List<String> listFilters) {
        this.context = context;
        this.listFilters = listFilters;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filters, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tvFilters.setText(listFilters.get(position));
    }

    @Override
    public int getItemCount() {
        return listFilters.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFilters;

        CustomViewHolder(View itemView) {
            super(itemView);
            tvFilters = itemView.findViewById(R.id.tv_filters);
            tvFilters.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
        }
    }

}