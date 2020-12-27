package com.adashkevich.transport_by_parser.model.gtfs.emum;

import com.adashkevich.transport_by_parser.exception.GTFSRuntimeException;

import java.util.Arrays;

public enum GContinuousDropOff {

    ALLOWED(0),
    DENY(1),
    PHONE_AGENCY(2),
    COORDINATE_WITH_DRIVER(3);

    public final int value;

    GContinuousDropOff(int value) {
        this.value = value;
    }

    public static GContinuousDropOff valueOf(int value) {
        return Arrays.stream(GContinuousDropOff.values()).filter(v -> v.value == value).findAny()
                .orElseThrow(() -> new GTFSRuntimeException("Wrong GContinuousDropOff Type value " + value));
    }
}
