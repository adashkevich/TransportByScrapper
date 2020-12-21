package com.adashkevich.transport_by_parser;

import com.adashkevich.transport_by_parser.model.Rout;
import com.adashkevich.transport_by_parser.model.Schedule;
import com.adashkevich.transport_by_parser.model.Stop;
import com.adashkevich.transport_by_parser.model.dto.StopRout;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransDirection;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransRout;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransRout.RoutType;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransRoutePoint;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransSchedule;
import com.adashkevich.transport_by_parser.model.gson.RouteStopsGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.StopGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.RoutGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.ScheduleGsonWrapper;
import com.adashkevich.transport_by_parser.utils.JsonConverterUtil;
import com.adashkevich.transport_by_parser.utils.Translit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.adashkevich.transport_by_parser.model.gotrans.GoTransRout.RoutType.BUS;

public class Parser {

    protected static final Gson gson = new Gson();
    protected static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        try {
//            parseBusStops();
//            System.out.println(getStopsFromJson().size());
//            getStopRoutes("6065164");
//            System.out.println(getRoutsFromJson().stream().map(r -> r.routType + ": " + r.routNumber).collect(Collectors.joining(",")));
//            System.out.println(getRoutsFromJson().stream().filter(distinctByKey(r -> r.routType)).map(r -> String.valueOf(r.routType)).collect(Collectors.joining(",")));
//            System.out.println(getRoutsFromJson().stream().filter(r -> JsonConverterUtil.getPublicTransportRoutTypes().contains(r.routType)).map(r -> r.routNumber).collect(Collectors.joining(",")));
//            getSchedule("10271756", "7258124");
//            extractAllStopRoutes();
//            System.out.println(getRouteStops("808972"));
            setStopsToRoutes();
//            setSchedulesToStops();
//            convertJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertJson() throws IOException {
        List<Rout> routs = getRoutsFromJson("routs_with_stops_and_schedule.json")
                .stream().filter(r -> JsonConverterUtil.getPublicTransportRoutTypes().contains(r.routType))
                .collect(Collectors.toList());

        List<GoTransRout> gtRoutes = new ArrayList<>();

        routs.forEach(r -> {
            RoutType routType = JsonConverterUtil.convertRoutType(r.routType);

            if (routType == null) {
                System.err.println("Unknown rout type for Rout " + r.routeId);
                return;
            }

            Optional<GoTransRout> foundGtRout = gtRoutes.stream().filter(er -> r.routNumber.equals(er.name) && routType.equals(er.type)).findAny();

            GoTransRout gtRout;
            if (foundGtRout.isPresent()) {
                gtRout = foundGtRout.get();
            } else {
                gtRout = new GoTransRout();
                gtRout.type = routType;
                gtRout.name = r.routNumber;
                gtRout.code = (routType == BUS ? "a" : "t") + Translit.apply(r.routNumber);
            }

            GoTransDirection gtDirection = new GoTransDirection();
            gtDirection.transportByRoutId = r.routeId;
            gtDirection.isForward = r.direction;
            gtDirection.name = r.startStopName + " - " + r.finishStopName;

            if (r.stops != null) {
                for (int i = 0; i < r.stops.size(); ++i) {
                    GoTransRoutePoint grRoutPoint = new GoTransRoutePoint();
                    grRoutPoint.transportByStopId = r.stops.get(i).stopId;
                    grRoutPoint.position = (short) (i + 1);
                    grRoutPoint.name = r.stops.get(i).name;

                    if (r.stops.get(i).schedules != null) {
                        Map<Short, GoTransSchedule> gtScheduleMap = new TreeMap<>();
                        r.stops.get(i).schedules.forEach(sc -> {
                            GoTransSchedule gtSchedule;
                            if (gtScheduleMap.containsKey(sc.day)) {
                                gtSchedule = gtScheduleMap.get(sc.day);
                            } else {
                                gtSchedule = new GoTransSchedule();
                                gtSchedule.days = String.valueOf(sc.day);
                                gtScheduleMap.put(sc.day, gtSchedule);
                            }
                            gtSchedule.times.add(String.format("%02d:%02d", sc.hour, sc.minutes));
                        });

                        grRoutPoint.schedules = gtScheduleMap.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .map(Map.Entry::getValue)
                                .collect(Collectors.toList());
                    }

                    gtDirection.routePoints.add(grRoutPoint);
                }
            }

            gtRout.directions.add(gtDirection);

            if (!foundGtRout.isPresent()) {
                gtRoutes.add(gtRout);
            }
        });

        FileUtils.writeStringToFile(new File("go_trans.json"), gson.toJson(gtRoutes), Charset.defaultCharset());
    }

    public static void setSchedulesToStops() throws IOException {
        List<Rout> routs = getRoutsFromJson("routs_with_stops.json");

        routs.forEach(r -> {
            System.out.println("Start processing Rout " + r.routeId);
            if (r.stops != null) {
                r.stops.forEach(s -> {
                    try {
                        s.schedules = getSchedule(s.stopId, r.routeId);
                    } catch (Exception e) {
                        System.out.println("Failed to get Schedule for stop " + s.stopId + " on rout " + r.routeId);
                        e.printStackTrace();
                    }
                });
            }
        });

        FileUtils.writeStringToFile(new File("routs_with_stops_and_schedule.json"), gson.toJson(routs), Charset.defaultCharset());
    }

    public static void setStopsToRoutes() throws IOException {
        List<Rout> routs = getRoutsFromJson();

        routs.forEach(r -> {
            try {
                Pair<List<Stop>, List<Stop>> routStops = getRouteStops(r.routeId);
                r.stops = routStops.getLeft();
                r.backwardStops = routStops.getRight();
            } catch (Exception e) {
                System.out.println("Failed to get Stops for rout " + r.routeId);
                e.printStackTrace();
            }
        });

        FileUtils.writeStringToFile(new File("routs_with_stops.json"), gson.toJson(routs), Charset.defaultCharset());
    }

    public static Pair<List<Stop>, List<Stop>>  getRouteStops(String routId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRout().setRoutId(routId)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetRouteStops")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        RouteStopsGsonWrapper wrapper = gson.fromJson(Objects.requireNonNull(response.body()).string(), RouteStopsGsonWrapper.class);
        return Pair.of(wrapper.stops, wrapper.backwardStops);
    }

    public static void extractAllStopRoutes() throws IOException {
        List<Rout> routs = getAllStopsRoutes(getStopsFromJson());

        FileUtils.writeStringToFile(new File("routs.json"), gson.toJson(routs), Charset.defaultCharset());
    }

    public static List<Rout> getAllStopsRoutes(@NotNull List<Stop> stops) {
        List<Rout> routs = new ArrayList<>();

        stops.forEach(s -> {
            try {
                routs.addAll(getStopRoutes(s.stopId));
            } catch (Exception e) {
                System.out.println("Failed to get Routes for stop " + s.stopId);
                e.printStackTrace();
            }
        });

        return routs.stream()
                .filter(distinctByKey(r -> r.routeId))
                .collect(Collectors.toList());
    }

    public static List<Schedule> getSchedule(String stopId, String routeId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRout().setStopId(stopId).setRoutId(routeId)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetSchedule")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        List<Schedule> schedules = new ArrayList<>();

        if (response.isSuccessful()) {
            String content = Objects.requireNonNull(response.body()).string();

            parseTransportByResponse(content, (entity) -> {
                schedules.addAll(gson.fromJson(entity, ScheduleGsonWrapper.class).item);
            });
        } else {
            System.err.println("Failed to get schedule for Stop " + stopId + " Rout " + routeId);
            System.err.println(Objects.requireNonNull(response.body()).string());
        }

//        FileUtils.writeStringToFile(new File("schedule.json"), gson.toJson(routs), Charset.defaultCharset());
        return schedules;
    }

    public static List<Rout> getStopRoutes(String stopId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRout().setStopId(stopId)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetStopRouts")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String content = Objects.requireNonNull(response.body()).string();

        List<Rout> routs = new ArrayList<>();

        parseTransportByResponse(content, (entity) -> {
            routs.add(gson.fromJson(entity, RoutGsonWrapper.class).result);
        });

//        FileUtils.writeStringToFile(new File("routs.json"), gson.toJson(routs), Charset.defaultCharset());
        return routs;
    }

    public static void parseStops() throws IOException {
        String content = IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream("bus_stops.response")), StandardCharsets.UTF_8);

        List<Stop> stops = new ArrayList<>();

        parseTransportByResponse(content, (entity) -> {
            stops.add(gson.fromJson(entity, StopGsonWrapper.class).result);
        });

        FileUtils.writeStringToFile(new File("stops.json"), gson.toJson(stops), Charset.defaultCharset());
    }

    public static void parseTransportByResponse(String responseContent, Consumer<String> entityProcessor) {
        int blockStartIndex = -1;
        int nesting = 0;

        for (int i = 0; i < responseContent.length(); ++i) {
            if (responseContent.charAt(i) == '{') {
                if (nesting == 0) {
                    blockStartIndex = i;
                }
                ++nesting;
            }

            if (responseContent.charAt(i) == '}') {
                --nesting;
                if (nesting == 0) {
                    entityProcessor.accept(responseContent.substring(blockStartIndex, i + 1));
                }
            }
        }
    }

    public static List<Stop> getStopsFromJson() throws IOException {
        return gson.fromJson(IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream("stops.json")), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Stop>>() {
                }.getType());
    }

    public static List<Rout> getRoutsFromJson() throws IOException {
        return getRoutsFromJson("routs.json");
    }

    public static List<Rout> getRoutsFromJson(String fileName) throws IOException {
        return gson.fromJson(IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Rout>>() {
                }.getType());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
