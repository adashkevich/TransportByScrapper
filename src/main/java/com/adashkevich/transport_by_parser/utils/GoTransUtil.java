package com.adashkevich.transport_by_parser.utils;

import com.adashkevich.transport_by_parser.model.gotrans.GoTransRout.RoutType;

import java.util.Arrays;
import java.util.List;

import static com.adashkevich.transport_by_parser.model.gotrans.GoTransRout.RoutType.*;

public class GoTransUtil {

    public static List<Short> processableRoutType = Arrays.asList((short) 0, (short) 1, (short) 3);

    public static List<Short> getPublicTransportRoutTypes() {
        return processableRoutType;
    }

    public static RoutType convertRoutType(short routType) {
        if (routType == 0) {
            return BUS;
        } else if (routType == 1) {
            return TROLLEYBUS;
        } else if (routType == 3) {
            return MBUS;
        }
        return null;
    }

    public static String getFirstRoutLetter(RoutType type) {
        switch (type) {
            case TROLLEYBUS:
                return "t";
            case BUS:
                return "a";
            case MBUS:
                return "m";
            default:
                return null;
        }
    }
}
