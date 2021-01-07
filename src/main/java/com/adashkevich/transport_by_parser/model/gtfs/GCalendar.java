package com.adashkevich.transport_by_parser.model.gtfs;

import com.adashkevich.transport_by_parser.helper.GTFSHelper;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GDay;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Arrays;

public class GCalendar {

    public static final String WEEKDAYS_SERVICE_ID = "weekdays";
    public static final String WEEKEND_SERVICE_ID = "weekend";

    @CsvBindByName(column = "service_id")
    private String serviceID;
    @CsvBindByName(column = "monday")
    private int monday = 0;
    @CsvBindByName(column = "tuesday")
    private int tuesday = 0;
    @CsvBindByName(column = "wednesday")
    private int wednesday = 0;
    @CsvBindByName(column = "thursday")
    private int thursday = 0;
    @CsvBindByName(column = "friday")
    private int friday = 0;
    @CsvBindByName(column = "saturday")
    private int saturday = 0;
    @CsvBindByName(column = "sunday")
    private int sunday = 0;
    @CsvBindByName(column = "start_date")
    private String startDate;
    @CsvBindByName(column = "end_date")
    private String endDate;

    public static String getShortServiceID(String serviceID) {
        switch (serviceID) {
            case WEEKDAYS_SERVICE_ID:
                return "wd";
            case WEEKEND_SERVICE_ID:
                return "we";
            default:
                return "none";
        }
    }

    public static GCalendar forDays(GDay... days) {
        GCalendar gCalendar = new GCalendar();
        Arrays.stream(days).forEach(d -> {
            switch (d) {
                case MONDAY:
                    gCalendar.monday = 1;
                    break;
                case TUESDAY:
                    gCalendar.tuesday = 1;
                    break;
                case WEDNESDAY:
                    gCalendar.wednesday = 1;
                    break;
                case THURSDAY:
                    gCalendar.thursday = 1;
                    break;
                case FRIDAY:
                    gCalendar.friday = 1;
                    break;
                case SATURDAY:
                    gCalendar.saturday = 1;
                    break;
                case SUNDAY:
                    gCalendar.sunday = 1;
                    break;
            }
        });
        return gCalendar;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartLocalDate() {
        return StringUtils.isNoneBlank(startDate) ? LocalDate.parse(startDate, GTFSHelper.DATE_FORMATTER) : null;
    }

    public void setStartLocalDate(@NotNull LocalDate startDate) {
        this.startDate = startDate.format(GTFSHelper.DATE_FORMATTER);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndLocalDate() {
        return StringUtils.isNoneBlank(endDate) ? LocalDate.parse(endDate, GTFSHelper.DATE_FORMATTER) : null;
    }

    public void setEndLocalDate(LocalDate endDate) {
        this.endDate = endDate.format(GTFSHelper.DATE_FORMATTER);;
    }
}
