package com.fashion.amai.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityHome;
import com.fashion.amai.activity.MerchantActivity;
import com.fashion.amai.model.Movie;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder> {

    private List<Movie> moviesList;
    View itemView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView emptyView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            emptyView = (ImageView) view.findViewById(R.id.emptyView);
        }
    }


    public AdapterHome(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        if (position==0){
            // add one view at top and other things follow
            holder.emptyView.setVisibility(View.VISIBLE);
        } else {
            holder.emptyView.setVisibility(View.GONE);
        }

        holder.imageView.setImageDrawable(itemView.getResources().getDrawable(moviesList.get(position).getImage_url()));


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), MerchantActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
