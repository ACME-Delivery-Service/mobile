package com.swa.swamobileteam.data.deliveries;


import android.support.annotation.NonNull;

import com.swa.swamobileteam.transportApi.TransportApiClient;

import java.util.ArrayList;

import io.reactivex.Single;

public class DeliveryScheduleRepository extends AbstractDeliveriesListRepository {
    public DeliveryScheduleRepository(TransportApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public Single<Integer> refresh(@NonNull String token) {
        return apiClient.getSchedule(20, 0, token).flatMap(deliveryScheduleResponse -> {
            totalCount = deliveryScheduleResponse.getTotalCount();

            deliveriesListItems = new ArrayList<>();
            deliveriesListItems.addAll(deliveryScheduleResponse.getResults());

            return Single.just(deliveriesListItems.size());
        });
    }

    @Override
    public Single<Integer> loadDeliveries(@NonNull String token) {
        // Do not load more items than there are
        if (deliveriesListItems.size() >= totalCount)
            return Single.just(0);

        // load more items
        return apiClient.getSchedule(20, deliveriesListItems.size(), token).flatMap(deliveryScheduleResponse -> {
            deliveriesListItems.addAll(deliveryScheduleResponse.getResults());

            return Single.just(deliveryScheduleResponse.getResults().size());
        });
    }
}
