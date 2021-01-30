package com.fashion.amai.utils;

import android.content.SharedPreferences;

import com.bumptech.glide.load.Key;


/**
 * Created by harish on 1/2/2017.
 */

public class MySharedPreferences {

    public static void wipe(SharedPreferences sharedPreferences) {

        sharedPreferences.edit().remove(PrefKeys.USER_ID).apply();
        sharedPreferences.edit().remove(PrefKeys.ACCESS_TOKEN).apply();
        // MySharedPreferences.storeValueInSharedPreferences(sharedPreferences, AMAZON_USER_ID, null);
    }

    public static void wipeToken(SharedPreferences sharedPreferences) {

    //    sharedPreferences.edit().remove(PrefKeys.USER_ID).apply();
        sharedPreferences.edit().remove(PrefKeys.ACCESS_TOKEN).apply();
        // MySharedPreferences.storeValueInSharedPreferences(sharedPreferences, AMAZON_USER_ID, null);
    }

    protected static void storeValueInSharedPreferences(SharedPreferences sharedPreferences, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected static void storeValueInSharedPreferences2(SharedPreferences sharedPreferences, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    protected static String getValueFromSharedPreferences(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.getString(key, null);
    }

    public static void registerUserId(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.USER_ID, userId);
    }

    public static void registerCartItem(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.CART, userId);
    }

    public static String getUserId(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.USER_ID);
    }

    public static void registerToken(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.ACCESS_TOKEN, userId);
    }

    public static void registerRefreshToken(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.REFRESH_TOKEN, userId);
    }

    public static void registerProfileImage(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.PROFILE_IMAGE, userId);
    }

    public static void registerAddress(SharedPreferences sharedPreferences, String address) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.USER_ADDRESS, address);
    }

    public static String getRefreshToken(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.REFRESH_TOKEN);
    }

    public static String getAddress(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.USER_ADDRESS);
    }

    public static String getToken(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.ACCESS_TOKEN);
    }

    public static void registerUsername(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.USERNAME, userId);
    }


    public static String getUsername(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.USERNAME);
    }

    public static String getProfileImage(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.PROFILE_IMAGE);
    }

    public static void registerPassword(SharedPreferences sharedPreferences, String userId) {
        MySharedPreferences.storeValueInSharedPreferences2(sharedPreferences, PrefKeys.PASSWORD, userId);
    }

    public static String getPassword(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.PASSWORD);
    }

    public static String getCart(SharedPreferences sharedPreferences) {
        return MySharedPreferences.getValueFromSharedPreferences(sharedPreferences, PrefKeys.CART);
    }

}
