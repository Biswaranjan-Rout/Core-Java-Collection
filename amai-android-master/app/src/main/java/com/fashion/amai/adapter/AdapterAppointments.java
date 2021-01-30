package com.fashion.amai.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityAppointmentsCancelled;
import com.fashion.amai.activity.ActivitySchedule;
import com.fashion.amai.activity.AuthActivity;
import com.fashion.amai.model.BookAppointment;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.utils.Constants;
import com.fashion.amai.utils.MySharedPreferences;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterAppointments extends RecyclerView.Adapter<AdapterAppointments.CustomViewHolder> {
    private Context context;
    private List<BookAppointment.Data> myDataModalList;
    private String dateTime, timeString;
    private SharedPreferences preferences;

    public AdapterAppointments(Context context, List<BookAppointment.Data> myDataModalList, SharedPreferences preferences) {
        this.context = context;
        this.myDataModalList = myDataModalList;
        this.preferences = preferences;
    }

    /*
     public void removeAt(int position) {
        myDataModalList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, myDataModalList.size());
    }
     */

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_tab, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
       // Picasso.get().load(myDataModalList.get(position).getImageUrl()).into(holder.ivItem);
        holder.tvItemName.setText(myDataModalList.get(position).getApnId());
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        holder.appointment_status.setText(String.valueOf("Status : " + getAppointMentStatus(myDataModalList.get(position).getStatus())));



        try {
            dateTime = date.format(myDataModalList.get(position).getBookedDate());
            timeString = time.format(myDataModalList.get(position).getBookedDate());
           // System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvDate.setText(dateTime);
        holder.tvTimeSlot.setText(myDataModalList.get(position).getSlot());

        holder.reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ActivitySchedule.class);
                intent.putExtra(Constants.APPOINTMENT_ID, myDataModalList.get(position).getApnId());
              //  intent.putExtra(Constants.SEND_ADDRESS, MySharedPreferences.getAddress(preferences));

                view.getContext().startActivity(intent);
            }
        });

        holder.cancel_appointment_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setTitle("Do you really want to cancel this Appointment");
                alertDialogBuilder.setMessage("Click yes to cancel the Appointment");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent intent = new Intent(view.getContext(), ActivityAppointmentsCancelled.class);
                        intent.putExtra(Constants.APPOINTMENT_ID, myDataModalList.get(position).getApnId());
                        intent.putExtra(Constants.STATUS, Constants.CANCELED);
                        //  intent.putExtra(Constants.SEND_ADDRESS, MySharedPreferences.getAddress(preferences));

                        view.getContext().startActivity(intent);

                      //  deleteWishList(merchantType, myDataModalList.get(getAdapterPosition()).getId());

                       // Todo delete wishlist
                     /*   myDataModalList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, myDataModalList.size());

                      */


                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialogBuilder.show();

            }
        });
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
        TextView tvItemName, tvDate, tvTimeSlot, reschedule, cancel_appointment_textview, appointment_status;

        CustomViewHolder(View itemView) {
            super(itemView);
            ivItem =itemView.findViewById(R.id.iv_itemImage);
            tvItemName =itemView.findViewById(R.id.tv_item_name);
            tvDate =itemView.findViewById(R.id.tv_date);
            tvTimeSlot =itemView.findViewById(R.id.tv_time_slot);
            reschedule =itemView.findViewById(R.id.reschedule);
            appointment_status =itemView.findViewById(R.id.appointment_status);
            cancel_appointment_textview =itemView.findViewById(R.id.cancel_appointment_textview);
        }
    }
}
