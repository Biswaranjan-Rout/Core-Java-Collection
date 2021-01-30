package com.fashion.amai.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fashion.amai.R;
import com.fashion.amai.fragment.PersonalizeFragment;
import com.fashion.amai.utils.DataKeys;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class PersonalizeSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public PersonalizeSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return PersonalizeFragment.newInstance(DataKeys.MERCHANT_BOUTIQUE);
            case 1:
                return PersonalizeFragment.newInstance(DataKeys.MERCHANT_DESIGNER);
            case 2:
                return PersonalizeFragment.newInstance(DataKeys.MERCHANT_DESIGN_HOUSES);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}