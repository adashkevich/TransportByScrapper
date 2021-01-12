package com.adashkevich.transport_by_parser.model.dto;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName(value = "Point1")
    AreaPoint topLeft;
    @SerializedName(value = "Point2")
    AreaPoint bottomRight;

    public Area() {}

    public Area(double top, double left, double bottom, double right) {
        topLeft = new AreaPoint(top, left);
        bottomRight = new AreaPoint(bottom, right);
    }
}
