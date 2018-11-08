package com.swa.swamobileteam.data.deliveries;

import com.google.gson.annotations.SerializedName;

/**
 * Status of a delivery order.
 */
public enum DeliveryOrderStatus {
    /**
     * Order that was marked as being in process by the driver.
     * For such orders, time is being tracked on the backend.
     */
    @SerializedName("in_progress")
    IN_PROCESS,

    /**
     * Order that is to be executed by the driver.
     */
    @SerializedName("pending")
    PENDING,

    /**
     * Order that was marked as finished by the driver.
     */
    @SerializedName("finished")
    FINISHED
}
