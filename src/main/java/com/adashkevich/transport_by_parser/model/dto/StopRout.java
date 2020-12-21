package com.adashkevich.transport_by_parser.model.dto;

import com.google.gson.annotations.SerializedName;

public class StopRout {

    @SerializedName(value = "RouteId")
    String routId;
    @SerializedName(value = "StopId")
    String stopId;
    @SerializedName(value = "Types")
    String types;

    public StopRout() {}

    public StopRout(String stopId) {
        this.stopId = stopId;
    }

    public StopRout setRoutId(String routId) {
        this.routId = routId;
        return this;
    }

    public StopRout setStopId(String stopId) {
        this.stopId = stopId;
        return this;
    }

    public StopRout setTypes(String types) {
        this.types = types;
        return this;
    }
}
