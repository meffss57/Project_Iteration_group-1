package com.company.controllers;

import com.company.models.Car;
import com.company.controllers.interfaces.ICarController;
import com.company.repositories.interfaces.ICarRepository;

import java.util.List;

public class CarController implements ICarController {
    private final ICarRepository repo;

    public CarController(ICarRepository repo) { // Dependency Injection
        this.repo = repo;
    }

    public String createCar(String vin, String brand, String model, String branchCity, int year, String color,String engineType, double engineVolume, int mileage, double salePrice, String status) {

        Car car = new Car( vin,  brand,  model,  branchCity,  year, color, engineType,  engineVolume,  mileage,  salePrice,  status);

        boolean created = repo.createCar(car);

        return (created ? "Car was created!" : "Car creation was failed!");
    }

    @Override
    public String getCar(int car_id) {
        Car car = repo.getCar(car_id);

        return (car == null ? "Car was not found!" : car.toString());
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
    public String sortingCars(){
        return "something";
    }
}
