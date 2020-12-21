package com.adashkevich.transport_by_parser.model.gotrans;

import java.util.ArrayList;
import java.util.List;

public class GoTransDirection {

    public String transportByRoutId;
    public boolean isForward;
    public String name;
    public List<GoTransRoutePoint> routePoints = new ArrayList<>();
}
