package com.example.takeme;

public class Car {
    String carColor;
    String carType;
    int carNumber;

    public Car(){}

    public Car(String carColor, String carType, int carNumber) {
        this.carColor = carColor;
        this.carType = carType;
        this.carNumber = carNumber;
    }

    public String getCarColor() {
        return carColor;
    }

//    public void setCarColor(String carColor) {
//        this.carColor = carColor;
//    }

    public String getCarType() {
        return carType;
    }

//    public void setCarType(String carType) {
//        this.carType = carType;
//    }

    public int getCarNumber() {
        return carNumber;
    }

//    public void setCarNumber(int carNumber) {
//        this.carNumber = carNumber;
//    }
}
