package com.example.takeme;

import java.util.ArrayList;

public class Driver extends User {
   Car myCar;
   int rank;
   ArrayList<String> trempsIds;

   public Driver(){}
    public Driver(String name, String lastName, String email, String phone, String id, Boolean gender, int carNumber, String carType, String carColor)
    {
        super(name, lastName, email, phone, id, gender);
        this.myCar=new Car(carColor, carType,carNumber);
        this.rank = 0;
        this.trempsIds= new ArrayList<String>();
    }

    public Car getMyCar() {
        return myCar;
    }

    public int getRank(){
        return rank;
    }


}
