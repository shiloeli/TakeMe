package com.example.takeme;

import android.text.BoringLayout;
import android.widget.EditText;

public class User {
    String name, lastName, email, phone, id, carType;
    Boolean gender;

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

    public User(String name, String lastName, String email, String phone, String id, Boolean gender)
    {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.gender = gender;
    }
    public User(){};


}