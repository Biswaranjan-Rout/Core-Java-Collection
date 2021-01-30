package com.fashion.amai.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fashion.amai.fragment.MyAppointmentFragment;
import com.fashion.amai.fragment.WishListAll;
import com.fashion.amai.fragment.WishListDesigns;
import com.fashion.amai.utils.Constants;

public class AdapterMyAppointmentTabs extends FragmentStatePagerAdapter {

    public AdapterMyAppointmentTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

     /*   String WISHLIST_BOUTIQUE = "boutique"; done
        String WISHLIST_DESIGNER = "designer"; done
        String WISHLIST_PRODUCT = "product";
        String WISHLIST_DESIGN_HOUSE = "design_house"; */ //done

        switch (position) {
            case 0:
                MyAppointmentFragment tab1 =  MyAppointmentFragment.newInstance(Constants.BOOKED_APPOINTMENT); // Booked
                return tab1;
            case 1:
                //WishListAll tab2 =  WishListAll.newInstance(Constants.WISHLIST_PRODUCT);

                MyAppointmentFragment tab2 =  MyAppointmentFragment.newInstance(Constants.CANCEL_APPOINTMENT); //canceled
                return tab2;
            case 2:

                MyAppointmentFragment tab3 =  MyAppointmentFragment.newInstance(Constants.COMPLETED_APPOINTMENT); //complete

              //  WishListBoutiques tab3 = new WishListBoutiques();
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
                return "Booked";
            case 1:
                return "Cancelled";
            case 2:
                return "Completed";
            default:
                return null;
        }
    }

}
