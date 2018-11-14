package com.swa.swamobileteam.ui.delivery;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.data.deliveries.DeliveryDetailsRepository;
import com.swa.swamobileteam.data.deliveries.DeliveryDetailsRepositoryImpl;
import com.swa.swamobileteam.data.deliveries.DeliveryPeriod;
import com.swa.swamobileteam.transportApi.TransportApiClient;

import javax.inject.Inject;

import io.reactivex.Single;

public class DeliveryModel implements DeliveryContract.Model {
    private DeliveryDetailsRepository deliveryDetailsRepository;

    @Inject
    public DeliveryModel(DeliveryDetailsRepository repository) {
        this.deliveryDetailsRepository = repository;
    }

    @Override
    public Single<DeliveryInfo> getDeliveryInfo(int deliveryID, @NonNull String token) {
        return deliveryDetailsRepository.getDeliveryInfo(deliveryID, token);
    }

    @Override
    public Double getRemainingTime(DeliveryPeriod period) {
        return deliveryDetailsRepository.getRemainingTime(period);
    }
}
