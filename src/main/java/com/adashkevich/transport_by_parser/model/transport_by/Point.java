package com.adashkevich.transport_by_parser.model.transport_by;

import com.google.gson.annotations.SerializedName;

public class Point {
    @SerializedName(value = "lat", alternate = "Latitude")
    public Double latitude;
    @SerializedName(value = "lon", alternate = "Longitude")
    public Double longitude;
}
