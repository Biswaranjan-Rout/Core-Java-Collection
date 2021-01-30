package com.fashion.amai.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.adapter.AdapterMyWishlistTab2;
import com.fashion.amai.R;
import com.google.android.material.tabs.TabLayout;

public class WishListDesigns extends BaseFragment {
    private AdapterMyWishlistTab2 adapterMyWishlistTab2;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_2_my_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);
        adapterMyWishlistTab2 = new AdapterMyWishlistTab2(getChildFragmentManager());
        viewPager.setAdapter(adapterMyWishlistTab2);
        tabLayout.setupWithViewPager(viewPager);
    }

}
