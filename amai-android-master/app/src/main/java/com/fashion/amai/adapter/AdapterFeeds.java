package com.fashion.amai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;

public class AdapterFeeds extends RecyclerView.Adapter<AdapterFeeds.CustomViewHolder> {
    private Context context;

    public AdapterFeeds(Context context) {
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_results, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) { }

    @Override
    public int getItemCount() {
        return 50;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        CustomViewHolder(View itemView) {
            super(itemView);
        }

    }

}
