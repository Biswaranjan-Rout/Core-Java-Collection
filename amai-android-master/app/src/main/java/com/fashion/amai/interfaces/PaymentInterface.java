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

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
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

public interface PaymentInterface {

    @Headers("Content-Type: application/json")
    @POST(ApiEndpoints.GET_HASH)
    Call<Checksum> getHash(@Body JsonObject object);


}
