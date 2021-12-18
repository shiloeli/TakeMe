package com.example.takeme;

import java.util.ArrayList;

public class Tremp {
    String src, dest, day, hour, date, seats;
    ArrayList<String> passengersIds;
    public Tremp(){

    }

    public Tremp(String src, String dest, String day, String hour, String date, String seats){
        this.passengersIds = new ArrayList<String>();
        passengersIds.add("debug");
        passengersIds.add("debug2222");
        this.src = src;
        this.dest = dest;
        this.day = day;
        this.hour = hour;
        this.date = date;
        this.seats = seats;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public String getDay(){
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getDate() {
        return date;
    }

    public String getSeats() {
        return seats;
    }
}
