package com.fashion.amai.utils;

public final class ApiEndpoints {

    public static final String user="";
    //getMerchantProductsByMerchantID
    public static final String REGISTER =  "api/v1/register";
    public static final String MERCHANT_PRODUCT_BY_MERCHANT_ID =  "api/v1/merchants/{MERCHANT_ID}/products";
    public static final String PRODUCT_DETAILS =  "api/v1/products/{PRODUCT_ID}";
    public static final String SELECT_DESIGN =  "api/v1/me/selected-designs";
    public static final String GET_ALL_PRODUCTS =  "api/v1/products";
    public static final String ADD_APPOINTMENT =  "api/v1/me/appointments";
    public static final String UNSELECT_SINGLE_PRODUCT =  "api/v1/me/selected-designs/{PRODUCT_ID}";
    public static final String CLEAR_DESIGNS =  "api/v1/me/selected-designs";
    public static final String ADD_TO_WISHLIST =  "api/v1/me/wishlists";

    public static final String FORGOT_PASSWORD="api/v1/users/"+user+"/reset-password";

    public static final String GET_MERCHANT_BY_ID =  "api/v1/merchants/{ID}";

    public static final String GET_ALL_WISHLIST =  "api/v1/me/wishlists/?type={ID}";
    public static final String UPDATE_APPOINTMENT =  "api/v1/me/appointments";
    public static final String GET_WISHLIST =  "api/v1/me/wishlists/";
    public static final String GET_WISHLIST_TYPE =  "api/v1/me/wishlists/";

    public static final String GET_MY_DESIGNS =  "api/v1/me/designs";
    public static final String DELETE_WISHLIST =  "api/v1/me/wishlists/";
    public static final String DELETE_CHECKOUT =  "api/v1/me/checkedout-designs/{ID}";
    public static final String CHECKOUT_DESIGNS =  "api/v1/me/checkedout-designs";
    public static final String ADD_TO_MEASUREMENT =  "api/v1/me/measurements";
    public static final String GET_MEASUREMENT =  "api/v1/me/measurements/{id}";
    public static final String GET_USER_PROFILE =  "api/v1/me/personal-detail";
    public static final String REFRESH_TOKEN =  "api/v1/token";

    public static final String ADD_TO_CART =  "api/v1/me/carts";
    public static final String GET_HASH =  "api/v1/checksum/generate";

}
