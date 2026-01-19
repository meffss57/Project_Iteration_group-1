package com.company.controllers;

import com.company.models.Car;
import com.company.controllers.interfaces.ICarController;
import com.company.repositories.interfaces.ICarRepository;

import java.util.Collections;
import java.util.List;

public class CarController implements ICarController{
    private final ICarRepository repo;

    public CarController(ICarRepository repo) { // Dependency Injection
        this.repo = repo;
    }

    public String createCar(String vin, String brand, String model, String branchCity, int year, String color,String engineType, double engineVolume, int mileage, double salePrice, String status) {

        Car car = new Car( vin,  brand,  model,  branchCity,  year, color, engineType,  engineVolume,  mileage,  salePrice,  status);

        boolean created = repo.createCar(car);

        return (created ? "Car was created!" : "Car creation was failed!");
    }

    private String carsToString(List<Car> cars) {
        if (cars == null || cars.isEmpty()) return "Car was not found!";
        StringBuilder sb = new StringBuilder();
        for (Car c : cars) sb.append(c).append("\n");
        return sb.toString();
    }

    @Override
    public String filterByBrand(String car_brand) {
        return carsToString(repo.filterByBrand(car_brand));
    }

    @Override
    public String filterByCity(String car_city) {
        return carsToString(repo.filterByCity(car_city));
    }

    @Override
    public String filterByYear(int car_year) {
        return carsToString(repo.filterByYear(car_year));
    }

    @Override
    public String filterByEngineType(String car_engine_type) {
        return carsToString(repo.filterByEngineType(car_engine_type));
    }

    @Override
    public String filterByPriceRange(double car_price_low, double car_price_high) {
        return carsToString(repo.filterByPriceRange(car_price_low, car_price_high));
    }

    @Override public String getAvailableBrands(){
        return String.join("\n", repo.getAvailableBrands());
    }

    @Override public String getAvailableCities(){
        return String.join("\n", repo.getAvailableCities());
    }

    @Override public String getAvailableEngineTypes(){
        return String.join("\n", repo.getAvailableEngineTypes());
    }


    @Override
    public String getCar(int car_id) {
        Car car = repo.getCar(car_id);

        return (car == null ? "Car was not found!" : car.toString());
    }

    public String buyCar(int car_id){
        Car car = repo.buyCar(car_id);

        return (car == null ? "Car has been sold or is archived!" : car.toString());
    }

    @Override
    public String getAllCars() {
        List<Car> cars = repo.getAllCars();

        StringBuilder response = new StringBuilder();
        for (Car car : cars) {
            response.append(car.toString()).append("\n");
        }

        return response.toString();
    }

    @Override
    public String FilterCarsByASC() {
        List<Car> cars = repo.FilterCarsByASC();


        StringBuilder response = new StringBuilder();
        for (Car car : cars) {
            response.append(car.toString()).append("\n");
        }

        return response.toString();
    }



}
