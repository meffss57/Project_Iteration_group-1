package com.company.validation;

public class CarValidator {

    public static void validateCreateCar(
            String vin,
            String brand,
            String model,
            String branchCity,
            int year,
            String color,
            String engineType,
            double engineVolume,
            int mileage,
            double salePrice,
            String status
    ) {

        if(vin == null || vin.isBlank() || vin.length() != 17){
            throw new IllegalArgumentException("VIN must be exactly 17 characters");
        }
        if(brand == null){
            throw new IllegalArgumentException(("Brand cannot be empty"));
        }
        if(model == null){
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if(branchCity == null){
            throw new IllegalArgumentException("City cannot be empty");
        }
        if(year < 1950 || year > 2026){
            throw new IllegalArgumentException("Year must be in interval 1950 and 2026");
        }
        if(color == null){
            throw new IllegalArgumentException("Color cannot be empty");
        }
        if(engineType == null){
            throw new IllegalArgumentException("Engine type cannot be empty");
        }
        if(engineVolume <= 0 || engineVolume >= 10){
            throw new IllegalArgumentException("Engine volume must be positive");
        }
        if(mileage < 0){
            throw new IllegalArgumentException("Mileage must be positive");
        }
        if(salePrice <= 0){
            throw new IllegalArgumentException("Sale price must be positive");
        }
        if(status == null){
            throw new IllegalArgumentException("Status cannot be empty");
        }
    }
}
