package com.adashkevich.transport_by_parser.model.gson;

import com.adashkevich.transport_by_parser.model.Schedule;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleGsonWrapper {

    @SerializedName(value = "Items")
    public List<Schedule> item;
}
