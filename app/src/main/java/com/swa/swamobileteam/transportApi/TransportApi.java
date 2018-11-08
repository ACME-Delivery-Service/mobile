package com.swa.swamobileteam.transportApi;

import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.data.deliveries.User;
import com.swa.swamobileteam.transportApi.authentication.LoginResponse;
import com.swa.swamobileteam.transportApi.authentication.LoginRequestParams;
import com.swa.swamobileteam.transportApi.deliveries.DeliveryOrderResponse;
import com.swa.swamobileteam.transportApi.deliveries.DeliveryScheduleResponse;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransportApi {
    @POST("account/login")
    Single<LoginResponse> login(@Body LoginRequestParams request);

    @POST("account/logout")
    Completable logout(@Header("Authorization") String token);

    @GET("driver/pending")
    Single<DeliveryScheduleResponse> getSchedule(@Query("limit") int limit,
                                                 @Query("offset") int offset,
                                                 @Header("Authorization") String token);

    @GET("driver/current")
    Single<DeliveryScheduleResponse> getInProgress(@Query("limit") int limit,
                                                   @Query("offset") int offset,
                                                   @Header("Authorization") String token);

    @GET("order/{id}/info")
    Single<DeliveryOrderResponse> getDeliveryOrderInfo(@Path("id") int orderID,
                                                       @Header("Authorization") String token);

    @POST("order/{id}/status")
    Completable submitNewDeliveryOrderStatus(@Path("id") int orderID,
                                             @Header("Authorization") String token);

    @GET("driver/co_contact")
    Single<User> getControlOperatorContact(@Header("Authorization") String token);

    @POST("driver/location")
    Completable sendLocation(@Body Location location, @Header("Authorization") String token);
}
