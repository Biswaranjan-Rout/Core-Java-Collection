package com.fashion.amai.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.fashion.amai.fragment.WishListAll;
import com.fashion.amai.fragment.WishListDesigns;
import com.fashion.amai.utils.Constants;

public class AdapterMyWishlistTabs extends FragmentStatePagerAdapter {

    public AdapterMyWishlistTabs(FragmentManager fm) {
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
                WishListAll tab1 =  WishListAll.newInstance(Constants.WISHLIST_ALL); // All products
                return tab1;
            case 1:
                //WishListAll tab2 =  WishListAll.newInstance(Constants.WISHLIST_PRODUCT);

                WishListDesigns tab2 = new WishListDesigns();
                return tab2;
            case 2:
                WishListAll tab3 =  WishListAll.newInstance(Constants.WISHLIST_BOUTIQUE); //Boutique

              //  WishListBoutiques tab3 = new WishListBoutiques();
                return tab3;
            case 3:
                WishListAll tab4 =  WishListAll.newInstance(Constants.WISHLIST_DESIGNER); // Designer

              //  WishListDesigners tab4 = new WishListDesigners();
                return tab4;
            case 4:
                WishListAll tab5 =  WishListAll.newInstance(Constants.WISHLIST_DESIGN_HOUSE); // Designer house

               // WishListDesignHouse tab5 = new WishListDesignHouse();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Designs";
            case 2:
                return "Boutiques";
            case 3:
                return "Designers";
            case 4:
                return "Design Houses";
            default:
                return null;
        }
    }

}
