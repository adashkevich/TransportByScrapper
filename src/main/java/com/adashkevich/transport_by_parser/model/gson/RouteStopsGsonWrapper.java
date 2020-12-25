package com.adashkevich.transport_by_parser.model.gson;

import com.adashkevich.transport_by_parser.model.transport_by.Stop;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteStopsGsonWrapper {

    @SerializedName(value = "StopsA")
    public List<Stop> stops;

    @SerializedName(value = "StopsB")
    public List<Stop> backwardStops;
}
