package com.swa.swamobileteam.transportApi;

import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.transportApi.authentication.LoginResponse;
import com.swa.swamobileteam.transportApi.authentication.LoginRequestParams;
import com.swa.swamobileteam.transportApi.controlOperator.ControlOperatorResponse;
import com.swa.swamobileteam.transportApi.deliveries.DeliveriesParams;
import com.swa.swamobileteam.transportApi.deliveries.DeliveryOrderResponse;
import com.swa.swamobileteam.transportApi.deliveries.DeliveryScheduleResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransportApi {
    @POST("account/login")
    Single<LoginResponse> login(@Body LoginRequestParams request);

    @POST("account/logout")
    Completable logout(@Header("Authorization") String token);

    @GET("drivers/pending")
    Single<DeliveryScheduleResponse> getSchedule(@Body DeliveriesParams request,
                                                 @Header("Authorization") String token);

    @GET("drivers/current")
    Single<DeliveryScheduleResponse> getInProgress(@Body DeliveriesParams request,
                                                   @Header("Authorization") String token);

    @GET("order/{id}/info")
    Single<DeliveryOrderResponse> getDeliveryOrderInfo(@Path("id") Integer orderID,
                                                       @Header("Authorization") String token);

    @GET("drivers/co_contact")
    Single<ControlOperatorResponse> getControlOperatorContact(@Header("Authorization") String token);

    @POST("drivers/location")
    Completable sendLocation(@Body Location location, @Header("Authorization") String token);
}
