package com.fashion.amai.model;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Movie {
    private String title, year;
    private int image_url;

    public Movie() {
    }

    public Movie(String title, int image_url, String year) {
        this.title = title;
        this.image_url = image_url;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }
}
