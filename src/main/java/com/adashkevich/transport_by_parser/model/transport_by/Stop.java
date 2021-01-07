package com.adashkevich.transport_by_parser.model.transport_by;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Schedule> getWeekdaySchedule(int separationHour) {
        return schedules != null ? schedules.stream().filter(s -> (s.day == 1 && s.hour >= separationHour)
                || (s.day == 2 && s.hour < separationHour))
                .map(s -> new Schedule((short) (s.day - 1), s.hour, s.minutes))
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    public List<Schedule> getWeekendSchedule(int separationHour) {
        return schedules != null ? schedules.stream().filter(s -> (s.day == 6 && s.hour >= separationHour)
                || (s.day == 7 && s.hour < separationHour))
                .map(s -> new Schedule((short) (s.day - 6), s.hour, s.minutes))
                .collect(Collectors.toList()) : new ArrayList<>();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
