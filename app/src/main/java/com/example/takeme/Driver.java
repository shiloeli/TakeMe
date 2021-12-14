package com.example.takeme;

public class Driver extends User {
   Car myCar;
   int rank;

   public Driver(){}
    public Driver(String name, String lastName, String email, String phone, String id, Boolean gender, int carNumber, String carType, String carColor)
    {
        super(name, lastName, email, phone, id, gender);
        this.myCar=new Car(carColor, carType,carNumber);
        this.rank = 0;
    }

//    public int getCarNumber()
//    {
//        return myCar.getCarNumber();
//    }

    public Car getMyCar() {
        return myCar;
    }

    public int getRank(){
        return rank;
    }


}
