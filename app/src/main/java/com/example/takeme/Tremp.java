package com.example.takeme;

import java.util.ArrayList;

public class Tremp {
    String src, dest, hour, date;
    int seats;
    ArrayList<String> passengersIds;
    public Tremp(){

    }

    public Tremp(String src, String dest, String hour, String date, int seats){
        this.passengersIds = new ArrayList<String>();
        this.src = src;
        this.dest = dest;
        this.hour = hour;
        this.date = date;
        this.seats = seats;
    }
    public ArrayList<String> getPassengersIds(){return this.passengersIds;}
    public String getSrc() {
        return src;
    }

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
