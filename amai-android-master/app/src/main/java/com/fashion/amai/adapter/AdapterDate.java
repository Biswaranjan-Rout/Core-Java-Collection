package com.fashion.amai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.model.DateModel;
import com.fashion.amai.R;

import java.util.List;

public class AdapterDate extends RecyclerView.Adapter<AdapterDate.CustomViewHolder> {
    private Context context;
    private int startIdx = -1;
    private List<DateModel> list;

    public AdapterDate(Context context, List<DateModel> listDate) {
        this.context = context;
        this.list = listDate;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Log.d("state", String.valueOf(list.get(position).getValueState()));
        if(list.get(position).getValueState()==0){
            holder.tv_date.setBackgroundResource(R.drawable.station_shape);
            holder.tv_date.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }else {
            holder.tv_date.setBackgroundResource(R.drawable.station_shape_unselected);
            holder.tv_date.setTextColor(context.getResources().getColor(R.color.colorWhite));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout layoutDate;
        ToggleButton tv_date;

        CustomViewHolder(View itemView) {
            super(itemView);
            layoutDate = itemView.findViewById(R.id.layout_date);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_date.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.tv_date:
                    int positionDate = getAdapterPosition();
                    Log.d("position" , String.valueOf(positionDate));
                    if (list.get(positionDate).getValueState() == 0) {
                        list.get(positionDate).setValueState(1);
                        if (startIdx != -1) {
                            list.get(startIdx).setValueState(0);
                        }
                        startIdx = positionDate;

                    }
                    notifyDataSetChanged();
                    break;

            }
        }
    }
}
