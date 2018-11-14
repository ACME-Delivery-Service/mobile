package com.swa.swamobileteam.data.deliveries;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.transportApi.TransportApiClient;
import com.swa.swamobileteam.ui.delivery.DeliveryInfo;

import io.reactivex.Single;

public class DeliveryDetailsRepositoryImpl implements DeliveryDetailsRepository {
    private TransportApiClient apiClient;

    public DeliveryDetailsRepositoryImpl(TransportApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Single<DeliveryInfo> getDeliveryInfo(int deliveryID, @NonNull String token) {
        return apiClient.getDeliveryOrderInfo(deliveryID, token);
    }

    @Override
    public Double getRemainingTime(DeliveryPeriod period) {
        return 10.0;
    }
}
