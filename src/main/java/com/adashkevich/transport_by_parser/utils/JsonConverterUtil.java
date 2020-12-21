package com.adashkevich.transport_by_parser.utils;

import com.adashkevich.transport_by_parser.model.gotrans.GoTransRout;

import java.util.Arrays;
import java.util.List;

import static com.adashkevich.transport_by_parser.model.gotrans.GoTransRout.RoutType.BUS;
import static com.adashkevich.transport_by_parser.model.gotrans.GoTransRout.RoutType.TROLLEYBUS;

public class JsonConverterUtil {

    protected static List<Short> processableRoutType = Arrays.asList((short) 0, (short) 1, (short) 3);

    public static List<Short> getPublicTransportRoutTypes() {
        return processableRoutType;
    }

    public static GoTransRout.RoutType convertRoutType(short routType) {
        if (routType == 0 || routType == 3) {
            return BUS;
        } else if (routType == 1) {
            return TROLLEYBUS;
        }
        return null;
    }
}
