package com.swa.swamobileteam.data.deliveries;


import android.support.annotation.NonNull;

import com.swa.swamobileteam.ui.deliveryGroups.DeliveriesListItem;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * A repository that provides deliveries schedule.
 */
public interface DeliveriesListRepository {
    /**
     * Refreshes list of scheduled deliveries.
     */
    Single<Integer> refresh(@NonNull String token);

    /**
     * Returns delivery item given its index
     * @param index index of a delivery item to get
     * @return delivery item at given index
     */
    DeliveriesListItem getDeliveryListItem(int index);

    /**
     * Increases number of stored items limit in the repository and loads more items to that limit.
     * @param token API access token.
     * @return number of items, that were appended to the initial list of items before the call.
     */
    Single<Integer> loadDeliveries(@NonNull String token);

    /**
     * Updates the status of the desired delivery order.
     * @param deliveryID ID of the order to update.
     * @param newStatus Status to set.
     * @param token API Access token
     * @return Operation result (success / failure).
     */
    Completable updateDeliveryOrderStatus(@NonNull int deliveryID, @NonNull DeliveryOrderStatus newStatus, @NonNull String token);

    void updateItemETA(Double minutes, int index);
}
