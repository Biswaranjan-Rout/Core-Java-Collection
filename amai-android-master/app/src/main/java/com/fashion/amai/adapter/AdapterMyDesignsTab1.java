package com.fashion.amai.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.activity.ActivityMyDesigns;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.AddedToCheckoutPojo;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;
import com.fashion.amai.model.MyDesignsModel;
import com.fashion.amai.utils.Constants;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;

public class AdapterMyDesignsTab1 extends RecyclerView.Adapter<AdapterMyDesignsTab1.CustomViewHolder> {
    private Context context;
    private List<MyDesignsModel.Data> myDataModalList;
    private String dateTime, timeString;

    protected int position;

    MerchantInterface.Merchant merchantApiInterface;

    public AdapterMyDesignsTab1(Context context, List<MyDesignsModel.Data> myDataModalList, MerchantInterface.Merchant merchantApiInterface) {
        this.context = context;
        this.myDataModalList = myDataModalList;
        this.merchantApiInterface = merchantApiInterface;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_1_my_designs, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Picasso.get().load(myDataModalList.get(position).getProductImage()).into(holder.ivItem);
        holder.tvItemName.setText(myDataModalList.get(position).getProductName());
        holder.tvAppointmentStatus.setText(String.valueOf("Status : " + getAppointMentStatus(myDataModalList.get(position).getStatus())));
        holder.appointment_id.setText(String.valueOf("Appoint. ID : " + String.valueOf(myDataModalList.get(position).getApnId())));

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        try {
            dateTime = date.format(myDataModalList.get(position).getBookedDate());
            timeString = time.format(myDataModalList.get(position).getBookedDate());
            // System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvDate.setText(dateTime);
        holder.tvTimeSlot.setText(timeString);

        holder.confirm_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showConfirmationDialog(v, holder, position);

            }
        });
    }

    private void showConfirmationDialog(View view, CustomViewHolder holder, int position){
        new AlertDialog.Builder(view.getContext())
                .setTitle("Do you want to add this to checkout")
                .setMessage("Click confirm to continue")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        JsonObject design = new JsonObject();

                        try {
                            design.addProperty("product_id", myDataModalList.get(position).getProductId());
                            design.addProperty("qty", 1);
                            design.addProperty("apn_id", myDataModalList.get(position).getApnId());

                        } catch (JsonIOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        sendCheckoutRequest(design, holder, position, view);
                        removeAt(position);

                    }
                }).create().show();
    }

    public void removeAt(int position) {
        myDataModalList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, myDataModalList.size());
    }

    private String getAppointMentStatus(int appointmentStatus) {
        String status = "";
        switch (appointmentStatus){
            case Constants.BOOKED:
                status = "BOOKED";
                break;
            case Constants.RESCHEDULED:
                status = "RESCHEDULED";
                break;
            case Constants.CANCELED:
                status = "CANCELED";
                break;
            case Constants.RESOLVED:
                status = "RESOLVED";
                break;

        }
        return status;
    }

    @Override
    public int getItemCount() {
        return myDataModalList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItem;
        TextView confirm_design, tvItemName, tvAppointmentStatus, tvDate, tvTimeSlot, appointment_id;
       // protected  Button ;

        CustomViewHolder(View itemView) {
            super(itemView);
            ivItem =itemView.findViewById(R.id.iv_itemImage);
            tvItemName =itemView.findViewById(R.id.tv_item_name);
            tvAppointmentStatus =itemView.findViewById(R.id.tv_status);
            tvDate =itemView.findViewById(R.id.tv_date);
            tvTimeSlot =itemView.findViewById(R.id.tv_time_slot);
            appointment_id =itemView.findViewById(R.id.appointment_id);
            confirm_design =itemView.findViewById(R.id.confirm_design);

        }

    }

    private void sendCheckoutRequest(JsonObject object, CustomViewHolder holder, int position, View v) {

        retrofit2.Call<AddedToCheckoutPojo> call = merchantApiInterface.checkout_designs(object);
        call.enqueue(new retrofit2.Callback<AddedToCheckoutPojo>() {
            @Override
            public void onResponse(retrofit2.Call<AddedToCheckoutPojo> call, retrofit2.Response<AddedToCheckoutPojo> response) {
                holder.confirm_design.setVisibility(View.GONE);

                try{
                    if(response.code()==200){

                        Intent intent = new Intent(v.getContext(), ActivityMyDesigns.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        v.getContext().startActivity(intent);

                        Log.d("*****","Here in block 2");

                    }else if(response.code()==201){
                        holder.confirm_design.setVisibility(View.GONE);

                        //  design_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        holder.confirm_design.setVisibility(View.GONE);

                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                    //    design_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    holder.confirm_design.setVisibility(View.GONE);

                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                   // design_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddedToCheckoutPojo> call, Throwable t) {
                t.printStackTrace();
                holder.confirm_design.setVisibility(View.GONE);

                //  showToast(SOME_THING_WENT_WRONG);
                Log.d("*****","Here in block 6");
            }

        });
    }

}
