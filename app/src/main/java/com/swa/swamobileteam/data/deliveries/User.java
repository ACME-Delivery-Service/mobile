package com.swa.swamobileteam.data.deliveries;

public class User {
    private int id;
    private HumanContacts contacts;

    public User(int id, HumanContacts contacts) {
        this.id = id;
        this.contacts = contacts;
    }

    public int getId() {
        return id;
    }

    public HumanContacts getContacts() {
        return contacts;
    }
}
