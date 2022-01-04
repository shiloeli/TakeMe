package com.example.takeme;

import java.util.ArrayList;

public class Driver extends User {
    //Fields
    Car myCar;
    ArrayList<String> trempsIds;

    //Empty constructor
    public Driver() {
    }

    //Constructor
    public Driver(String name, String lastName, String email, String phone, String id, Boolean gender, int carNumber, String carType, String carColor, boolean isDriver) {
        super(name, lastName, email, phone, id, gender, isDriver);
        this.myCar = new Car(carColor, carType, carNumber);
        this.trempsIds = new ArrayList<String>();
    }

    public ArrayList<String> getTrempsIds() {
        return this.trempsIds;
    }

    public Car getMyCar() {
        return myCar;
    }
}

