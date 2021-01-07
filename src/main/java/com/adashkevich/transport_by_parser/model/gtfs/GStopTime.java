package com.adashkevich.transport_by_parser.model.gtfs;

import com.adashkevich.transport_by_parser.model.gtfs.emum.GTimepoint;
import com.opencsv.bean.CsvBindByName;

public class GStopTime {

    @CsvBindByName(column = "trip_id")
    private String tripID;
    @CsvBindByName(column = "arrival_time")
    private String arrivalTime;
    @CsvBindByName(column = "departure_time")
    private String departureTime;
    @CsvBindByName(column = "stop_id")
    private String stopID;
    @CsvBindByName(column = "stop_sequence")
    private int stopSequence;
    @CsvBindByName(column = "stop_headsign")
    private String stopHeadsign;
    @CsvBindByName(column = "pickup_type")
    private Integer pickupType;
    @CsvBindByName(column = "drop_off_type")
    private Integer dropOffType;
    @CsvBindByName(column = "continuous_pickup")
    private Integer continuousPickup;
    @CsvBindByName(column = "continuous_drop_off")
    private Integer continuousDropOff;
    @CsvBindByName(column = "shape_dist_traveled")
    private Double shapeDistTraveled;
    @CsvBindByName(column = "timepoint")
    private Integer timepoint;

    public int getArrivalTimestamp() {
        return arrivalTime != null ? Integer.parseInt(arrivalTime.substring(0, 2)) * 60
                + Integer.parseInt(arrivalTime.substring(3, 5)) : Integer.MAX_VALUE;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setArrivalTime(short h, short m) {
        setArrivalTime((short) 0, h, m);
    }

    public void setArrivalTime(short day, short h, short m) {
        this.arrivalTime = String.format("%02d:%02d:00", day * 24 + h, m);
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setDepartureTime(short h, short m) {
        setDepartureTime((short) 0, h, m);
    }

    public void setDepartureTime(short day, short h, short m) {
        this.departureTime = String.format("%02d:%02d:00", day * 24 + h, m);
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public String getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(String stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
    }

    public Integer getPickupType() {
        return pickupType;
    }

    public void setPickupType(Integer pickupType) {
        this.pickupType = pickupType;
    }

    public Integer getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(Integer dropOffType) {
        this.dropOffType = dropOffType;
    }

    public Integer getContinuousPickup() {
        return continuousPickup;
    }

    public void setContinuousPickup(Integer continuousPickup) {
        this.continuousPickup = continuousPickup;
    }

    public Integer getContinuousDropOff() {
        return continuousDropOff;
    }

    public void setContinuousDropOff(Integer continuousDropOff) {
        this.continuousDropOff = continuousDropOff;
    }

    public Double getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(Double shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }

    public Integer getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(Integer timepoint) {
        this.timepoint = timepoint;
    }

    public GTimepoint getTimepointEnum() {
        return timepoint != null ? GTimepoint.valueOf(timepoint) : null;
    }

    public void setTimepointEnum(GTimepoint timepoint) {
        this.timepoint = timepoint.value;
    }
}
