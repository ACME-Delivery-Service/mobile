package com.swa.swamobileteam.data.deliveries;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.ui.delivery.DeliveryInfo;

import io.reactivex.Single;

public interface DeliveryDetailsRepository {
    /**
     * Retrieves derailed information about a delivery.
     * @param deliveryID Identifier of the delivery to retrieve info for.
     */
    Single<DeliveryInfo> getDeliveryInfo(int deliveryID, @NonNull String token);

    /**
     * Calculates how much time is left to finish the delivery in seconds.
     * @param period Delivery period of the delivery.
     */
    Double getRemainingTime(DeliveryPeriod period);
}
