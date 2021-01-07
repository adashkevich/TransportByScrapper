package com.adashkevich.transport_by_parser.model.gtfs.emum;

import com.adashkevich.transport_by_parser.exception.GTFSRuntimeException;

import java.util.Arrays;

public enum GTimepoint {
    APPROXIMATE(0),
    EXACT(1);

    public final int value;

    GTimepoint(int value) {
        this.value = value;
    }

    public static GTimepoint valueOf(int value) {
        return Arrays.stream(GTimepoint.values()).filter(v -> v.value == value).findAny()
                .orElseThrow(() -> new GTFSRuntimeException("Wrong GTimepoint value " + value));
    }
}
