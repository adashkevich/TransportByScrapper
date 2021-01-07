package com.adashkevich.transport_by_parser.helper;

import java.time.format.DateTimeFormatter;

public interface GTFSHelper {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    static Integer getIntValue(Boolean value) {
        return value != null ? (value ? 1 : 0) : null;
    }
}
