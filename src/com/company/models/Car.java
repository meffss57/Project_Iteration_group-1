package com.company.models;


public class Car {
    private int carId;
    private String vin;
    private String brand;
    private String model;
    private String branchCity;
    private int year;
    private String color;
    private String engineType;
    private double engineVolume;
    private int mileage;
    private double salePrice;
    private String status;

    public Car() {}

    public Car(String vin, String brand, String model, String branchCity, int year, String color,
               String engineType, double engineVolume, int mileage, double salePrice, String status) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.branchCity = branchCity;
        this.year = year;
        this.color = color;
        this.engineType = engineType;
        this.engineVolume = engineVolume;
        this.mileage = mileage;
        this.salePrice = salePrice;
        this.status = status;
    }

    public Car(int carId, String vin, String brand, String model, String branchCity, int year, String color, String engineType, double engineVolume, int mileage, double salePrice, String status) {
        this(vin, brand, model, branchCity, year, color, engineType, engineVolume, mileage, salePrice, status);
        this.carId = carId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(String branchCity) {
        this.branchCity = branchCity;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", vin='" + vin + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", branchCity='" + branchCity + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", engineType='" + engineType + '\'' +
                ", engineVolume=" + engineVolume +
                ", mileage=" + mileage +
                ", salePrice=" + salePrice +
                ", status='" + status + '\'' +
                '}';
    }



}
