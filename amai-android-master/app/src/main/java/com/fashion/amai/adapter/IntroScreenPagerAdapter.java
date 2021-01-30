package com.fashion.amai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fashion.amai.model.ModelProductDetail;
import com.fashion.amai.R;

import java.util.List;

public class IntroScreenPagerAdapter extends PagerAdapter {
    private Context ctx;
  private List<ModelProductDetail> list;
    private LayoutInflater layoutInflater;
    int [] listArr ;

    public IntroScreenPagerAdapter(Context context, List<ModelProductDetail> list , int [] listArr) {
        this.ctx = context;
        this.list = list;
        this.listArr = listArr;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.intro_view_pager, null);
        ImageView imageView = view.findViewById(R.id.image_profile_intro);
        TextView tvContent = view.findViewById(R.id.tv_intro_content);
        TextView tvTitle = view.findViewById(R.id.tv_title_intro);
       /* Glide.with(ctx) //1
                .load(String.valueOf(list.get(position).getImage()))
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3//4
                .into(imageView);*/

        imageView.setImageResource(listArr[position]);
        tvContent.setText(list.get(position).getProductContent());
        tvTitle.setText(list.get(position).getProductDes());
        ViewPager viewPager = (ViewPager) collection;
        viewPager.addView(view);
       /* if(viewPager.getCurrentItem()>6){
            ctx.startActivity(new Intent(ctx, AuthActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }*/
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}