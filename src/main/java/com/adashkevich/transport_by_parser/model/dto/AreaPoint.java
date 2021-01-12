package com.adashkevich.transport_by_parser.model.dto;

import com.google.gson.annotations.SerializedName;

public class AreaPoint {
    @SerializedName(value = "Latitude")
    public Double latitude;
    @SerializedName(value = "Longitude")
    public Double longitude;

    public AreaPoint() {}

    public AreaPoint(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
