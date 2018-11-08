package com.swa.swamobileteam.transportApi.deliveries;

import com.swa.swamobileteam.ui.deliveryGroups.DeliveriesListItem;

import java.util.List;

public class DeliveryScheduleResponse {
    /**
     * Delivery schedules
     */
    private List<DeliveriesListItem> results;

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public List<DeliveriesListItem> getResults() {
        return results;
    }
}
