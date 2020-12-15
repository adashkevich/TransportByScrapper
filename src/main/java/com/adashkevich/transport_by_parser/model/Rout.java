package com.adashkevich.transport_by_parser.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Rout {

    @SerializedName(value = "routeId", alternate = "RouteId")
    public String routeId;
    @SerializedName(value = "routType", alternate = "Type")
    public short routType;
    @SerializedName(value = "routNumber", alternate = "Number")
    public String routNumber;
    @SerializedName(value = "direction", alternate = "Direction")
    public boolean direction;
    @SerializedName(value = "startStopName", alternate = "StartStopName")
    public String startStopName;
    @SerializedName(value = "finishStopName", alternate = "FinishStopName")
    public String finishStopName;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
