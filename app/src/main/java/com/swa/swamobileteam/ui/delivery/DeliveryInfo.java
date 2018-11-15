package com.swa.swamobileteam.ui.delivery;

import com.swa.swamobileteam.data.deliveries.Address;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.DeliveryPeriod;
import com.swa.swamobileteam.data.deliveries.ParcelInfo;
import com.swa.swamobileteam.data.deliveries.User;

import java.util.List;

public final class DeliveryInfo {

    public DeliveryInfo(User user, DeliveryOrderStatus status, String description, Address addressTo,
                        Address addressFrom, int id, DeliveryPeriod period, List<ParcelInfo> parcels){
        this.customerInfo = user;
        this.deliveryStatus = status;
        this.description = description;
        this.addressTo =addressTo;
        this.addressFrom = addressFrom;
        this.id = id;
        this.deliveryPeriod = period;
        this.parcelsInfo = parcels;
    }

    /**
     * Customer first and last name, phone number
     */
    private User customerInfo;
    /**
     * Status of the order
     */
    private DeliveryOrderStatus deliveryStatus;
    /**
     * Description of the order - additional info
     */
    private String description;

    private Address addressTo;
    private Address addressFrom;

    /**
     * Priority of the order
     */
    private int priority;
    /**
     * If of the order
     */
    private int id;
    /**
     * Delivery period: start and end
     */
    private DeliveryPeriod deliveryPeriod;

    /**
     * Information about parcels being delivered.
     */
    private List<ParcelInfo> parcelsInfo;

    public DeliveryPeriod getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public DeliveryOrderStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public User getCustomerInfo() {
        return customerInfo;
    }

    public Address getAddressTo() {
        return addressTo;
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

    public List<ParcelInfo> getParcelsInfo() {
        return parcelsInfo;
    }
}
