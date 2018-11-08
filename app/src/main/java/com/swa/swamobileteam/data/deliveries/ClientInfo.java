package com.swa.swamobileteam.data.deliveries;

/**
 * Information about a client a PARCEL is intended to be delivered to.
 */
public class ClientInfo {
    /**
     * FirstName of client
     */
    private String firstName;

    /**
     * LastName of client
     */
    private String lastName;

    /**
     * Phone number string with country code.
     */
    private String phoneNumber;

    public ClientInfo(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
