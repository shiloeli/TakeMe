package com.example.takeme;

public class Tremp {
    String src, dest, day, hour, date, seats;

    public Tremp(){

    }

    public Tremp(String src, String dest, String day, String hour, String date, String seats){
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
