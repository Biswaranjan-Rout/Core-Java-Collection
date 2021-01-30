package com.fashion.amai.model;

import android.graphics.drawable.Drawable;

public class ModelMenuTopHome {
    private Drawable itemImage;
    private String itemTitle;
    private int id;

    public ModelMenuTopHome(Drawable itemImage, String itemTitle, int id) {
        this.itemImage = itemImage;
        this.itemTitle = itemTitle;
        this.id = id;
    }

    public Drawable getItemImage() {
        return itemImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getId() {
        return id;
    }
}

