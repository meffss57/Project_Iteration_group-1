package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.view.CarPrinter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final ICarController controller;

    private Role currentRole = Role.USER;

    public MyApplication(ICarController controller) {
        this.controller = controller;
    }


    private void startMenu() {
        System.out.println("=================================");
        System.out.println("WELCOME TO KZ.WHEELS");
        System.out.println("=================================");
        System.out.println("1. Continue as user");
        System.out.println("2. Login as admin");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private void userMenu() {
        System.out.println("\nWELCOME DEAR CUSTOMER");
        System.out.println("1. View all cars");
        System.out.println("2. Get car by ID");
        System.out.println("3. Sort cars by price");
        System.out.println("4. Buy car");
        System.out.println("0. Back");
        System.out.print("Choose option: ");
    }

    private void adminMenu() {
        System.out.println("\nADMIN MENU");
        System.out.println("1. View all cars");
        System.out.println("2. Get car by ID");
        System.out.println("3. Create car");
        System.out.println("0. Back");
        System.out.print("Choose option: ");
    }

    private void adminLogin() {
        System.out.print("Enter admin password: ");
        String password = scanner.next();

        if ("0101".equals(password)) {
            currentRole = Role.ADMIN;
            System.out.println("Admin access granted");
        } else {
            System.out.println("Wrong password");
        }
    }
    private void runUserMenu() {
        while (true) {
            userMenu();
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> getAllCarsMenu();
                case 2 -> getCarByIdMenu();
                case 3 -> sortCars();
                case 4 -> buyCar();
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }


    private void handleAdminOption(int option) {
        switch (option) {
            case 1 -> getAllCarsMenu();
            case 2 -> getCarByIdMenu();
            case 3 -> createCarMenu();
            case 0 -> {
                currentRole = Role.USER;
                System.out.println("Logged out");
            }
            default -> System.out.println("Invalid option");
        }
    }

    public void start() {
        while (true) {
            try {
                if (currentRole == Role.USER) {
                    startMenu();
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1 -> {
                            runUserMenu();
                        }
                        case 2 -> adminLogin();
                        case 0 -> {
                            System.out.println("Goodbye!");
                            return;
                        }
                        default -> System.out.println("Invalid option");
                    }

                } else {
                    adminMenu();
                    handleAdminOption(scanner.nextInt());
                }

            } catch (InputMismatchException e) {
                System.out.println("Input must be a number");
                scanner.nextLine();
            }
        }
    }

    /* ================= EXISTING FUNCTIONS ================= */

    private void getAllCarsMenu() {
        System.out.println("\n=================================");
        System.out.println("LIST OF ALL CARS");
        System.out.println("=================================");

        String response = controller.getAllCars();
        CarPrinter.printAllCars(response);
    }

    private void getCarByIdMenu() {
        System.out.print("\nEnter car ID: ");
        int id = scanner.nextInt();

        System.out.println("\n=================================");
        System.out.println("CAR SEARCH RESULT");
        System.out.println("=================================");

        String response = controller.getCar(id);

        if ("Car was not found!".equals(response)) {
            System.out.println("Car was not found!");
        } else {
            CarPrinter.printCarCard(response);
        }
    }

    private void createCarMenu() {
        System.out.println("\nCREATE NEW CAR");

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

        System.out.println("\n" + response);
    }
    private void sortCars() {
        System.out.println("\n=================================");
        System.out.println("LIST OF ALL SORTED CARS");
        System.out.println("=================================");

        String response = controller.sortCars();
        CarPrinter.printAllCars(response);
    }
    private void buyCar(){
        System.out.print("\nEnter car ID: ");
        int id = scanner.nextInt();

        System.out.println("\n=================================");
        System.out.println("CAR SEARCH RESULT");
        System.out.println("=================================");

        String response = controller.getCar(id);

        if ("Car was not found!".equals(response)) {
            System.out.println("Car was not found!");
        } else {
            CarPrinter.printCarCard(response);
        }
    }
}
