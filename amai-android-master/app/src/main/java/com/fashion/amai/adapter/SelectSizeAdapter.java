package com.fashion.amai.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SelectSizeAdapter  extends RecyclerView.Adapter<SelectSizeAdapter.CustomViewHolder> {

    Context ctx;
    String [] arr ;
    JsonObject sizes;
    private boolean size_s = false;
    private boolean size_m = false;
    private boolean size_l = false;
    private boolean size_xl = false;
   // private Map<String, int> =


    public SelectSizeAdapter(Context context, String[] sizeArray, JsonObject sizes) {
        this.ctx = context;
        this.arr = sizeArray;
       // this.sizes = new ArrayList<>();
        this.sizes = sizes;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_size, parent, false);
        return new SelectSizeAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String currentSize = null;
        if (arr[position].contains("s")){
            currentSize = "s";
        } else if (arr[position].contains("m")){
            currentSize = "s";
        } else if (arr[position].contains("l")){
            currentSize = "l";
        }else if (arr[position].contains("xl")){
            currentSize = "xl";
        }

      holder.size_measure.setText(arr[position]);

      String size = String.valueOf(sizes.get(arr[position]));

      holder.count_left.setText(size + " left");

        String finalCurrentSize = currentSize;
        holder.size_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unCheckOthers(holder, position);
                holder.size_measure.setBackground(v.getResources().getDrawable(R.drawable.button_shape_alphabet_selected, null));
                holder.size_measure.setTextColor(Color.parseColor("#f43d7b"));
                if (finalCurrentSize.equals("s")){
                    size_s = true;
                } else if (finalCurrentSize.equals("m")){
                    size_m = true;
                } else if (finalCurrentSize.equals("l")){
                    size_l = true;
                } else if (finalCurrentSize.equals("xl")){
                    size_xl = true;
                }
            }
        });


    }

    private void unCheckOthers(CustomViewHolder holder, int position) {
        size_s = false;
         size_m = false;
         size_l = false;
         size_xl = false;

         for (int i=0; i<arr.length; i++){
            // holder.size_measure
         }

    }

    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView size_measure, count_left;
        LinearLayout size_select;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            size_measure = itemView.findViewById(R.id.size_measure);
            count_left = itemView.findViewById(R.id.count_left);
            size_select = itemView.findViewById(R.id.size_select);
        }
    }
}
