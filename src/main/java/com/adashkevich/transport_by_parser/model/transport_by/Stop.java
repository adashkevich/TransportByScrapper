package com.adashkevich.transport_by_parser.model.transport_by;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Stop {

    @SerializedName(value = "stopId", alternate = "StopId")
    public String stopId;
    @SerializedName(value = "name", alternate = "StopName")
    public String name;
    @SerializedName(value = "bearing", alternate = "Bearing")
    public short bearing;
    @SerializedName(value = "stopType", alternate = "StopType")
    public short stopType;
    @SerializedName(value = "address", alternate = "Address")
    public String address;
    @SerializedName(value = "point", alternate = "Point")
    public Point point;

    public List<Schedule> schedules;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}