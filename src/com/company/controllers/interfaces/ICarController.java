package com.company.controllers.interfaces;

public interface ICarController {

    String createCar(String vin, String brand, String model, String branchCity,
                     int year, String color, String engineType,
                     double engineVolume, int mileage,
                     double salePrice, String status);

    String getCar(int carId);

    String getAllCars();

    String buyCar(int carId);

    String FilterCarsByASC();

    String filterByBrand(String car_brand);
    String filterByCity(String car_city);
    String filterByYear(int car_year);
    String filterByEngineType(String car_engine_type);
    String filterByPriceRange(double car_price_low, double car_price_high);

    String getAvailableBrands();
    String getAvailableCities();
    String getAvailableEngineTypes();

}
