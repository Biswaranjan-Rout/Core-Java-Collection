package com.fashion.amai.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyDesignAppointmentInterface {

    @GET ("api/v1/me/appointments?status={booked}")
    Call<MyDesignAppointmentInterface> getAppointmentStatus( @Path("status") String status );

}
