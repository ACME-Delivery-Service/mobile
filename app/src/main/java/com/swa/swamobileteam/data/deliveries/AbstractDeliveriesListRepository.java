package com.swa.swamobileteam.data.deliveries;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.transportApi.TransportApiClient;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveriesListItem;

import java.util.ArrayList;

import io.reactivex.Completable;

abstract class AbstractDeliveriesListRepository implements DeliveriesListRepository {
    ArrayList<DeliveriesListItem> deliveriesListItems;
    int totalCount;

    TransportApiClient apiClient;

    AbstractDeliveriesListRepository(TransportApiClient apiClient) {
        deliveriesListItems = new ArrayList<>();
        totalCount = 0;
        this.apiClient = apiClient;
    }

    @Override
    public Completable updateDeliveryOrderStatus(@NonNull int deliveryID, @NonNull DeliveryOrderStatus newStatus, @NonNull String token) {
        return apiClient.submitNewDeliveryOrderStatus(deliveryID, newStatus, token);
    }

    @Override
    public DeliveriesListItem getDeliveryListItem(int index) {
        return deliveriesListItems.get(index);
    }
}
