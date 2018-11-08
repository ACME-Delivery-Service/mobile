package com.swa.swamobileteam.data.deliveries;

import com.google.gson.annotations.SerializedName;

public class ParcelInfo {

    private Double weight;
    private Dimensions dimensions;
    private String id;
    private Shape shape;
    private String description;

    public String getId() {
        return id;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum Shape {
        @SerializedName("letter")
        LETTER,

        @SerializedName("parcel")
        PARCEL,

        @SerializedName("large_envelope")
        LARGE_ENVELOPE,

        @SerializedName("postcard")
        POSTCARD
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Information about PARCEL's size in meters.
     */
    public static class Dimensions {
        private Double x;
        private Double y;
        private Double z;

        public Dimensions(Double x, Double y, Double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Double getX() {
            return x;
        }

        public Double getY() {
            return y;
        }

        public Double getZ() {
            return z;
        }
    }


    public ParcelInfo(Double weight, Dimensions dimensions) {
        this.weight = weight;
        this.dimensions = dimensions;
    }

    public Double getWeight() {
        return weight;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
