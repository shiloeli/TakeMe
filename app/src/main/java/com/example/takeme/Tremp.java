package com.example.takeme;

import java.util.ArrayList;

public class Tremp {
    String src, dest, hour, date;
    int seats,emptySeats;
    String driverId;
    ArrayList<String> passengersIds;

    public int getEmptySeats() {
        return emptySeats;
    }

    public Tremp(){}

    public Tremp(String src, String dest, String hour, String date, int seats,String driverId){
        this.passengersIds = new ArrayList<String>();
        this.driverId=driverId;
        this.src = src;
        this.dest = dest;
        this.hour = hour;
        this.date = date;
        this.seats = seats;
        this.emptySeats = seats;
    }
    public boolean containPassenger(String uid)
    {
        return passengersIds.contains(uid);
    }

    public ArrayList<String> getPassengersIds(){return this.passengersIds;}
    public String getSrc() {
        return src;
    }

    public String getDriverId() {return driverId;}
    public String getDest() {
        return dest;
    }

    public String getHour() {
        return hour;
    }

    public String getDate() {
        return date;
    }

    public int getSeats() {
        return seats;
    }
}
