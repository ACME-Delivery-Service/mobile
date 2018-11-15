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
    private int id;

    /**
     * Address where the PARCEL is mean to be delivered.
     */

    /**
     * A date period, during which the PARCEL should be delivered to its destination.
     */
    private DeliveryPeriod deliveryPeriod;

    private DeliveryOrderStatus deliveryStatus;

    private Address addressTo;
    private Address addressFrom;

    private Double eta;

    public DeliveriesListItem(int id, DeliveryPeriod deliveryPeriod, DeliveryOrderStatus status, Address addressTo, Address addressFrom) {
        this.id = id;
        this.deliveryPeriod = deliveryPeriod;
        this.deliveryStatus = status;
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
    }


    public int getId() {
        return id;
    }

    public DeliveryPeriod getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public DeliveryOrderStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public Address getAddressTo() {
        return addressTo;
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setEta(Double eta) {
        this.eta = eta;
    }
}
