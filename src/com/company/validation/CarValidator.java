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
            String status,
            String category
    ) {

        if (vin == null || vin.isBlank() || vin.length() != 17) {
            throw new IllegalArgumentException("VIN must be exactly 17 characters");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (branchCity == null || branchCity.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        if (year < 1950 || year > 2026) {
            throw new IllegalArgumentException("Year must be in interval 1950 and 2026");
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color cannot be empty");
        }
        if (!(engineType.equalsIgnoreCase("petrol")
                || engineType.equalsIgnoreCase("diesel")
                || engineType.equalsIgnoreCase("electric"))) {
            throw new IllegalArgumentException("Engine type must be either petrol or diesel or electric");
        }
        if (engineVolume <= 0 || engineVolume >= 10) {
            throw new IllegalArgumentException("Engine volume must be positive");
        }
        if (mileage < 0) {
            throw new IllegalArgumentException("Mileage must be positive");
        }
        if (salePrice <= 0) {
            throw new IllegalArgumentException("Sale price must be positive");
        }

        if (status == null || !(status.equalsIgnoreCase("sold")
                || status.equalsIgnoreCase("archived")
                || status.equalsIgnoreCase("available"))) {
            throw new IllegalArgumentException("Status must be 'sold' or 'archived' or 'available'");
        }

        if (category == null || !(category.equalsIgnoreCase("Sedan")
                || category.equalsIgnoreCase("SUV")
                || category.equalsIgnoreCase("Hatchback")
                || category.equalsIgnoreCase("Electric"))) {
            throw new IllegalArgumentException("Category must be 'SUV' or 'Sedan' or 'Hatchback' or 'Electric'");
        }
    }
}
