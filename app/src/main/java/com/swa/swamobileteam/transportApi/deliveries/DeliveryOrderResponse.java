package com.swa.swamobileteam.transportApi.deliveries;

import com.swa.swamobileteam.data.deliveries.Address;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.DeliveryPeriod;
import com.swa.swamobileteam.data.deliveries.HumanContacts;
import com.swa.swamobileteam.data.deliveries.ParcelInfo;
import com.swa.swamobileteam.data.deliveries.User;

import java.util.List;

/**
 * Model which describes while order
 */
public class DeliveryOrderResponse {
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
    /**
     * Address in standard format: String representation and Location
     */
    // TODO: remove
    private Address address;

    private Address addressTo;
    private Address addressFrom;
    /**
     * Priority of the order
     */
    private int priority;
    /**
     * If of the order
     */
    private String id;
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

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public Address getAddress() {
        return address;
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
