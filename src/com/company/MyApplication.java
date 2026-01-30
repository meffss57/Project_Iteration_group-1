package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.view.CarPrinter;
import com.company.services.AdminAuthService;
import com.company.services.UserAuthService;
import java.util.Map;


import com.company.Role;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final ICarController controller;
    private final AdminAuthService authService;
    private final UserAuthService userAuthService;


    private Role currentRole = Role.USER;

    // available logined users
    private Integer currentUserId = null;

    public MyApplication(ICarController controller, AdminAuthService authService, UserAuthService userAuthService) {
        this.controller = controller;
        this.authService = authService;
        this.userAuthService = userAuthService;
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
    private void userAuthMenu() {
        System.out.println("\nUSER ACCESS");
        System.out.println("1. Login to existing account");
        System.out.println("2. Create new account");
        System.out.println("0. Back");
        System.out.print("Choose option: ");
    }


    private void userMenu() {
        System.out.println("\nWELCOME DEAR CUSTOMER");
        System.out.println("1. View all cars");
        System.out.println("2. Get car by ID");
        System.out.println("3. Buy car by ID");
        System.out.println("4. Filter cars");
        System.out.println("5. Full car description (JOIN)");
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

        if (authService.login(username, password)) {
            currentRole = Role.ADMIN;
            System.out.println("Admin access granted");
        } else {
            System.out.println("Wrong login or password");
        }
    }

    // returns integer not boolean(r)
    private boolean handleUserAuth() {
        while (true) {
            userAuthMenu();
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Username: ");
                    String u = scanner.nextLine();
                    System.out.print("Password: ");
                    String p = scanner.nextLine();

                    Integer id = userAuthService.login(u, p);
                    if (id != null) {
                        currentUserId = id;
                        System.out.println("Login successful (user_id=" + currentUserId + ")");
                        return true;
                    } else {
                        System.out.println("Wrong username or password");
                    }
                }
                case 2 -> {
                    System.out.print("Choose username: ");
                    String u = scanner.nextLine();
                    System.out.print("Choose password: ");
                    String p = scanner.nextLine();

                    if (userAuthService.register(u, p)) {
                        System.out.println("Account created");
                        return true;
                    } else {
                        System.out.println("Cannot create account");
                    }
                }
                case 0 -> {
                    return false;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    //method reference(lambda)(r)
    private void runUserMenu() {
            Map<Integer, Runnable> actions = Map.of(
                1, this::getAllCarsMenu,
                2, this::getCarByIdMenu,
                3, this::buyCarByIdMenu,
                4, this::FilterCars,
                5, this::fullCarDescriptionMenu
        );

        while (true) {
            userMenu();
            int option = scanner.nextInt();

            if (option == 0) return;

            actions.getOrDefault(option, () -> System.out.println("Invalid option")).run();
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
                            if (handleUserAuth()) {
                                runUserMenu();
                            }
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
        CarPrinter.printAllCars(controller.getAllCars());
    }

    private void getCarByIdMenu() {
        System.out.print("\nEnter car ID: ");
        int id = scanner.nextInt();

        String response = controller.getCar(id);
        if ("Car was not found!".equals(response)) {
            System.out.println("Car was not found!");
        } else {
            CarPrinter.printCarCard(response);
        }
    }

    private void buyCarByIdMenu() {
        if (currentUserId == null) {
            System.out.println("You must login first.");
            return;
        }

        System.out.print("\nEnter car ID: ");
        int id = scanner.nextInt();

        String response = controller.buyCar(id, currentUserId);
        if ("Car has been sold or is archived!".equals(response)) {
            System.out.println("Car has been sold or is archived!");
        } else {
            CarPrinter.printCarCard(response);
            System.out.println("Congratulations!");
        }
    }

    // added join
    private void fullCarDescriptionMenu() {
        System.out.print("\nEnter car ID: ");
        int id = scanner.nextInt();
        System.out.println(controller.getFullCarDescription(id));
    }

    private void FilterCars() {
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

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> FilterbyBrand();
            case 2 -> FilterbyCity();
            case 3 -> FilterbyYear();
            case 4 -> FilterbyEnginetype();
            case 5 -> FilterbyPrice();
            case 6 -> FilterCarsByASC();
            case 0 -> {}
            default -> System.out.println("Invalid option");
        }
    }

    private void FilterbyBrand() {
        scanner.nextLine();
        System.out.println("Available brands:\n" + controller.getAvailableBrands());
        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();
        CarPrinter.printAllCars(controller.filterByBrand(brand));
    }

    private void FilterbyYear() {
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        CarPrinter.printAllCars(controller.filterByYear(year));
    }

    private void FilterbyEnginetype() {
        scanner.nextLine();
        System.out.println("Available engine types:\n" + controller.getAvailableEngineTypes());
        System.out.print("Enter engine type: ");
        String type = scanner.nextLine();
        CarPrinter.printAllCars(controller.filterByEngineType(type));
    }

    private void FilterbyCity() {
        scanner.nextLine();
        System.out.println("Available cities:\n" + controller.getAvailableCities());
        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        CarPrinter.printAllCars(controller.filterByCity(city));
    }

    private void FilterbyPrice() {
        System.out.print("Enter low price: ");
        double low = scanner.nextDouble();
        System.out.print("Enter high price: ");
        double high = scanner.nextDouble();
        if (low > high) { double t = low; low = high; high = t; }
        CarPrinter.printAllCars(controller.filterByPriceRange(low, high));
    }

    private void FilterCarsByASC() {
        CarPrinter.printAllCars(controller.FilterCarsByASC());
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

        System.out.println(controller.createCar(
                vin, brand, model, branchCity,
                year, color, engineType,
                engineVolume, mileage, salePrice, status
        ));
    }
}
