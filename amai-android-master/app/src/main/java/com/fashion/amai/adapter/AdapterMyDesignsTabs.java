package com.fashion.amai.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fashion.amai.fragment.MyDesignsAppointment;
import com.fashion.amai.fragment.MyDesignsCheckout;
import com.fashion.amai.fragment.MyDesignsConfirm;

public class AdapterMyDesignsTabs extends FragmentStatePagerAdapter {
    public AdapterMyDesignsTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyDesignsAppointment tab1 = new MyDesignsAppointment();
                return tab1;
            case 1:
                MyDesignsCheckout tab2 = new MyDesignsCheckout();
                return tab2;
            case 2:
                MyDesignsConfirm tab3 = new MyDesignsConfirm();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Designs for Appointment";
            case 1:
                return "Checkout Designs";
            case 2:
                return "Confirmed Designs";
            default:
                return null;
        }
    }

}
