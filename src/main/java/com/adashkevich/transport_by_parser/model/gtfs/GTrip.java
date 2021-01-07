package com.adashkevich.transport_by_parser.model.gtfs;

import com.opencsv.bean.CsvBindByName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GTrip {

    @CsvBindByName(column = "route_id")
    private String routID;
    @CsvBindByName(column = "service_id")
    private String serviceID;
    @CsvBindByName(column = "trip_id")
    private String tripID;
    @CsvBindByName(column = "trip_headsign")
    private String tripHeadsign;
    @CsvBindByName(column = "trip_short_name")
    private String tripShortName;
    @CsvBindByName(column = "direction_id")
    private Integer directionID;
    @CsvBindByName(column = "block_id")
    private String blockID;
    @CsvBindByName(column = "shape_id")
    private String shapeID;
    @CsvBindByName(column = "wheelchair_accessible")
    private Integer wheelchairAccessible;
    @CsvBindByName(column = "bikes_allowed")
    private Integer bikesAllowed;

    private List<GStopTime> stopTimes = new ArrayList<>();

    public String getRoutID() {
        return routID;
    }

    public void setRoutID(String routID) {
        this.routID = routID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    public String getTripShortName() {
        return tripShortName;
    }

    public void setTripShortName(String tripShortName) {
        this.tripShortName = tripShortName;
    }

    public Integer getDirectionID() {
        return directionID;
    }

    public void setDirectionID(Integer directionID) {
        this.directionID = directionID;
    }

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public String getShapeID() {
        return shapeID;
    }

    public void setShapeID(String shapeID) {
        this.shapeID = shapeID;
    }

    public Integer getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public void setWheelchairAccessible(Integer wheelchairAccessible) {
        this.wheelchairAccessible = wheelchairAccessible;
    }

    public Integer getBikesAllowed() {
        return bikesAllowed;
    }

    public void setBikesAllowed(Integer bikesAllowed) {
        this.bikesAllowed = bikesAllowed;
    }

    public void addStopTime(GStopTime st) {
        stopTimes.add(st);
    }

    public List<GStopTime> getStopTimes() {
        return stopTimes;
    }

    public Optional<GStopTime> getLastStopTime() {
        return stopTimes.isEmpty() ? Optional.empty() : Optional.of(stopTimes.get(stopTimes.size() - 1));
    }
}
