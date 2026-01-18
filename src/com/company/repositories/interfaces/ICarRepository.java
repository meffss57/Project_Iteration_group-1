package com.company.repositories.interfaces;

import com.company.models.Car;
import java.util.List;

public interface ICarRepository {

    boolean createCar(Car car);

    Car getCar(int id);

    List<Car> getAllCars();

    List<Car> sortCars();

    List<Car> filterByBrand(String car_brand);

    List<Car> filterByCity(String car_city);

    List<Car> filterByYear(int car_year);

    List<Car> filterByEngineType(String car_engine_type);

    List<Car> filterByPriceRange(double car_price_low, double car_price_high);

    List<String> getAvailableBrands();

    List<String> getAvailableCities();

    List<String> getAvailableEngineTypes();
}
