package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.models.Car;
import com.company.services.UserAuthService;
import com.company.view.CarPrinter;
import com.company.services.AdminAuthService;
import com.company.Role;


import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final ICarController controller;
    private final AdminAuthService authService;
    private final UserAuthService authService1;

    private Role currentRole;

    public MyApplication(ICarController controller, AdminAuthService authService, UserAuthService authService1) {
        this.controller = controller;
        this.authService = authService;
        this.authService1 = authService1;
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
        System.out.println("3. Buy car by ID");
        System.out.println("4. Filter cars");
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
        System.out.print("Enter admin login: ");
        String username = scanner.next();

        System.out.print("Enter admin password: ");
        String password = scanner.next();

        if (authService.username(username, password)) {
            currentRole = Role.ADMIN;
            System.out.println("Admin access granted");
        } else {
            System.out.println("Wrong login or password");
        }
    }

    private void userLogin() {
        System.out.print("Enter admin login: ");
        String username = scanner.next();

        System.out.print("Enter admin password: ");
        String password = scanner.next();

        if (authService1.username(username, password)) {
            currentRole = Role.USER;
            System.out.println("Admin access granted");
        } else {
            System.out.println("Wrong login or password");
        }
    }


    private void runUserMenu() {
        while (true) {
            userMenu();
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> getAllCarsMenu();
                case 2 -> getCarByIdMenu();
                case 3 -> buyCarByIdMenu();
                case 4 -> FilterCars();
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

    private void buyCarByIdMenu() {
        System.out.print("\nEnter car ID: ");
        int id = scanner.nextInt();

        System.out.println("\n=================================");
        System.out.println("CAR PURCHASE RESULT");
        System.out.println("=================================");

        String response = controller.buyCar(id);

        if ("Car has been sold or is archived!".equals(response)) {
            System.out.println("Сar has been sold or is archived!");
        } else {
            CarPrinter.printCarCard(response);
            System.out.println("Congratulations!");
        }
    }

    private void FilterCars(){
        System.out.println("\n=================================");
        System.out.println("Choose How to filter: ");
        System.out.println("1.Filter by Brand");
        System.out.println("2.Filter by City");
        System.out.println("3.Filter by year");
        System.out.println("4.Filter by engine_type");
        System.out.println("5.Filter by price");
        System.out.println("6.Filter by ascending order");
        System.out.println("0.Back");
        System.out.println("=================================");

        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice){
            case 1 -> FilterbyBrand();
            case 2 -> FilterbyCity();
            case 3 -> FilterbyYear();
            case 4 -> FilterbyEnginetype();
            case 5 -> FilterbyPrice();
            case 6 -> FilterCarsByASC();
            case 0 -> { return; }
            default -> System.out.println("Invalid option");
        }
    }

    private void FilterbyBrand() {
        scanner.nextLine();
        System.out.println("Available brands:\n" + controller.getAvailableBrands());

        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();

        String response = controller.filterByBrand(brand);
        if ("Car was not found!".equals(response)) System.out.println("Unfortunately Car was not found!");
        else CarPrinter.printAllCars(response);
    }

    private void FilterbyYear() {
        System.out.print("Enter year: ");
        int year = readInt();

        String response = controller.filterByYear(year);

        if ("Car was not found!".equals(response)) {
            System.out.println("Unfortunately Car was not found!");
        } else {
            CarPrinter.printAllCars(response);
        }
    }

    private void FilterbyEnginetype() {
        scanner.nextLine();
        System.out.println("Available engine types:\n" + controller.getAvailableEngineTypes());

        System.out.print("Enter engine type: ");
        String type = scanner.nextLine();

        String response = controller.filterByEngineType(type);
        if ("Car was not found!".equals(response)) System.out.println("Unfortunately Car was not found!");
        else CarPrinter.printAllCars(response);
    }

    private void FilterbyCity() {
        scanner.nextLine();
        System.out.println("Available cities:\n" + controller.getAvailableCities());

        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        String response = controller.filterByCity(city);
        if ("Car was not found!".equals(response)) System.out.println("Unfortunately Car was not found!");
        else CarPrinter.printAllCars(response);
    }

    private void FilterbyPrice() {
        System.out.print("Enter low price: ");
        double low = readDouble();
        System.out.print("Enter high price: ");
        double high = readDouble();
        if (low > high) { double t=low; low=high; high=t; }

        String response = controller.filterByPriceRange(low, high);
        if ("Car was not found!".equals(response)) System.out.println("Unfortunately Car was not found!");
        else CarPrinter.printAllCars(response);
    }

    private void FilterCarsByASC() {
        System.out.println("\n=================================");
        System.out.println("LIST OF ALL SORTED CARS");
        System.out.println("=================================");

        String response = controller.FilterCarsByASC();
        CarPrinter.printAllCars(response);
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

    private int readInt() {
        int x = scanner.nextInt();
        scanner.nextLine();
        return x;
    }

    private double readDouble() {
        double x = scanner.nextDouble();
        scanner.nextLine();
        return x;
    }


}
