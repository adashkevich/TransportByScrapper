package com.adashkevich.transport_by_parser.utils;

import com.adashkevich.transport_by_parser.model.transport_by.Rout;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GomelPublicTransportUtil {

    // $$('.routes-list b').map(e => e.innerText)
    protected static List<String> busRoutesNumbers = Arrays.asList("1", "2", "2а", "2э", "3", "3а", "4", "4а", "4б", "5", "5а", "6", "7", "7а", "8", "8а", "8б", "8е", "9", "10", "10а", "10б", "11", "12", "13", "13а", "14", "15", "16", "16а", "17", "18", "18а", "19", "20", "21", "21а", "21в", "22", "22а", "23", "25", "25а", "25в", "26", "27", "28", "31", "33", "33а", "34", "35", "35а", "35б", "36а", "37", "38э", "39", "40", "41", "42", "42а", "42б", "43", "44", "46", "48", "50", "50а", "50б", "52", "54", "55", "56", "57", "58", "58а", "60", "60а", "61", "61а", "62", "63", "64", "27а", "59", "101", "107", "108", "108а", "110", "111", "119", "122", "127", "127а");
    protected static List<String> trolleybusRoutesNumbers = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "7а", "8", "9", "10", "11", "11б", "11в", "12", "12а", "14", "15", "16", "17", "18", "19", "19а", "20", "21", "22", "22а", "23", "24", "25");

    public static void trolleybusChecker(List<Rout> routes) throws IOException {
        List<Rout> trolleybus = routes.stream().filter(r -> r.routType == 1).collect(Collectors.toList());

        System.out.println(trolleybus.stream().filter(r -> !trolleybusRoutesNumbers.contains(r.routNumber)).map(r -> r.routNumber).sorted().collect(Collectors.joining(",")));
    }
}
