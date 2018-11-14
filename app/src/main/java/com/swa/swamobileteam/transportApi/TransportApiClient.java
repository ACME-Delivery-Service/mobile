package com.swa.swamobileteam.transportApi;

import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.data.deliveries.User;
import com.swa.swamobileteam.transportApi.authentication.LoginRequestParams;
import com.swa.swamobileteam.transportApi.authentication.LoginResponse;
import com.swa.swamobileteam.transportApi.deliveries.DeliveryScheduleResponse;
import com.swa.swamobileteam.ui.delivery.DeliveryInfo;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class TransportApiClient {
    private TransportApi api;

    public TransportApiClient(Retrofit retrofit) {
        api = retrofit.create(TransportApi.class);
    }

    public Single<LoginResponse> login(LoginRequestParams request) {
        return api.login(request);
    }

    public Completable logout(String token) {
        return api.logout(token);
    }

    public Single<DeliveryScheduleResponse> getSchedule(int limit, int offset, String token) {
        return api.getSchedule(limit, offset, token);
    }

    public Single<DeliveryScheduleResponse> getInProgress(int limit, int offset, String token) {
        return api.getInProgress(limit, offset, token);
    }

    public Single<DeliveryInfo> getDeliveryOrderInfo(int orderID, String token) {
        return api.getDeliveryOrderInfo(orderID, token);
    }

    public Completable submitNewDeliveryOrderStatus(int orderID, DeliveryOrderStatus newStatus, String token) {
        HashMap<String, DeliveryOrderStatus> statusBody = new HashMap<>();
        statusBody.put("status", newStatus);

        return api.submitNewDeliveryOrderStatus(orderID, statusBody, token);
    }

    public Single<User> getControlOperatorContact(String token) {
        return api.getControlOperatorContact(token);
    }

    public Completable sendLocation(Location location, String token) {
        return api.sendLocation(location, token);
    }
}
