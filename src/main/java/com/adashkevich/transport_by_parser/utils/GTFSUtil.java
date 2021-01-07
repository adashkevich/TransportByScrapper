package com.adashkevich.transport_by_parser.utils;

import com.adashkevich.transport_by_parser.helper.GTFSHelper;
import com.adashkevich.transport_by_parser.model.gtfs.*;
import com.adashkevich.transport_by_parser.model.gtfs.emum.*;
import com.adashkevich.transport_by_parser.model.transport_by.Rout;
import com.adashkevich.transport_by_parser.model.transport_by.Schedule;
import com.adashkevich.transport_by_parser.model.transport_by.Stop;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.adashkevich.transport_by_parser.model.gtfs.GCalendar.WEEKDAYS_SERVICE_ID;
import static com.adashkevich.transport_by_parser.model.gtfs.GCalendar.WEEKEND_SERVICE_ID;

public class GTFSUtil {

    public static GRoute convertRout(Rout tbRout) {
        GRoute gRout = new GRoute();
        gRout.setRouteID(tbRout.routeId);
        gRout.setAgencyID(getAgencyID(tbRout));
        gRout.setRouteShortName(tbRout.routNumber);
        gRout.setRouteLongName(tbRout.getRoutName());
        gRout.setRouteTypeEnum(getRouteTypeEnum(tbRout));
        gRout.setRouteUrl(getRoutUrl(tbRout));
        gRout.setRouteColor(getRouteColor(tbRout));
        gRout.setContinuousPickupEnum(GContinuousPickup.DENY);
        gRout.setContinuousDropOffEnum(GContinuousDropOff.DENY);

        return gRout;
    }

    public static String getAgencyID(Rout tbRout) {
        switch (tbRout.routType) {
            case 1:
                return "gomel_get";
            default:
                return "gomel_gopt";
        }
    }

    public static GRouteType getRouteTypeEnum(Rout tbRout) {
        switch (tbRout.routType) {
            case 1:
                return GRouteType.TROLLEYBUS;
            case 0:
            case 3:
                return GRouteType.BUS;
            default:
                return null;
        }
    }

    public static String getRoutUrl(Rout tbRout) {
        String routUrl;
        switch (tbRout.routType) {
            case 1:
                routUrl = String.format("https://gomeltrans.net/routes/trolleybus/%s/", TranslitUtil.translit(tbRout.routNumber));
                return checkUrl(routUrl) ? routUrl : null;
            case 0:
                routUrl = String.format("https://gomeltrans.net/routes/bus/%s/", TranslitUtil.translit(tbRout.routNumber));
                return checkUrl(routUrl) ? routUrl : null;
            case 3:
                routUrl = String.format("https://gomeltrans.net/routes/mt/%s/", TranslitUtil.translit(tbRout.routNumber));
                return checkUrl(routUrl) ? routUrl : null;
            default:
                return null;
        }
    }

    protected static boolean checkUrl(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            return HttpUtil.client.newCall(request).execute().isSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    protected static String getRouteColor(Rout tbRout) {
        switch (tbRout.routType) {
            case 1:
                return "008AFF";
            case 0:
                return "FF6363";
            case 3:
                return "52D86F";
            default:
                return null;
        }
    }

    public static GStop convertStop(Stop tbStop) {
        GStop gStop = new GStop();
        gStop.setStopID(tbStop.stopId);
        gStop.setStopName(tbStop.name);
        if (tbStop.point != null) {
            gStop.setStopLat(tbStop.point.latitude);
            gStop.setStopLon(tbStop.point.longitude);
        }
        gStop.setLocationTypeEnum(GLocationType.STOP);
        gStop.setStopTimezone("Europe/Minsk");

        return gStop;
    }

    public static List<GTrip> convertTrips(Rout r) {
        List<GTrip> trips = new ArrayList<>();

        trips.addAll(convertDirection(r, r.stops, r.direction));
        trips.addAll(convertDirection(r, r.backwardStops, !r.direction));

        return trips;
    }

    protected static List<GTrip> convertDirection(Rout r, List<Stop> stops, boolean direction) {
        List<GTrip> trips = new ArrayList<>();

        if (stops != null && !stops.isEmpty()) {
            Stop firstStop = stops.get(0);
            if (firstStop.schedules != null) {

                List<GTrip> weekdayTrips = firstStop.getWeekdaySchedule(3).stream()
                        .map(s -> convertTrip(r, firstStop.stopId, s, direction, WEEKDAYS_SERVICE_ID))
                        .collect(Collectors.toList());

                weekdayTrips.forEach(t -> {
                    stops.stream().skip(1).forEachOrdered(s -> {
                        t.getLastStopTime().flatMap(lst -> getClosestStopTime(s.stopId, lst, s.getWeekdaySchedule(3)))
                                .ifPresent(t::addStopTime);
                    });
                });

                trips.addAll(weekdayTrips);

                List<GTrip> weekendTrips = firstStop.getWeekendSchedule(3).stream()
                        .map(s -> convertTrip(r, firstStop.stopId, s, direction, WEEKEND_SERVICE_ID))
                        .collect(Collectors.toList());

                weekendTrips.forEach(t -> {
                    stops.stream().skip(1).forEachOrdered(s -> {
                        t.getLastStopTime().flatMap(lst -> getClosestStopTime(s.stopId, lst, s.getWeekendSchedule(3)))
                                .ifPresent(t::addStopTime);
                    });
                });

                trips.addAll(weekendTrips);
            } else {
                System.out.println("There isn't schedules for Rout " + r.routeId + " in direction " + direction);
            }
        } else {
            System.out.println("There aren't stops for Rout " + r.routeId + " in direction " + direction);
        }
        return trips;
    }

    protected static GTrip convertTrip(Rout r, String stopID, Schedule sc, boolean direction, String serviceID) {
        GTrip gTrip = new GTrip();
        gTrip.setRoutID(r.routeId);
        gTrip.setServiceID(serviceID);
        gTrip.setTripID(generateTripID(r, sc, direction, GCalendar.getShortServiceID(serviceID)));
        gTrip.setDirectionID(GTFSHelper.getIntValue(direction));
        gTrip.addStopTime(convertFirstStopTime(gTrip.getTripID(), stopID, sc));
        return gTrip;
    }

    protected static GStopTime convertFirstStopTime(String tripID, String stopID, Schedule sc) {
        GStopTime gStopTime = new GStopTime();

        gStopTime.setTripID(tripID);
        gStopTime.setArrivalTime(sc.hour, sc.minutes);
        gStopTime.setDepartureTime(sc.hour, sc.minutes);
        gStopTime.setStopID(stopID);
        gStopTime.setStopSequence(1);
        gStopTime.setTimepoint(GTimepoint.EXACT.value);
        return gStopTime;
    }

    protected static Optional<GStopTime> getClosestStopTime(String stopID, GStopTime prevStopTime, List<Schedule> schedules) {
        GStopTime gStopTime = new GStopTime();

        Optional<Schedule> schedule = schedules.stream()
                .filter(sc -> sc.timestamp() > prevStopTime.getArrivalTimestamp())
                .min(Comparator.comparing(Schedule::timestamp));

        if (schedule.isPresent()) {
            gStopTime.setArrivalTime(schedule.get().day, schedule.get().hour, schedule.get().minutes);
            gStopTime.setDepartureTime(schedule.get().day, schedule.get().hour, schedule.get().minutes);
        } else {
            System.err.printf("Closest StopTime for trip %s and stop %s did not found%n", prevStopTime.getTripID(), stopID);
            return Optional.empty();
        }

        gStopTime.setTripID(prevStopTime.getTripID());
        gStopTime.setStopID(stopID);
        gStopTime.setStopSequence(prevStopTime.getStopSequence() + 1);
        gStopTime.setTimepoint(prevStopTime.getTimepoint());
        return Optional.of(gStopTime);
    }

    protected static String generateTripID(Rout r, Schedule s, boolean direction, String shortServiceID) {
        return String.format("%s-%d-%s-%02d:%02d", r.routeId, GTFSHelper.getIntValue(direction), shortServiceID, s.hour, s.minutes);
    }
}
