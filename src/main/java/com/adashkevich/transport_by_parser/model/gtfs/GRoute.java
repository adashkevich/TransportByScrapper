package com.adashkevich.transport_by_parser.model.gtfs;

import com.adashkevich.transport_by_parser.model.gtfs.emum.GContinuousDropOff;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GContinuousPickup;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GRouteType;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GRoute {

    @CsvBindByName(column = "route_id")
    private String routeID;
    @CsvBindByName(column = "agency_id")
    private String agencyID;
    @CsvBindByName(column = "route_short_name")
    private String routeShortName;
    @CsvBindByName(column = "route_long_name")
    private String routeLongName;
    @CsvBindByName(column = "route_desc")
    private String routeDesc;
    @CsvBindByName(column = "route_type")
    private int routeType;
    @CsvBindByName(column = "route_url")
    private String routeUrl;
    @CsvBindByName(column = "route_color")
    private String routeColor;
    @CsvBindByName(column = "route_text_color")
    private String routeTextColor;
    @CsvBindByName(column = "route_sort_order")
    private Integer routeSortOrder;
    @CsvBindByName(column = "continuous_pickup")
    private Integer continuousPickup;
    @CsvBindByName(column = "continuous_drop_off")
    private Integer continuousDropOff;

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public GRouteType getRouteTypeEnum() {
        return GRouteType.valueOf(routeType);
    }

    public void setRouteTypeEnum(GRouteType routeType) {
        if(routeType != null) this.routeType = routeType.value;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public int getRouteSortOrder() {
        return routeSortOrder;
    }

    public void setRouteSortOrder(int routeSortOrder) {
        this.routeSortOrder = routeSortOrder;
    }

    public Integer getContinuousPickup() {
        return continuousPickup;
    }

    public void setContinuousPickup(Integer continuousPickup) {
        this.continuousPickup = continuousPickup;
    }

    public GContinuousPickup getContinuousPickupEnum() {
        return GContinuousPickup.valueOf(continuousPickup);
    }

    public void setContinuousPickupEnum(GContinuousPickup continuousPickup) {
        this.continuousPickup = continuousPickup.value;
    }

    public Integer getContinuousDropOff() {
        return continuousDropOff;
    }

    public void setContinuousDropOff(Integer continuousDropOff) {
        this.continuousDropOff = continuousDropOff;
    }

    public GContinuousDropOff getContinuousDropOffEnum() {
        return GContinuousDropOff.valueOf(continuousDropOff);
    }

    public void setContinuousDropOffEnum(GContinuousDropOff continuousDropOff) {
        this.continuousDropOff = continuousDropOff.value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
