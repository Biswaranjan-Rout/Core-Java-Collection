package com.fashion.amai.interfaces;

import com.fashion.amai.model.ActivitySchedulePojo;
import com.fashion.amai.model.AddToCartPojo;
import com.fashion.amai.model.AddedToCheckoutPojo;
import com.fashion.amai.model.AppointmentData;
import com.fashion.amai.model.BookAppointment;
import com.fashion.amai.model.CartPojo;
import com.fashion.amai.model.CheckedOutPojo;
import com.fashion.amai.model.ClearCheckoutPojo;
import com.fashion.amai.model.ClearSelectedDesignsPojo;
import com.fashion.amai.model.DetailsPojo;
import com.fashion.amai.model.GetStoreDetail;
import com.fashion.amai.model.MeasurementPojo;
import com.fashion.amai.model.MeasurementPojoResponse;
import com.fashion.amai.model.MerchantData;
import com.fashion.amai.model.MerchantProductsByMerchantID;
import com.fashion.amai.model.MyDesignsModel;
import com.fashion.amai.model.MyWishListDesigns;
import com.fashion.amai.model.Profilepojo;
import com.fashion.amai.model.RefreshTokenPojo;
import com.fashion.amai.model.SelectDesignPojo;
import com.fashion.amai.model.UnSelectPojo;
import com.fashion.amai.model.UpdateProfilePojo;
import com.fashion.amai.model.WishlistPojo;
import com.fashion.amai.utils.ApiEndpoints;
import com.fashion.amai.utils.Checksum;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MerchantInterface {

    public interface Merchant{

        @FormUrlEncoded
        @POST("generateChecksum.php")
        Call<Checksum> getChecksum(
                @Field("MID") String mId,
                @Field("ORDER_ID") String orderId,
                @Field("CUST_ID") String custId,
                @Field("CHANNEL_ID") String channelId,
                @Field("TXN_AMOUNT") String txnAmount,
                @Field("WEBSITE") String website,
                @Field("CALLBACK_URL") String callbackUrl,
                @Field("INDUSTRY_TYPE_ID") String industryTypeId
        );

        @GET("api/v1/boutiques/store-detail")
        Call<List<GetStoreDetail.ResponseObject>> getBoutiqueStoreDetail(@Header("Authorization") String authToken);

        @GET(ApiEndpoints.GET_WISHLIST)
        Call<MyWishListDesigns> getUserWishlist(
                @Query("type") String wishlistType);


        @GET(ApiEndpoints.GET_WISHLIST_TYPE)
        Call<MyWishListDesigns> getUserWishlistProduct(
                @Query("type") String wishlistType,
                @Query("product_type") String product_type);

        /*@GET(ApiEndpoints.GET_MY_DESIGNS)
        Call<MyDesignsModel> getMyDesigns(@Query("status" )String bookingStatus);*/

        @GET(ApiEndpoints.GET_MY_DESIGNS)
        Call<MyDesignsModel> getMyDesigns(@Query("status") String status);

        @GET(ApiEndpoints.GET_USER_PROFILE)
        Call<Profilepojo> getUserProfile();

        @Headers("Accept: multipart/form-data, " +
                "Content-Type: application/json")
        @Multipart
        @POST(ApiEndpoints.GET_USER_PROFILE)
        Call<UpdateProfilePojo> sendUserDetails(@Part MultipartBody.Part file);

        @GET("api/v1/merchants/")
        Call<MerchantData> getMerchantByType(@Query("type") String merchantType, @Query("page") String page, @Query("per_page") String per_page);

        @GET(ApiEndpoints.GET_MERCHANT_BY_ID)
        Call<DetailsPojo> getMerchantById(@Path("ID") String id);

        @GET(ApiEndpoints.GET_ALL_WISHLIST)
        Call<WishlistPojo> getAllWishlist(@Path("ID") String id);

        @GET(ApiEndpoints.UPDATE_APPOINTMENT)
        Call<BookAppointment> getAllAppointments(@Query("status") String status);

        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.ADD_APPOINTMENT)
        Call<AppointmentData> addAppointment(@Body JsonObject object);


        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.ADD_APPOINTMENT)
        Call<ActivitySchedulePojo> addAppointmentTime(@Body JsonObject object);

        // todo add measurment pending
        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.ADD_TO_MEASUREMENT)
        Call<MeasurementPojo> addMeasurement(@Body JsonObject object);

        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.REFRESH_TOKEN)
        Call<RefreshTokenPojo> getRefreshToken(@Body JsonObject object);


        @GET(ApiEndpoints.ADD_TO_MEASUREMENT)
        Call<MeasurementPojoResponse> getMeasurementList();

        @GET(ApiEndpoints.GET_MEASUREMENT)
        Call<MeasurementPojo> getMeasurementSingle(@Path("id") int id);



        @Headers("Content-Type: application/json")
        @PUT(ApiEndpoints.ADD_APPOINTMENT)
        Call<ClearSelectedDesignsPojo> updateAppointmentTime(@Body JsonObject object);

        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.SELECT_DESIGN)
        Call<SelectDesignPojo> selectDesign(@Body JsonObject object);

        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.CHECKOUT_DESIGNS)
        Call<AddedToCheckoutPojo> checkout_designs(@Body JsonObject object);

        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.ADD_TO_CART)
        Call<AddToCartPojo> addToCart(@Body JsonObject object);

        @GET(ApiEndpoints.CHECKOUT_DESIGNS)
        Call<CheckedOutPojo> get_checkout_designs();

        @GET(ApiEndpoints.ADD_TO_CART)
        Call<CartPojo> get_add_to_cart();

        @Headers("Content-Type: application/json")
        @POST(ApiEndpoints.ADD_TO_WISHLIST)
        Call<WishlistPojo> addToWishlist(@Body JsonObject wishlist);

        @DELETE(ApiEndpoints.UNSELECT_SINGLE_PRODUCT)
        Call<UnSelectPojo> unselectproduct(@Path("PRODUCT_ID") String PRODUCT_ID);

        @DELETE(ApiEndpoints.DELETE_WISHLIST)
        Call<ClearSelectedDesignsPojo> deleteWishList(@Query("type") String type, @Query("id") int id);

        @DELETE(ApiEndpoints.DELETE_CHECKOUT)
        Call<ClearCheckoutPojo> deleteCheckout(@Path("ID")  int id);

        @DELETE(ApiEndpoints.CLEAR_DESIGNS)
        Call<ClearSelectedDesignsPojo> clearDesigns();

        @PUT(ApiEndpoints.UPDATE_APPOINTMENT)
        Call<WishlistPojo> updateAppointment(@Path("ID") String id);

    }
}
