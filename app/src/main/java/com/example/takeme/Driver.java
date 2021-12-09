package com.example.takeme;

public class Driver extends User {
   Car myCar;
   short rank;
    public Driver(String name, String lastName, String email, String phone, String id, Boolean gender, int carNumber, String carType, String carColor)
    {
        super(name, lastName, email, phone, id, gender);
        this.myCar=new Car(carColor, carType,carNumber);
        rank = 0;
    }

    public int getCarNumber()
    {
        return myCar.getCarNumber();
    }

    public void setCarNumber(int carNumber)
    {
        this.myCar.carNumber = carNumber;
    }

    public String getCarType()
    {
        return carType;
    }

    public void setCarType(String carType) {
        this.myCar.setCarType(carType);
    }

    public String getCarColor()
    {
        return myCar.getCarColor();
    }

    public void setCarColor(String carColor)
    {
        this.myCar.setCarColor(carColor);
    }

}
