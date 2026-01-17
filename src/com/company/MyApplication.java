package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.repositories.AdminRepository;
import com.company.view.CarPrinter;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final ICarController controller;
    private final AdminRepository adminRepo;

    private Role currentRole = null;

    public MyApplication(ICarController controller, AdminRepository adminRepo) {
        this.controller = controller;
        this.adminRepo = adminRepo;
    }


    private void startMenu() {
        System.out.println("\n========================================");
        System.out.println("WELCOME TO KZ.WHEELS");
        System.out.println("========================================");
        System.out.println("1. Continue as user");
        System.out.println("2. Login as admin");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }


    private void userMenu() {
        System.out.println("\nHOW CAN WE HELP YOU?");
        System.out.println("1. View all cars");
        System.out.println("2. Get car by ID");
        System.out.println("3. Sort cars");
        System.out.println("0. Back");
        System.out.print("Choose option: ");
    }


    private void adminMenu() {
        System.out.println("\nADMIN");
        System.out.println("1. View all cars");
        System.out.println("2. Get car by ID");
        System.out.println("3. Create new car");
        System.out.println("4. Sort cars");
        System.out.println("0. Logout");
        System.out.print("Choose option: ");
    }


    public void start() {
        while (true) {
            try {
                if (currentRole == null) {
                    startMenu();
                    handleStart(scanner.nextInt());
                } else if (currentRole == Role.USER) {
                    userMenu();
                    handleUser(scanner.nextInt());
                } else {
                    adminMenu();
                    handleAdmin(scanner.nextInt());
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number");
                scanner.nextLine();
            }
        }
    }

    private void handleStart(int option) {
        switch (option) {
            case 1 -> currentRole = Role.USER;
            case 2 -> adminLogin();
            case 0 -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option");
        }
    }


    private void handleUser(int option) {
        switch (option) {
            case 1 -> getAllCarsMenu();
            case 2 -> getCarByIdMenu();
            case 3 -> sortcars();
            case 0 -> currentRole = null;
            default -> System.out.println("Invalid option");
        }
        waitEnter();
    }


    private void handleAdmin(int option) {
        switch (option) {
            case 1 -> getAllCarsMenu();
            case 2 -> getCarByIdMenu();
            case 3 -> createCarMenu();
            case 4 -> sortcars();
            case 0 -> currentRole = null;
            default -> System.out.println("Invalid option");
        }
        waitEnter();
    }


    private void adminLogin() {
        System.out.print("Admin username: ");
        String username = scanner.next();

        System.out.print("Admin password: ");
        String password = scanner.next();

        if (adminRepo.authenticate(username, password)) {
            currentRole = Role.ADMIN;
            System.out.println("Admin login successful");
        } else {
            System.out.println("Wrong admin credentials");
        }
    }

    private void getAllCarsMenu() {
        String response = controller.getAllCars();
        CarPrinter.printAllCars(response);
    }

    private void getCarByIdMenu() {
        System.out.print("Enter car ID: ");
        int id = scanner.nextInt();

        String response = controller.getCar(id);
        if ("Car was not found!".equals(response)) {
            System.out.println("Car was not found!");
        } else {
            CarPrinter.printCarCard(response);
        }
    }

    private void createCarMenu() {
        System.out.println("CREATE NEW CAR");

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

        System.out.println(
                controller.createCar(vin, brand, model, branchCity,
                        year, color, engineType,
                        engineVolume, mileage, salePrice, status)
        );
    }

    public void sortcars() {
        System.out.println("SORTING CARS");
    }

    private void waitEnter() {
        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}
