package com.adashkevich.transport_by_parser.model.gtfs;

import com.opencsv.bean.CsvBindByName;

public class GRoute {
    @CsvBindByName(column = "route_id")
    private String routeID;
    @CsvBindByName(column = "agency_id")
    private String agencyID;
    @CsvBindByName(column = "route_short_name")
    private String routeShortName;
    @CsvBindByName(column = "route_long_name")
    private String routeLongName;
    @CsvBindByName(column = "route_desc")
    private String routeDesc;
    @CsvBindByName(column = "route_type")
    private GRouteType routeType;
    @CsvBindByName(column = "route_url")
    private String routeUrl;
    @CsvBindByName(column = "route_color")
    private String routeColor;
    @CsvBindByName(column = "route_text_color")
    private String routeTextColor;

}
