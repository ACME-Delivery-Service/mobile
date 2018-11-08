package com.swa.swamobileteam.utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public enum DeliveryType {
    New(0, "New tasks"), InProgress(1, "In progress");

    private static SparseArray<DeliveryType> map = new SparseArray<>(5);
    private static List<String> names = new ArrayList<>(5);

    private int id;
    private String name;
    static {
        for (DeliveryType type : DeliveryType.values()) {
            map.put(type.id, type);
            names.add(type.toString());
        }
    }

    DeliveryType(int typeid, String name){
        this.id = typeid;
        this.name = name;
    }

    public static DeliveryType valueOf(int id) {
        return map.get(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }
}