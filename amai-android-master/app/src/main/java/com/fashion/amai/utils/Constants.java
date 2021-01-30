package com.fashion.amai.utils;

/**
 * Project AwsCognitoTest
 * Created by jmc on 23/02/16.
 */
public interface Constants {

    public static final int COMMENT_TEXT_TYPE=0;
    public static final int COMMENT_IMAGE_TYPE=1;
    public static final int COMMENT_GIF_TYPE=2;


    String ADD_TO_CART_TABLE = "CART_TABLE";
    String CART_PRODUCT_NAME = "CART_PRODUCT_NAME";
    String CART_PRODUCT_PRICE = "CART_PRODUCT_PRICE";
    String CART_PRODUCT_QUANTITY = "CART_PRODUCT_QUANTITY";
    String CART_PRODUCT_IMAGE = "CART_PRODUCT_IMAGE";

    String SEND_ADDRESS = "SEND_ADDRESS";

    String MERCHANT_ID = "MERCHANT_ID";
    String MEASUREMENT_ID = "MEASUREMENT_ID";
    String MERCHANT_NAME = "MERCHANT_NAME";
    String PRODUCT_ID = "PRODUCT_ID";
    String IMAGE_URL = "IMAGE_URL";
    String MERCHANT_TYPE = "MERCHANT_TYPE";

    String BOUTIQUE = "boutique";
    String DESIGNER = "designer";
    String DESIGN_HOUSE = "design-house";
    String APPOINTMENT_ID = "APPOINTMENT_ID";
    String STATUS = "STATUS";
    String PRODUCT = "product";

    String READYMADE = "readymade";
    String PERSONALIZE = "personalize";

    String PERSONALISE_DESIGNERS = "PERSONALISE_DESIGNERS";


   // website link String USERS_API = "http://amai-web.antino.io:4001";
    String USERS_API = "http://ec2-3-136-240-99.us-east-2.compute.amazonaws.com:4001";
    String PAYMENTS_API = "http://ec2-3-136-240-99.us-east-2.compute.amazonaws.com:4004";
   // String USERS_API = "http://192.168.0.139:3000";
  //  String PRODUCTS_API = "http://amai-web.antino.io:4002";
    String PRODUCTS_API = "http://ec2-3-136-240-99.us-east-2.compute.amazonaws.com:4002";

    String CONSTANT_PREF_FILE = "constant_prefs.xml";

    int BOOKED = 1;
    int RESCHEDULED = 2;
    int CANCELED = 3;
    int RESOLVED = 4;

    String WISHLIST_ALL = "all";
    String WISHLIST_BOUTIQUE = "boutique";
    String WISHLIST_DESIGNER = "designer";
    String WISHLIST_PRODUCT = "product";
    String WISHLIST_DESIGN_HOUSE = "design_house";

    String BOOKED_APPOINTMENT = "booked";
    String CANCEL_APPOINTMENT = "cancelled";
    String COMPLETED_APPOINTMENT = "completed";



    static final int SUCCESS_RESULT = 0;

    static final int FAILURE_RESULT = 1;

    String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";

    static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";


    String GRID_VIEW_POSITION = "GRID_VIEW_POSITION";
}
