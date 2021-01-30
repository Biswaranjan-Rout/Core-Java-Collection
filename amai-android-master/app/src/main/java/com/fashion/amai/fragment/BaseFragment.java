package com.fashion.amai.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.fashion.amai.custom.ApiClient;
import com.fashion.amai.interfaces.AuthInterface;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.interfaces.MyDesignAppointmentInterface;
import com.fashion.amai.interfaces.ProductInterface;
import com.fashion.amai.utils.MySharedPreferences;

import static com.fashion.amai.utils.Constants.CONSTANT_PREF_FILE;

public abstract class BaseFragment extends Fragment {
    protected SharedPreferences preferences;

    protected AuthInterface.Auth authApiInterface;
    protected MerchantInterface.Merchant merchantApiInterface;
    protected MyDesignAppointmentInterface myDesignAppointmentInterface;
    protected ProductInterface.Product productApiInterface;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        preferences = getActivity().getSharedPreferences(CONSTANT_PREF_FILE, Context.MODE_PRIVATE);

        authApiInterface  = ApiClient.getClient().create(AuthInterface.Auth.class);

        if (MySharedPreferences.getToken(preferences)!=null){
            merchantApiInterface  = ApiClient.getMerchantClient(MySharedPreferences.getToken(preferences)).create(MerchantInterface.Merchant.class);
            productApiInterface  = ApiClient.getProductClient(MySharedPreferences.getToken(preferences)).create(ProductInterface.Product.class);
        }

        //  setHasOptionsMenu(false);
    }
}
