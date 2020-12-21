package com.adashkevich.transport_by_parser.model.gotrans;

import java.util.List;

public class GoTransRoutePoint {

    public String transportByStopId;
    public short position;
    public String name;
    public List<GoTransSchedule> schedules;
}
