package com.fashion.amai.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fashion.amai.adapter.AdapterMyDesignsTab1;
import com.fashion.amai.adapter.AdapterMyDesignsTab2;
import com.fashion.amai.interfaces.MyDesignCheckoutInterface;
import com.fashion.amai.interfaces.UpdateCheckoutInterface;
import com.fashion.amai.model.CheckedOutPojo;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;
import com.fashion.amai.model.MyDesignPojo;
import com.fashion.amai.model.MyDesignsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyDesignsCheckout extends BaseFragment {
    private RecyclerView recyclerAppointments;
    private LinearLayoutManager linearLayoutManager;

    private AdapterMyDesignsTab2 adapterMyDesignsTab2;

    private List<CheckedOutPojo.Data> myDataModalList;
    private View checkout_progressbar;
    private TextView total_checkout_value;
    private TextView total_bag_discount;
    private TextView order_total;
    private TextView total_checkout_price;
    private int total;
    private int total_full;
    private int total_discount;
    private CheckedOutPojo checkedOutPojo;

    View view;
    private CardView my_design_cart;

    private MyDesignCheckoutInterface myDesignCheckoutInterface;
    private UpdateCheckoutInterface updateCheckoutInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_2_my_designes, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDataModalList = new ArrayList<>();
        recyclerAppointments = view.findViewById(R.id.recycler_checkout_designs);
        my_design_cart = view.findViewById(R.id.my_design_cart);
        checkout_progressbar = view.findViewById(R.id.checkout_progressbar);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        total_checkout_value  = view.findViewById(R.id.total_checkout_value);
        total_bag_discount  = view.findViewById(R.id.total_bag_discount);
        order_total  = view.findViewById(R.id.order_total);
        total_checkout_price  = view.findViewById(R.id.total_checkout_price);

        updateCheckoutInterface = new UpdateCheckoutInterface() {
            @Override
            public void updateTotal(int price, int discount) {


                total_full = total_full - price;

                total_discount = total_discount - discount;

                total_checkout_value.setText(String.valueOf(total_full));
                total_bag_discount.setText(String.valueOf(total_discount));

                order_total.setText(String.valueOf(total_full - total_discount));
                total_checkout_price.setText(String.valueOf(total_full));
            }
        };

        myDesignCheckoutInterface = new MyDesignCheckoutInterface() {
            @Override
            public void hideTotal() {
                my_design_cart.setVisibility(View.GONE);

            }
        };

        adapterMyDesignsTab2 = new AdapterMyDesignsTab2(getActivity(), merchantApiInterface, myDesignCheckoutInterface, updateCheckoutInterface);
        recyclerAppointments.setLayoutManager(linearLayoutManager);
        recyclerAppointments.setAdapter(adapterMyDesignsTab2);

        getCheckoutDesigns();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getCheckoutDesigns() {

        retrofit2.Call<CheckedOutPojo> call = merchantApiInterface.get_checkout_designs();
        call.enqueue(new retrofit2.Callback<CheckedOutPojo>() {
            @Override
            public void onResponse(retrofit2.Call<CheckedOutPojo> call, retrofit2.Response<CheckedOutPojo> response) {
                try{
                    if(response.code()==200){
                        checkedOutPojo = response.body();

                        myDataModalList = response.body().getData();

                        Log.d("*****","Here in block 2");
                        if(myDataModalList.size()>0){

                            adapterMyDesignsTab2.addAll(myDataModalList);

                            total_checkout_value.setText(String.valueOf(response.body().getTotalPrice()));
                            total_full = response.body().getTotalPrice();
                            double doubleNumber = response.body().getTotalDiscount();
                             total_discount = (int) doubleNumber;
                            total_bag_discount.setText(String.valueOf(total_discount));


                            total = response.body().getTotalPrice() - total_discount;
                            order_total.setText(String.valueOf(total));
                            total_checkout_price.setText(String.valueOf(total));

                            checkout_progressbar.setVisibility(View.GONE);
                            // setGridViewListender();

                        }else {
                            my_design_cart.setVisibility(View.GONE);

                            checkout_progressbar.setVisibility(View.GONE);
                            // progressDialog.dismiss();
                        }
                    }else if(response.code()==201){
                        checkout_progressbar.setVisibility(View.GONE);
                        Log.d("*****","Here in block 3");
                    }else if(response.code()==401){
                        //  showToast(SOME_THING_WENT_WRONG);
                        Log.d("*****","Here in block 4");
                        checkout_progressbar.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    //  showToast(SOME_THING_WENT_WRONG);
                    Log.d("*****","Here in block 5");
                    checkout_progressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CheckedOutPojo> call, Throwable t) {
                t.printStackTrace();
                //  showToast(SOME_THING_WENT_WRONG);
                checkout_progressbar.setVisibility(View.GONE);
                Log.d("*****","Here in block 6");
            }

        });
    }
}
