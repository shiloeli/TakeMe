package com.example.takeme;

public class User {
    private String cv;
    private String userName;
    private String email;
    private String name;
    private String lastName;
    private String phone;
    private String carType;
    private Boolean gender;
    private Boolean driver;
    private Boolean passenger;




    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.userName = username;
        this.email = email;
    }
    public User(String cv, String name, String email, String lastName, String phone, String carType, Boolean gender, Boolean driver, Boolean passenger)
    {
        this.cv=cv;
        this.name=name;
        this.lastName=lastName;
        this.phone=phone;
        this.carType=carType;
        this.gender=gender;
        this.driver=driver;
        this.passenger=passenger;
        this.email=email;

    }

}