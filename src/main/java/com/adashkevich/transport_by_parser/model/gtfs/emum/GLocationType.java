package com.adashkevich.transport_by_parser.model.gtfs.emum;

import com.adashkevich.transport_by_parser.exception.GTFSRuntimeException;

import java.util.Arrays;

public enum GLocationType {

    STOP(0),
    PLATFORM(0),
    STATION(1),
    ENTRANCE_EXIT(2),
    GENERIC_NODE(3),
    BOARDING_AREA(4);

    public final int value;

    GLocationType(int value) {
        this.value = value;
    }

    public static GLocationType valueOf(int value) throws GTFSRuntimeException {
        return Arrays.stream(GLocationType.values()).filter(v -> v.value == value).findAny()
                .orElseThrow(() -> new GTFSRuntimeException("Wrong GLocationType value " + value));
    }
}
