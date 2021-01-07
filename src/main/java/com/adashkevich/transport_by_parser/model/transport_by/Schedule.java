package com.adashkevich.transport_by_parser.model.transport_by;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Schedule {

    @SerializedName(value = "day", alternate = "Days")
    public short day;
    @SerializedName(value = "hour", alternate = "Hour")
    public short hour;
    @SerializedName(value = "minutes", alternate = "Minutes")
    public short minutes;

    public int timestamp() {
        return hour * 60 + minutes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
