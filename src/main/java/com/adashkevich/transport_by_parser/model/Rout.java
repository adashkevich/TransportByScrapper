package com.adashkevich.transport_by_parser.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Rout {

    @SerializedName(value = "routeId", alternate = "RouteId")
    public String routeId;
    @SerializedName(value = "routType", alternate = "Type")
//    0 bus
//    1 trolleybus
//    3 shuttle bus
//    26 electric train
//    31 regional train
//    28 interregional trains of economy class
//    30 interregional trains of business class
    public short routType;
    @SerializedName(value = "routNumber", alternate = "Number")
    public String routNumber;
    @SerializedName(value = "direction", alternate = "Direction")
    public boolean direction;
    @SerializedName(value = "startStopName", alternate = "StartStopName")
    public String startStopName;
    @SerializedName(value = "finishStopName", alternate = "FinishStopName")
    public String finishStopName;

    public List<Stop> stops;
    public List<Stop> backwardStops;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String display() {
        return routNumber + ": " + startStopName + " — " + finishStopName;
    }
}
