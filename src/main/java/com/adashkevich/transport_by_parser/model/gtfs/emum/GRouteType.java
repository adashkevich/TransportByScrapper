package com.adashkevich.transport_by_parser.model.gtfs.emum;

import com.adashkevich.transport_by_parser.exception.GTFSRuntimeException;

import java.util.Arrays;

public enum GRouteType {
    /* 0 - Tram, Streetcar, Light rail. Any light rail or street level system within a metropolitan area. */
    TRAM(0),
    /* 1 - Subway, Metro. Any underground rail system within a metropolitan area. */
    METRO(1),
    /* 2 - Rail. Used for intercity or long-distance travel. */
    RAIL(2),
    /* 3 - Bus. Used for short- and long-distance bus routes. */
    BUS(3),
    /*4 - Ferry. Used for short- and long-distance boat service.*/
    FERRY(4),
    /*5 - Cable tram. Used for street-level rail cars where the cable runs beneath the vehicle, e.g., cable car in San Francisco.*/
    CABLE(5),
    /*6 - Aerial lift, suspended cable car (e.g., gondola lift, aerial tramway). Cable transport where cabins, cars, gondolas or open chairs are suspended by means of one or more cables.*/
    AERIAL_LIFT(6),
    /*7 - Funicular. Any rail system designed for steep inclines.*/
    FUNICULAR(7),
    /*11 - Trolleybus. Electric buses that draw power from overhead wires using poles.*/
    TROLLEYBUS(11),
    /*12 - Monorail. Railway in which the track consists of a single rail or a beam.*/
    MONORAIL(12);

    public final int value;

    GRouteType(int value) {
        this.value = value;
    }

    public static GRouteType valueOf(int value) {
        return Arrays.stream(GRouteType.values()).filter(v -> v.value == value).findAny()
                .orElseThrow(() -> new GTFSRuntimeException("Wrong GRouteType value " + value));
    }
}
