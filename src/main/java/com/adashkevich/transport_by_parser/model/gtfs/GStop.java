package com.adashkevich.transport_by_parser.model.gtfs;

import com.adashkevich.transport_by_parser.model.gtfs.emum.GLocationType;
import com.opencsv.bean.CsvBindByName;

public class GStop {

    @CsvBindByName(column = "stop_id")
    private String stopID;
    @CsvBindByName(column = "stop_code")
    private String stopCode;
    @CsvBindByName(column = "stop_name")
    private String stopName;
    @CsvBindByName(column = "stop_desc")
    private String stopDesc;
    @CsvBindByName(column = "stop_lat")
    private Double stopLat;
    @CsvBindByName(column = "stop_lon")
    private Double stopLon;
    @CsvBindByName(column = "zone_id")
    private String zoneID;
    @CsvBindByName(column = "stop_url")
    private String stopUrl;
    @CsvBindByName(column = "location_type")
    private Integer locationType;
    @CsvBindByName(column = "parent_station")
    private String parentStation;
    @CsvBindByName(column = "stop_timezone")
    private String stopTimezone;
    @CsvBindByName(column = "wheelchair_boarding")
    private Integer wheelchairBoarding = 0;
    @CsvBindByName(column = "level_id")
    private String levelID;
    @CsvBindByName(column = "platform_code")
    private String platformCode;

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }

    public String getStopCode() {
        return stopCode;
    }

    public void setStopCode(String stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public double getStopLat() {
        return stopLat;
    }

    public void setStopLat(double stopLat) {
        this.stopLat = stopLat;
    }

    public double getStopLon() {
        return stopLon;
    }

    public void setStopLon(double stopLon) {
        this.stopLon = stopLon;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public String getStopUrl() {
        return stopUrl;
    }

    public void setStopUrl(String stopUrl) {
        this.stopUrl = stopUrl;
    }

    public int getLocationType() {
        return locationType;
    }

    public void setLocationType(int locationType) {
        this.locationType = locationType;
    }

    public GLocationType getLocationTypeEnum() {
        return locationType != null ? GLocationType.valueOf(locationType) : null;
    }

    public void setLocationTypeEnum(GLocationType locationType) {
        this.locationType = locationType.value;
    }

    public String getParentStation() {
        return parentStation;
    }

    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    public String getStopTimezone() {
        return stopTimezone;
    }

    public void setStopTimezone(String stopTimezone) {
        this.stopTimezone = stopTimezone;
    }

    public int getWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public void setWheelchairBoarding(int wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}
