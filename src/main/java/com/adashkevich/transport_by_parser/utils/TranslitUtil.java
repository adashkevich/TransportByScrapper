package com.adashkevich.transport_by_parser.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.adashkevich.transport_by_parser.utils.TranslitUtil.CharCase.UPPER;

public class TranslitUtil {

    enum CharCase {
        UPPER, LOWER
    }

    final static Map<String, String> map = translitMap();

    private static Map<String, String> translitMap() {
        Map<String, String> map = new HashMap<>();
        map.put("а", "a");
        map.put("б", "b");
        map.put("в", "v");
        map.put("г", "g");
        map.put("д", "d");
        map.put("е", "e");
        map.put("з", "z");
        map.put("и", "i");
        map.put("й", "j");
        map.put("к", "k");
        map.put("л", "l");
        map.put("м", "m");
        map.put("н", "n");
        map.put("о", "o");
        map.put("п", "p");
        map.put("р", "r");
        map.put("с", "s");
        map.put("т", "t");
        map.put("у", "u");
        map.put("ф", "f");
        map.put("х", "h");
        map.put("э", "E");

        return map;
    }

    private static CharCase charClass(char c) {
        return Character.isUpperCase(c) ? UPPER : CharCase.LOWER;
    }

    private static String getChar(String c) {
        CharCase charCase = charClass(c.charAt(0));
        String result = map.get(c.toLowerCase());
        return result == null ? c : UPPER.equals(charCase) ? result.toUpperCase() : result;
    }

    public static String translit(String text) {
        int len = text.length();
        return IntStream.range(0, len).mapToObj(i -> getChar(text.substring(i, i + 1))).collect(Collectors.joining());
    }
}
