package com.swa.swamobileteam.data.deliveries;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.transportApi.TransportApiClient;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveriesListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class InProgressDeliveriesRepository extends AbstractDeliveriesListRepository {
    public InProgressDeliveriesRepository(TransportApiClient apiClient) {
        super(apiClient);
    }

    @Override
    public Single<Integer> refresh(@NonNull String token) {
        return apiClient.getInProgress(20, 0, token).flatMap(deliveryScheduleResponse -> {
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
        return apiClient.getInProgress(20, deliveriesListItems.size(), token).flatMap(deliveryScheduleResponse -> {
            deliveriesListItems.addAll(deliveryScheduleResponse.getResults());

            return Single.just(deliveryScheduleResponse.getResults().size());
        });
    }

    @Override
    public DeliveriesListItem getDeliveryListItem(int index) {
        return deliveriesListItems.get(index);
    }
}
