package com.adashkevich.transport_by_parser;

import com.adashkevich.transport_by_parser.model.dto.Area;
import com.adashkevich.transport_by_parser.model.dto.StopRout;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransDirection;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransRout;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransRoutePoint;
import com.adashkevich.transport_by_parser.model.gotrans.GoTransSchedule;
import com.adashkevich.transport_by_parser.model.gson.RoutGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.RouteStopsGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.ScheduleGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.StopGsonWrapper;
import com.adashkevich.transport_by_parser.model.transport_by.Rout;
import com.adashkevich.transport_by_parser.model.transport_by.Schedule;
import com.adashkevich.transport_by_parser.model.transport_by.Stop;
import com.adashkevich.transport_by_parser.utils.GoTransUtil;
import com.adashkevich.transport_by_parser.utils.HttpUtil;
import com.adashkevich.transport_by_parser.utils.TranslitUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Parser {

    protected static final Gson gson = new Gson();

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("Program argument should be present");
            } else {
                switch (args[0]) {
                    case "convert:gotrans":
                        convertToGoTransFormat(args[1]);
                        break;
                    case "scrape:stops":
                        scrapeStops(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
                        break;
                    case "scrape:routs":
                        scrapeAllStopRoutes(args[1]);
                        break;
                    case "scrape:routs_stops":
                        scrapeRoutesStops(args[1]);
                        break;
                    case "scrape:schedules":
                        scrapeSchedules(args[1]);
                        break;
                    default:
                        System.err.printf("Command %s not found. List of available commands: " +
                                "convert:gotrans, scrape:stops, scrape:routs, scrape:routs_stops, scrape:schedules%n", args[0]);
                }
            }


//            List<GRoute> routs = CsvUtil.read(Paths.get("src/main/resources/gtfs/routes.txt"), GRoute.class);
//            GRoute rout = new GRoute();
//            rout.setAgencyID("a_id");
//            rout.setRouteTypeEnum(GRouteType.CABLE);
//            rout.setRouteID("r_id");
//            routs.add(rout);
//            CsvUtil.write(routs, Paths.get("src/main/resources/gtfs/routes.txt"));

//            CsvUtil.write(getRoutsFromJson("transport_by/routs_with_stops_and_schedule.json").stream()
//                    .filter(r -> r.routType == 1)
//                    .map(GTFSUtil::convertRout)
//                    .collect(Collectors.toList()),
//                    Paths.get("src/main/resources/gtfs/routes.csv"));

//            System.out.println(CsvUtil.read(Paths.get("src/main/resources/gtfs/routes.csv"), GRoute.class));

//            List<GRoute> gtfsRouts = CsvUtil.read(Paths.get("src/main/resources/gtfs/routes.csv"), GRoute.class);


//            List<String> stopIDs = getRoutsFromJson("transport_by/routs_with_stops.json").stream()
//                    .filter(r -> r.routType == 1)
//                    .flatMap(r -> {
//                        List<Stop> stopList = new ArrayList<>(r.stops);
//                        if(r.backwardStops != null) stopList.addAll(r.backwardStops);
//                        return stopList.stream();
//                    })
//                    .map(s -> s.stopId)
//                    .distinct()
//                    .collect(Collectors.toList());
//
//            CsvUtil.write(getStopsFromJson().stream().filter(s -> stopIDs
//                            .contains(s.stopId))
//                            .map(GTFSUtil::convertStop)
//                            .collect(Collectors.toList()),
//                    Paths.get("src/main/resources/gtfs/stops.csv"));


//            GCalendar weekdays = GCalendar.forDays(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
//            weekdays.setServiceID("weekdays");
//            weekdays.setStartLocalDate(LocalDate.of(2020, 1, 1));
//            weekdays.setEndLocalDate(LocalDate.of(2022, 1, 1));
//
//            GCalendar weekend = GCalendar.forDays(SATURDAY, SUNDAY);
//            weekend.setServiceID("weekend");
//            weekend.setStartLocalDate(LocalDate.of(2020, 1, 1));
//            weekend.setEndLocalDate(LocalDate.of(2022, 1, 1));
//
//            CsvUtil.write(Arrays.asList(weekdays, weekend), Paths.get("src/main/resources/gtfs/calendar.txt"));

//            List<Rout> routs = getRoutsFromJson("transport_by/routs_with_stops_and_schedule.json").stream()
//                    .filter(r -> r.routType == 1).collect(Collectors.toList());
//            List<GTrip> gTrips = routs.stream().flatMap(r -> GTFSUtil.convertTrips(r).stream()).collect(Collectors.toList());
//            CsvUtil.write(gTrips, Paths.get("src/main/resources/gtfs/trips.csv"));
//            CsvUtil.write(gTrips.stream().flatMap(t -> t.getStopTimes().stream()).collect(Collectors.toList()),
//                    Paths.get("src/main/resources/gtfs/stop_times.csv"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertToGoTransFormat(String transportByJsonPath) throws IOException {
        convertToGoTransFormat(transportByJsonPath, "go_trans.json");
    }

    public static void convertToGoTransFormat(String transportByJsonPath, String destinationFile) throws IOException {
        List<Rout> routs = getRoutsFromFileSystem(transportByJsonPath)
                .stream().filter(r -> GoTransUtil.getPublicTransportRoutTypes().contains(r.routType))
                .collect(Collectors.toList());

        List<GoTransRout> gtRoutes = new ArrayList<>();

        routs.forEach(r -> {
            GoTransRout gtRout = new GoTransRout();
            gtRout.type = GoTransUtil.convertRoutType(r.routType);
            if (gtRout.type == null) {
                System.err.println("Unknown rout type for Rout " + r.routeId);
                return;
            }
            gtRout.name = r.routNumber;
            gtRout.code = GoTransUtil.getFirstRoutLetter(gtRout.type) + TranslitUtil.translit(r.routNumber);

            setGoTransDirection(r, r.direction, r.stops)
                    .ifPresent(d -> gtRout.directions.add(d));
            setGoTransDirection(r, !r.direction, r.backwardStops)
                    .ifPresent(d -> gtRout.directions.add(d));

            gtRoutes.add(gtRout);
        });

        FileUtils.writeStringToFile(new File(destinationFile), gson.toJson(gtRoutes), StandardCharsets.UTF_8);
    }

    public static Optional<GoTransDirection> setGoTransDirection(Rout rout, boolean isForward, List<Stop> stops) {
        if (stops != null) {
            GoTransDirection gtDirection = new GoTransDirection();
            gtDirection.transportByRoutId = rout.routeId;
            gtDirection.isForward = isForward;
            gtDirection.name = rout.getRoutName(isForward);

            for (int i = 0; i < stops.size(); ++i) {
                GoTransRoutePoint grRoutPoint = new GoTransRoutePoint();
                grRoutPoint.transportByStopId = stops.get(i).stopId;
                grRoutPoint.position = (short) (i + 1);
                grRoutPoint.name = stops.get(i).name;

                if (stops.get(i).schedules != null) {
                    Map<Short, GoTransSchedule> gtScheduleMap = new TreeMap<>();
                    stops.get(i).schedules.forEach(sc -> {
                        GoTransSchedule gtSchedule;
                        if (gtScheduleMap.containsKey(sc.day)) {
                            gtSchedule = gtScheduleMap.get(sc.day);
                        } else {
                            gtSchedule = new GoTransSchedule();
                            gtSchedule.days = String.valueOf(sc.day);
                            gtScheduleMap.put(sc.day, gtSchedule);
                        }
                        gtSchedule.times.add(String.format("%02d:%02d", sc.hour, sc.minutes));
                        Collections.sort(gtSchedule.times);
                    });

                    grRoutPoint.schedules = gtScheduleMap.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .map(Map.Entry::getValue)
                            .collect(Collectors.toList());
                }

                gtDirection.routePoints.add(grRoutPoint);
            }
            return Optional.of(gtDirection);
        }
        return Optional.empty();
    }

    public static void scrapeSchedules(String routesWithStopsFile) throws IOException {
        scrapeSchedules(routesWithStopsFile, "routs_with_stops_and_schedule.json");
    }

    public static void scrapeSchedules(String routesWithStopsFile, String destinationFile) throws IOException {
        List<Rout> routs = getRoutsFromFileSystem(routesWithStopsFile);

        routs.forEach(r -> {
            System.out.println("Start scraping Rout " + r.routeId);
            setScheduleToStops(r.stops, r.routeId);
            setScheduleToStops(r.backwardStops, r.routeId);
        });

        FileUtils.writeStringToFile(new File(destinationFile), gson.toJson(routs), StandardCharsets.UTF_8);
    }

    public static void setScheduleToStops(List<Stop> stops, String routeId) {
        if (stops != null) {
            stops.forEach(s -> {
                try {
                    s.schedules = getSchedule(s.stopId, routeId);
                } catch (Exception e) {
                    System.err.println("Failed to get Schedule for stop " + s.stopId + " on rout " + routeId);
                    e.printStackTrace();
                }
            });
        }
    }

    public static void scrapeRoutesStops(String routesJsonPath) throws IOException {
        scrapeRoutesStops(routesJsonPath, "routs_with_stops.json");
    }

    public static void scrapeRoutesStops(String routesJsonPath, String destinationFile) throws IOException {
        List<Rout> routs = getRoutsFromFileSystem(routesJsonPath);

        routs.forEach(r -> {
            try {
                Pair<List<Stop>, List<Stop>> routStops = getRouteStops(r.routeId);
                r.stops = routStops.getLeft();
                r.backwardStops = routStops.getRight();
            } catch (Exception e) {
                System.err.println("Failed to get Stops for rout " + r.routeId);
//                e.printStackTrace();
            }
        });

        FileUtils.writeStringToFile(new File(destinationFile), gson.toJson(routs), StandardCharsets.UTF_8);
    }

    public static void scrapeStops(double top, double left, double bottom, double right) throws IOException {
        scrapeStops(top, left, bottom, right, "stops.json");
    }

    public static void scrapeStops(double top, double left, double bottom, double right, String destinationFile) throws IOException {
        List<Stop> stops = getStops(top, left, bottom, right);

        FileUtils.writeStringToFile(new File(destinationFile), gson.toJson(stops), StandardCharsets.UTF_8);
    }

    public static List<Stop> getStops(double top, double left, double bottom, double right) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new Area(top, left, bottom, right)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetStops")
                .post(body)
                .build();

        Response response = HttpUtil.client.newCall(request).execute();

        List<Stop> stops = new ArrayList<>();

        if (response.isSuccessful()) {
            String content = Objects.requireNonNull(response.body()).string();

            parseTransportByResponse(content, (entity) -> {
                stops.add(gson.fromJson(entity, StopGsonWrapper.class).result);
            });
        } else {
            System.err.println("Failed to get stops");
            System.err.println(Objects.requireNonNull(response.body()).string());
        }

        return stops;
    }

    public static Pair<List<Stop>, List<Stop>> getRouteStops(String routId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRout().setRoutId(routId)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetRouteStops")
                .post(body)
                .build();

        Response response = HttpUtil.client.newCall(request).execute();

        RouteStopsGsonWrapper wrapper = gson.fromJson(Objects.requireNonNull(response.body()).string(), RouteStopsGsonWrapper.class);
        return Pair.of(wrapper.stops, wrapper.backwardStops);
    }

    public static void scrapeAllStopRoutes(String stopsJsonPath) throws IOException {
        scrapeAllStopRoutes(stopsJsonPath, "routs.json");
    }

    public static void scrapeAllStopRoutes(String stopsJsonPath, String destinationFile) throws IOException {
        List<Rout> routs = getAllStopsRoutes(getStopsFromJson(stopsJsonPath));

        FileUtils.writeStringToFile(new File(destinationFile), gson.toJson(routs), StandardCharsets.UTF_8);
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

        Response response = HttpUtil.client.newCall(request).execute();

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

//        FileUtils.writeStringToFile(new File("schedule.json"), gson.toJson(routs), StandardCharsets.UTF_8);
        return schedules;
    }

    public static List<Rout> getStopRoutes(String stopId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRout().setStopId(stopId)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetStopRouts")
                .post(body)
                .build();

        Response response = HttpUtil.client.newCall(request).execute();

        String content = Objects.requireNonNull(response.body()).string();

        List<Rout> routs = new ArrayList<>();

        parseTransportByResponse(content, (entity) -> {
            routs.add(gson.fromJson(entity, RoutGsonWrapper.class).result);
        });

//        FileUtils.writeStringToFile(new File("routs.json"), gson.toJson(routs), StandardCharsets.UTF_8);
        return routs;
    }

    @Deprecated
    public static void parseStops() throws IOException {
        String content = IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream("transport_by/all_stops.response")), StandardCharsets.UTF_8);

        List<Stop> stops = new ArrayList<>();

        parseTransportByResponse(content, (entity) -> {
            stops.add(gson.fromJson(entity, StopGsonWrapper.class).result);
        });

        FileUtils.writeStringToFile(new File("src/main/resources/transport_by/stops.json"), gson.toJson(stops), StandardCharsets.UTF_8);
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
        return gson.fromJson(IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream("transport_by/stops.json")), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Stop>>() {
                }.getType());
    }

    public static List<Stop> getStopsFromJson(String filePath) throws IOException {
        return gson.fromJson(IOUtils.toString(new FileInputStream(filePath), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Stop>>() {
                }.getType());
    }

    public static List<Rout> getRoutsFromJson() throws IOException {
        return getRoutsFromJson("transport_by/routs.json");
    }

    public static List<Rout> getRoutsFromJson(String fileName) throws IOException {
        return gson.fromJson(IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Rout>>() {
                }.getType());
    }

    public static List<Rout> getRoutsFromFileSystem(String filePath) throws IOException {
        return gson.fromJson(IOUtils.toString(new FileInputStream(filePath), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Rout>>() {
                }.getType());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
