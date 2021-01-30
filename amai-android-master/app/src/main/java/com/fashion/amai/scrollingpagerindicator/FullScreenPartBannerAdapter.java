package com.fashion.amai.scrollingpagerindicator;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.fashion.amai.R;
import com.fashion.amai.activity.ActivityOurDesignsProduct;
import com.fashion.amai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita Olifer
 */
public class FullScreenPartBannerAdapter extends PagerAdapter {
    private List<Integer> images;
    private int pageCount;

    public FullScreenPartBannerAdapter(int pageCount) {
        images = new ArrayList<>();
        this.pageCount = pageCount;
    }

    public void addAll(List<Integer> listPages) {
        for (Integer result : listPages) {
            add(result);
            notifyDataSetChanged();
        }
    }

    public void add(Integer r) {
        images.add(r);

    }
    public void setCount(int count) {
        this.pageCount = count;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(collection.getContext())
                .inflate(R.layout.full_screen_page_2parts, collection, false);

        ImageView label = layout.findViewById(R.id.demo_page_label);
        ImageView label2 = layout.findViewById(R.id.demo_page_label2);

        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsProduct.class);
                intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Designer Name");
                intentActivityProducts.putExtra(Constants.MERCHANT_ID, 11);
                intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique");
                v.getContext().startActivity(intentActivityProducts);
            }
        });
        label2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActivityProducts = new Intent(v.getContext(), ActivityOurDesignsProduct.class);
                intentActivityProducts.putExtra(Constants.MERCHANT_NAME, "Designer Name");
                intentActivityProducts.putExtra(Constants.MERCHANT_ID, 95);
                intentActivityProducts.putExtra(Constants.IMAGE_URL, "https://amaifilestorage123.s3.amazonaws.com/be8fa17bc3b466092f4640c0e8333be3");
                intentActivityProducts.putExtra(Constants.MERCHANT_TYPE, "boutique");
                v.getContext().startActivity(intentActivityProducts);
            }
        });


        label.setImageDrawable(layout.getResources().getDrawable(images.get(position)));
//        label.setText(String.valueOf(position));
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {

        return images == null ? 0 : images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }
}
