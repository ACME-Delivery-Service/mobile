package com.swa.swamobileteam.ui.deliveryGroups;

import com.swa.swamobileteam.data.deliveries.Address;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.DeliveryPeriod;
import com.swa.swamobileteam.data.deliveries.ParcelInfo;

import java.util.List;

/**
 * A single item in the delivery schedule list.
 */
public class DeliveriesListItem {
    /**
     * Unique identification number string of the PARCEL to deliver.
     */
    private String id;

    /**
     * Address where the PARCEL is mean to be delivered.
     */
    // TODO: remove
    private Address address;

    /**
     * A date period, during which the PARCEL should be delivered to its destination.
     */
    private DeliveryPeriod deliveryPeriod;

    /**
     * A flag indicating whether the delivery is marked as in progress.
     */
    // TODO: remove
    private Boolean isInProgress;

    private DeliveryOrderStatus deliveryStatus;
    private Address addressTo;
    private Address addressFrom;

    /**
     * Weight in kg of the PARCEL to deliver.
     */
    // TODO: remove
    private Double weight;

    /**
     * Priority of the delivery.
     */
    private int priority;

    public DeliveriesListItem(String id, Address address, DeliveryPeriod deliveryPeriod, Boolean isInProgress, Double weight) {
        this.id = id;
        this.address = address;
        this.deliveryPeriod = deliveryPeriod;
        this.isInProgress = isInProgress;
        this.weight = weight;
    }

    public int getPriority() { return priority; }

    public String getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public DeliveryPeriod getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public Double getWeight() {
        return weight;
    }

    public Boolean getInProgress() {
        return isInProgress;
    }
}
