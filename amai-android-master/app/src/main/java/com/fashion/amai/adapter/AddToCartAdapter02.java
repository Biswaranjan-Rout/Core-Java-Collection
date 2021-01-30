package com.fashion.amai.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fashion.amai.R;
import com.fashion.amai.model.CartPojo;

import java.util.ArrayList;
import java.util.List;

public class AddToCartAdapter02 extends RecyclerView.Adapter<AddToCartAdapter02.ViewHolder> {
    private List<CartPojo.CartItems> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private  View v;
    private SharedPreferences preferences;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, product_count;
        public TextView price_add_to_cart;
        public TextView color;
        public TextView product_size_add_to_cart;

        public ImageView image, ic_add, ic_remove;

        public int totalQuantity = 0;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            name = (TextView) v.findViewById(R.id.add_to_cart_name);
            product_count = (TextView) v.findViewById(R.id.product_count);
            price_add_to_cart = (TextView) v.findViewById(R.id.price_add_to_cart);
            color = (TextView) v.findViewById(R.id.color_add_to_cart);
            product_size_add_to_cart = (TextView) v.findViewById(R.id.product_size_add_to_cart);
            image = (ImageView) v.findViewById(R.id.add_to_cart_image);
            ic_remove = (ImageView) v.findViewById(R.id.ic_remove);
            ic_add = (ImageView) v.findViewById(R.id.ic_add);
        }
    }

    public void add(int position, CartPojo.CartItems person) {
        mPeopleList.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public AddToCartAdapter02(List<CartPojo.CartItems> myDataset, Context context, RecyclerView recyclerView, SharedPreferences preferences) {

        mPeopleList = new ArrayList<>();
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
        this.preferences = preferences;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AddToCartAdapter02.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
         v =
                inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final CartPojo.CartItems product = mPeopleList.get(position);
        holder.name.setText("Name: " + product.getProduct_name());
        holder.price_add_to_cart.setText("Price: " + product.getActual_price());
        holder.color.setText("Color: " + "White");
        holder.product_size_add_to_cart.setText("Size: " + product.getSize());

        holder.totalQuantity = product.getProduct_quantity();

        holder.product_count.setText(String.valueOf(product.getProduct_quantity()));


        holder.ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    holder.totalQuantity++;
                    holder.product_count.setText(String.valueOf(holder.totalQuantity));

            }
        });

        holder.ic_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( holder.totalQuantity==1){
                    mPeopleList.remove(position);
                    mRecyclerV.removeViewAt(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mPeopleList.size());
                    notifyDataSetChanged();

                } else {

                    holder.totalQuantity--;
                    holder.product_count.setText(String.valueOf(holder.totalQuantity));
                }
            }
        });


        //  holder.color.setText("Quantity: " + product.getQuantity());

       /* holder.delete_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mPeopleList.remove(position);
                mRecyclerV.removeViewAt(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mPeopleList.size());
                notifyDataSetChanged();

              /*  AddToCartDBHelper dbHelper = new AddToCartDBHelper(mContext, preferences);
                dbHelper.deleteCartProduct(product.getProduct_id(), mContext);
                // Todo add validation to invalidate option menu


            }
        });

        */

        Glide.with(mContext)
                .load(String.valueOf(mPeopleList.get(position).getImage()))
                .centerCrop()
                .placeholder(new ColorDrawable(ContextCompat.getColor(v.getContext(), R.color.white)))
                .into(holder.image);

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }



}