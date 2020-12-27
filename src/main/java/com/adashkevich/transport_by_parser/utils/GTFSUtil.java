package com.adashkevich.transport_by_parser.utils;

import com.adashkevich.transport_by_parser.model.gtfs.*;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GContinuousDropOff;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GContinuousPickup;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GRouteType;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GLocationType;
import com.adashkevich.transport_by_parser.model.transport_by.Rout;
import com.adashkevich.transport_by_parser.model.transport_by.Stop;
import okhttp3.Request;

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
}
