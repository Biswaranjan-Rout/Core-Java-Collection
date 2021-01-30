package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fashion.amai.R;
import com.fashion.amai.utils.Constants;

public class ImagePreviewActivity extends AppCompatActivity {
    private ImageView imgView;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_image_preview);


        Intent intent  = getIntent();
        imageUrl   = intent.getStringExtra(Constants.IMAGE_URL);
       // inti view
        intiView();

    }

    //@SuppressLint("ClickableViewAccessibility")
    private void intiView() {
        imgView = findViewById(R.id.img_preview);

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .placeholder(Drawable.createFromPath(getString(R.string.glide_placeholder)))
                .into(imgView);

    }

}
