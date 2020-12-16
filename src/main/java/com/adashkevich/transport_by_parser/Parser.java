package com.adashkevich.transport_by_parser;

import com.adashkevich.transport_by_parser.model.Rout;
import com.adashkevich.transport_by_parser.model.Schedule;
import com.adashkevich.transport_by_parser.model.Stop;
import com.adashkevich.transport_by_parser.model.dto.StopRoutSchedule;
import com.adashkevich.transport_by_parser.model.dto.StopRouts;
import com.adashkevich.transport_by_parser.model.gson.BusGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.RoutGsonWrapper;
import com.adashkevich.transport_by_parser.model.gson.ScheduleGsonWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Parser {

    protected static final Gson gson = new Gson();
    protected static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {

        try {
//            parseBusStops();
//            System.out.println(getStopsFromJson());
//            getStopRoutes("6065164");
//            System.out.println(getRoutsFromJson());
            getSchedule("10271756", "7258124");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSchedule(String stopId, String routeId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRoutSchedule(stopId, routeId)), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://212.98.184.62/api/GetSchedule")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String content = Objects.requireNonNull(response.body()).string();

        List<Schedule> routs = new ArrayList<>();

        parseTransportByResponse(content, (entity) -> {
            routs.addAll(gson.fromJson(entity, ScheduleGsonWrapper.class).item);
        });

        FileUtils.writeStringToFile(new File("schedule.json"), gson.toJson(routs), Charset.defaultCharset());
    }

    public static void getStopRoutes(String stopId) throws IOException {
        RequestBody body = RequestBody.create(gson.toJson(new StopRouts(stopId)), MediaType.get("application/json; charset=utf-8"));

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

        FileUtils.writeStringToFile(new File("routs.json"), gson.toJson(routs), Charset.defaultCharset());

    }

    public static void parseStops() throws IOException {
        String content = IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream("bus_stops.response")), StandardCharsets.UTF_8);

        List<Stop> stops = new ArrayList<>();

        parseTransportByResponse(content, (entity) -> {
            stops.add(gson.fromJson(entity, BusGsonWrapper.class).result);
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
        return gson.fromJson(IOUtils.toString(Objects.requireNonNull(Parser.class.getClassLoader().getResourceAsStream("routs.json")), StandardCharsets.UTF_8),
                new TypeToken<ArrayList<Rout>>() {
                }.getType());
    }
}
