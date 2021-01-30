package com.fashion.amai.interfaces;

import android.util.Log;

import com.fashion.amai.model.AppointmentData;
import com.fashion.amai.model.GetAllProducts;
import com.fashion.amai.model.GetProductById;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.SelectDesignPojo;
import com.fashion.amai.utils.ApiEndpoints;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class ProductInterface {

    public interface Product{
        @GET(ApiEndpoints.GET_ALL_PRODUCTS)
        Call<List<GetAllProducts.ResponseObject>> getAllProducts();




        @GET(ApiEndpoints.PRODUCT_DETAILS)
        Call<GetProductById.ResponseObject> getProductById(@Path("PRODUCT_ID") String PRODUCT_ID);

        @GET(ApiEndpoints.MERCHANT_PRODUCT_BY_MERCHANT_ID)
        Call<MerchantProductsByMerchantID.Response> getMerchantProductsByMerchantID
                (@Path("MERCHANT_ID") String MERCHANT_ID,
                 @Query("type") String type,
                 @Query("page") String page,
                 @Query("per_page") String per_page);


    }
}
