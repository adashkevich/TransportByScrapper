package com.adashkevich.transport_by_parser.model.dto;

import com.google.gson.annotations.SerializedName;

public class StopRouts {
    @SerializedName(value = "StopId")
    String stopId;
    @SerializedName(value = "Types")
    String types;

    public StopRouts() {}

    public StopRouts(String stopId) {
        this.stopId = stopId;
    }
}
