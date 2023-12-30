package com.noisy.noisy.Response;

import java.time.LocalDateTime;

public class DateTimePack {
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer minute;

    public DateTimePack(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public DateTimePack(LocalDateTime time) {
        this.year = time.getYear();
        this.month = time.getMonthValue();
        this.day = time.getDayOfMonth();
        this.hour = time.getHour();
        this.minute = time.getMinute();
    }

    public DateTimePack(DateTimePack time) {
        this.year = time.getYear();
        this.month = time.getMonth();
        this.day = time.getDay();
        this.hour = time.getHour();
        this.minute = time.getMinute();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }
}
