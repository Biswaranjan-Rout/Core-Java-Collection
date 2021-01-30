package com.fashion.amai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;

import java.util.List;

public class AdapterFilterOptions extends RecyclerView.Adapter<AdapterFilterOptions.CustomViewHolder> {
    private Context context;
    private List<String> listFilterOptions;

    public AdapterFilterOptions(Context context, List<String> listFilterOptions) {
        this.context = context;
        this.listFilterOptions = listFilterOptions;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_options, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tvFilterOptions.setText(listFilterOptions.get(position));
    }

    @Override
    public int getItemCount() {
        return listFilterOptions.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFilterOptions;

        CustomViewHolder(View itemView) {
            super(itemView);
            tvFilterOptions = itemView.findViewById(R.id.tv_filter_options);
            tvFilterOptions.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setSelected(false);
            switch (getAdapterPosition()) {
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

    public void showToast(String toastMessage){
        Toast.makeText(context,toastMessage, Toast.LENGTH_SHORT).show();
    }

}