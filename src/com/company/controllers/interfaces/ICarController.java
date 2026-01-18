package com.company.controllers.interfaces;

public interface ICarController {

    String createCar(String vin, String brand, String model, String branchCity,
                     int year, String color, String engineType,
                     double engineVolume, int mileage,
                     double salePrice, String status);

    String getCar(int carId);

    String getAllCars();

    String sortCars();

    String buyCar(int carId);
}
