package com.example.takeme;

import android.widget.EditText;

public class User {

    String name, lastName, email, phone, id, carType;
    Driver driver;
    boolean passenger;
    public User(){

    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getId(){
        return id;
    }

    public Driver getDriver(){
        return driver;
    }

    public boolean getPassenger(){
        return passenger;
    }


    public User(String name, String lastName, String email, String phone, String id, String catType, Boolean driver, Boolean passenger){
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.passenger = passenger;

        if(driver){
            this.driver = new Driver(carType);
        }
    }


}