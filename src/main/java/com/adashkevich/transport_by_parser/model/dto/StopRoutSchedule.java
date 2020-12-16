package com.adashkevich.transport_by_parser.model.dto;

import com.google.gson.annotations.SerializedName;

public class StopRoutSchedule {

    @SerializedName(value = "StopId")
    String stopId;
    @SerializedName(value = "RouteId")
    String routId;

    public StopRoutSchedule() {}

    public StopRoutSchedule(String stopId, String routId) {
        this.stopId = stopId;
        this.routId = routId;
    }
}
