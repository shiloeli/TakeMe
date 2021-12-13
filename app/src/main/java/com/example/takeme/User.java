package com.example.takeme;

import android.text.BoringLayout;
import android.widget.EditText;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
    String name, lastName, email, phone, id;
    Boolean gender,isDriver;

    public String getGender()
    {
        if (gender == true)
            return "Male";
        return "Female";
    }

    public String getName()
    {
        return name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getId()
    {
        return id;
    }
    public String getIsDriver()
    {
        if (gender == true)
            return "Driver";
        return "User";
    }

    public User(){

    }
    public User(String name, String lastName, String email, String phone, String id, Boolean gender,Boolean isDriver)
    {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.gender = gender;
        this.isDriver=isDriver;

    }
    public User(User other)
    {
        this.name = other.name;
        this.lastName = other.lastName;
        this.email = other.email;
        this.phone = other.phone;
        this.id = other.id;
        this.gender = other.gender;
        this.isDriver=other.isDriver;
    }
//    public User(HashMap userDetails)
//    {
//        String gender = (String) userDetails.get("gender");
//        User(userDetails.get("name"),userDetails.get("lastName"),userDetails.get("email"),userDetails.get("phone"),userDetails.get("id"),userDetails.get())
//
//    }

}