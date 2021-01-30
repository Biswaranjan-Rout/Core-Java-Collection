package com.fashion.amai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.MyMeasurementDetailsActivity;
import com.fashion.amai.utils.Constants;

import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.CustomViewHolder> {
    private Context context;
    private List<String> profileOptionsList;

    public AdapterProfile(Context context, List<String> profileOptionsList) {
        this.context = context;
        this.profileOptionsList = profileOptionsList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_options, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tvProfileOption.setText(profileOptionsList.get(position));
    }

    @Override
    public int getItemCount() {
        return profileOptionsList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvProfileOption;

        CustomViewHolder(View itemView) {
            super(itemView);
            tvProfileOption = itemView.findViewById(R.id.tv_profile_option);
            tvProfileOption.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_profile_option: {


                    Intent intent = new Intent(view.getContext(), MyMeasurementDetailsActivity.class);
                   // intent.putExtra(Constants.MEASUREMENT_ID, 26);
                    view.getContext().startActivity(intent);

                   // view.getContext().startActivity(new Intent(view.getContext(), MyMeasurementDetailsActivity.class));

                    break;
                }
                case 1: {

                    Intent intent = new Intent(view.getContext(), MyMeasurementDetailsActivity.class);
                  //  intent.putExtra(Constants.MEASUREMENT_ID, 26);
                    view.getContext().startActivity(intent);

                   //view.getContext().startActivity(new Intent(view.getContext(), MyMeasurementDetailsActivity.class));


                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                case 5: {
                    break;
                }
                case 6: {
                    break;
                }
                case 7: {
                    break;
                }
                case 8: {
                    break;
                }
            }
        }
    }

}