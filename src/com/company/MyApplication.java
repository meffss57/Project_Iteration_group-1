package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.models.Car;
import com.company.view.CarPrinter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final ICarController controller;

    public MyApplication(ICarController controller) {
        this.controller = controller;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("CAR DEALERSHIP SYSTEM");
        System.out.println("========================================");
        System.out.println("1. View all cars");
        System.out.println("2. Get car by ID");
        System.out.println("3. Create new car");
        System.out.println("4. Sort cars");
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Choose option: ");
    }

    public void start() {
        while (true) {
            mainMenu();

            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> getAllCarsMenu();
                    case 2 -> getCarByIdMenu();
                    case 3 -> createCarMenu();
                    case 4 -> sortcars();
                    case 0 -> {
                        System.out.println("\nGoodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option");
                }

            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("\nPress ENTER to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    public void getAllCarsMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("LIST OF ALL CARS");
        System.out.println("========================================");

        String response = controller.getAllCars();
        CarPrinter.printAllCars(response);
    }

    public void getCarByIdMenu() {
        System.out.print("Please enter id: ");
        int id = scanner.nextInt();

        System.out.println();
        System.out.println("========================================");
        System.out.println("CAR SEARCH RESULT");
        System.out.println("========================================");

        String response = controller.getCar(id);

        if ("Car was not found!".equals(response)) {
            System.out.println("Car was not found!");
        } else {
            CarPrinter.printCarCard(response);
        }
    }

    private void createCarMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("CREATE NEW CAR");
        System.out.println("========================================");

        System.out.print("VIN: ");
        String vin = scanner.next();

        System.out.print("Brand: ");
        String brand = scanner.next();

        System.out.print("Model: ");
        String model = scanner.next();

        System.out.print("Branch city: ");
        String branchCity = scanner.next();

        System.out.print("Year: ");
        int year = scanner.nextInt();

        System.out.print("Color: ");
        String color = scanner.next();

        System.out.print("Engine type: ");
        String engineType = scanner.next();

        System.out.print("Engine volume: ");
        double engineVolume = scanner.nextDouble();

        System.out.print("Mileage: ");
        int mileage = scanner.nextInt();

        System.out.print("Sale price: ");
        double salePrice = scanner.nextDouble();

        System.out.print("Status: ");
        String status = scanner.next();

        String response = controller.createCar(
                vin, brand, model, branchCity,
                year, color, engineType,
                engineVolume, mileage, salePrice, status
        );

        System.out.println();
        System.out.println("========================================");
        System.out.println(response);
        System.out.println("========================================");
    }

    public void sortcars() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("SORTING CARS ");
        System.out.println("========================================");



    }
}


