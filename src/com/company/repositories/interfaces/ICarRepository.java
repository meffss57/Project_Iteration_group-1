package com.company.repositories.interfaces;

import com.company.models.Car;

import java.util.List;

public interface ICarRepository {
    boolean createCar(Car car);
    Car getCar(int id);
    List<Car> getAllCars();
    List<Car> sortCars();
    Car buyCar(int id);
}