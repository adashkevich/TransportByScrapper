package com.adashkevich.transport_by_parser.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Schedule {

    @SerializedName(value = "day", alternate = "Days")
    short day;
    @SerializedName(value = "hour", alternate = "Hour")
    short hour;
    @SerializedName(value = "minutes", alternate = "Minutes")
    short minutes;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
