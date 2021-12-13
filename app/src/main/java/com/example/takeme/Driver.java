package com.example.takeme;

public class Driver extends User {
   Car myCar;
   short rank;
    public Driver(User user,int carNumber, String carType, String carColor)
    {
        super(user);
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
    public short getRank()
    {
        return rank;
    }

}
