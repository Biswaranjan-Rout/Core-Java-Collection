package com.fashion.amai.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fashion.amai.activity.ImagePreviewActivity;
import com.fashion.amai.R;
import com.fashion.amai.interfaces.MerchantInterface;
import com.fashion.amai.model.DetailsPojo;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.utils.Constants;

import java.util.List;

public class MerchantDetailsAdapter extends PagerAdapter {
    private Context ctx;
    private DetailsPojo data;
    private LayoutInflater layoutInflater;
    private List<String> strings;

    public MerchantDetailsAdapter(Context context, List<String> strings) {
        this.ctx = context;
        this.strings = strings;
    }

    public void addAll(DetailsPojo data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.user_image_view_pager, null );
        ImageView imageView =  view.findViewById( R.id.image_profile );



        Glide.with(ctx)
                .load(strings.get(position))
                .fitCenter()
                .placeholder(new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.white)))
                .into(imageView);

        //setOnTouch(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ImagePreviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Constants.IMAGE_URL, strings.get(position));
                ctx.startActivity(intent);

            }
        });

      //  setOnTouch(imageView , position);
        ViewPager viewPager = (ViewPager) collection;
        viewPager.addView( view  );
        return view;
    }

    private void setOnTouch(ImageView imageView, int position) {



    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
