package com.fashion.amai.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.model.TimeModel;
import com.fashion.amai.R;

import java.util.List;

public class AdapterTime extends RecyclerView.Adapter<AdapterTime.CustomViewHolder> {
    private Context context;
    List<TimeModel>  list;
    private int startIdx=-1;

    public AdapterTime(Context context, List<TimeModel> listDate) {
        this.context = context;
        this.list = listDate;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if(list.get(position).getValueState()==0){
            holder.tvTime.setBackgroundResource(R.drawable.station_shape);
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }else {
            holder.tvTime.setBackgroundResource(R.drawable.station_shape_unselected);
            holder.tvTime.setTextColor(context.getResources().getColor(R.color.colorWhite));

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ToggleButton tvTime;
        CustomViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTime.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
          switch (view.getId()){
              case R.id.tv_time:
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
