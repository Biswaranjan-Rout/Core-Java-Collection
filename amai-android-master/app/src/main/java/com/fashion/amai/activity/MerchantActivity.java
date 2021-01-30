package com.fashion.amai.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.adapter.PersonalizeSectionsPagerAdapter;
import com.fashion.amai.R;
import com.fashion.amai.fragment.GenderBottomDialogFragment;
import com.fashion.amai.fragment.SortBottomDialogFragment;
import com.fashion.amai.utils.MenuConverter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

public class MerchantActivity extends BaseActivity implements View.OnClickListener, GenderBottomDialogFragment.ItemClickListener, SortBottomDialogFragment.ItemClickListener {
   // private ImageView ivBack, ivSearch, ivWishList, ivCart;
    private PersonalizeSectionsPagerAdapter personalizeSectionsPagerAdapter;
    private BottomSheetDialog bottomSheetDialog;
   // private RadioButton radioButtonMale, radioButtonFemale, radioButtonHighToLow, radioButtonLowToHigh, radioButtonPopularity, radioButtonDeliveryTime;
    private ViewPager viewPager;
    private TabLayout tabs;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalize);

        initTabLayouts();
        initViews();
        setOnClickListeners();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // toolbar.setNavigationIcon(R.drawable.icon_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("AMAI");

    }

    private void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTabLayouts() {
        personalizeSectionsPagerAdapter = new PersonalizeSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(personalizeSectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void initViews() {

        bottomSheetDialog = new BottomSheetDialog(this);

    }

    private void setOnClickListeners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_search: {

                break;
            }
            case R.id.iv_wish_list: {

                break;
            }
            case R.id.iv_cart: {

                break;
            }
            case R.id.layout_gender: {

                GenderBottomDialogFragment addPhotoBottomDialogFragment = GenderBottomDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getSupportFragmentManager(), GenderBottomDialogFragment.TAG);


              /*  view = getLayoutInflater().inflate(R.layout.bottom_sheet_gender, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

               */
                break;
            }
            case R.id.layout_sort: {

                SortBottomDialogFragment addPhotoBottomDialogFragment = SortBottomDialogFragment.newInstance();
                addPhotoBottomDialogFragment.show(getSupportFragmentManager(), SortBottomDialogFragment.TAG);


             /*   view = getLayoutInflater().inflate(R.layout.bottom_sheet_sort, null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

              */
                break;
            }
            case R.id.layout_filter: {
                Intent intentActivityFilters = new Intent(MerchantActivity.this, ActivityFilters.class);
                startActivity(intentActivityFilters);
                break;
            }
            case R.id.radio_btn_male:{
                Toast.makeText(this, "Male", Toast.LENGTH_SHORT).show();

                break;

            } case R.id.radio_btn_female:{
                Toast.makeText(this, "Female", Toast.LENGTH_SHORT).show();

                break;

            }


        }
    }

    @Override
    public void onItemClick(String item) {
        
        Toast.makeText(this, "Merchant Page " + item, Toast.LENGTH_LONG).show();
    }
}