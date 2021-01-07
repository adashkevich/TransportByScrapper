package com.adashkevich.transport_by_parser.model.gtfs.emum;

import com.adashkevich.transport_by_parser.exception.GTFSRuntimeException;

import java.util.Arrays;

public enum GPickupType {

    NO_PICKUP(0),
    PHONE_AGENCY(1),
    COORDINATE_WITH_DRIVER(2);

    public final int value;

    GPickupType(int value) {
        this.value = value;
    }

    public static GPickupType valueOf(int value) {
        return Arrays.stream(GPickupType.values()).filter(v -> v.value == value).findAny()
                .orElseThrow(() -> new GTFSRuntimeException("Wrong GPickupType value " + value));
    }
}
