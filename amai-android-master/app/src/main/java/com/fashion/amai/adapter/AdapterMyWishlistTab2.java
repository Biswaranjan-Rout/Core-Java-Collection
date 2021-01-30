package com.fashion.amai.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fashion.amai.fragment.WishListDesignTab1;
import com.fashion.amai.fragment.WishListDesignTab2;

public class AdapterMyWishlistTab2 extends FragmentStatePagerAdapter {
    public AdapterMyWishlistTab2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WishListDesignTab1 tab1 = new WishListDesignTab1();
                return tab1;
            case 1:
                WishListDesignTab2 tab2 = new WishListDesignTab2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Customized Designs";
            case 1:
                return "Catalogue Designs";
            default:
                return null;
        }
    }

}
