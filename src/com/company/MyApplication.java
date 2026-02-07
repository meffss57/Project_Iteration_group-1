package com.company;

import com.company.controllers.interfaces.ICarController;
import com.company.models.AuthUser;
import com.company.services.UserAuthService;
import com.company.view.CarPrinter;
import com.company.services.ChatBotService;
import com.company.view.ChatPrinter;



public class MyApplication {

    private final java.util.Scanner scanner = new java.util.Scanner(System.in);

    private final ICarController controller;
    private final UserAuthService userAuthService;

    private final ChatBotService chatBot;


    private AuthUser currentUser = null;

    public MyApplication(ICarController controller,
                         UserAuthService userAuthService,
                         ChatBotService chatBot) {
        this.chatBot = chatBot;
        this.controller = controller;
        this.userAuthService = userAuthService;
    }

    // Helpers
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Input must be a number");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Input must be a number");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    //  Start menu
    private void startMenu() {
        System.out.println("=================================");
        System.out.println("WELCOME TO KZ.WHEELS");
        System.out.println("=================================");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("0. Exit");
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                startMenu();
                int choice = readInt("Choose option: ");

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    return;
                } else if (choice == 1) {
                    signIn();
                } else if (choice == 2) {
                    signUp();
                } else {
                    System.out.println("Invalid option");
                }
            } else {
                runRoleMenu();
            }
        }
    }

    private void signIn() {
        String u = readLine("Username: ");
        String p = readLine("Password: ");

        AuthUser user = userAuthService.login(u, p);
        if (user == null) {
            System.out.println("Wrong username or password");
            return;
        }

        currentUser = user;
        System.out.println("You are welcome, " + currentUser.getRole() + " " + currentUser.getUsername() + "!");
    }

    private void signUp() {
        String u = readLine("Choose username: ");
        String p = readLine("Choose password: ");

        boolean ok = userAuthService.register(u, p);
        if (ok) {
            System.out.println("Account created. Now Sign in.");
        } else {
            System.out.println("Cannot create account");
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("Logged out");
    }

    private void runRoleMenu() {
        switch (currentUser.getRole()) {
            case USER -> runUserMenu();
            case ADMIN -> runAdminMenu();
            case MANAGER -> runManagerMenu();
        }
    }

    //USER
    private void runUserMenu() {
        while (currentUser != null && currentUser.getRole() == Role.USER) {
            System.out.println("\nWELCOME DEAR CUSTOMER");
            System.out.println("1. View all cars");
            System.out.println("2. Get car by ID");
            System.out.println("3. Buy car by ID");
            System.out.println("4. Filter cars");
            System.out.println("5. Full car description (JOIN)");
            System.out.println("6. Chat with Wheely");
            System.out.println("0. Logout");


            int option = readInt("Choose option: ");
            switch (option) {
                case 1 -> getAllCarsMenu();
                case 2 -> getCarByIdMenu();
                case 3 -> buyCarByIdMenu();
                case 4 -> filterCarsMenu();
                case 5 -> fullCarDescriptionMenu();
                case 6 -> chatMenu();
                case 0 -> logout();
                default -> System.out.println("Invalid option");
            }
        }
    }
    private void chatMenu() {

        System.out.println("\n=== AI CAR ADVISOR WHEELY ===");
        System.out.println("Type 'exit' to leave chat\n");

        while (true) {

            String q = readLine("You: ");

            if (q.equalsIgnoreCase("exit")) break;

            System.out.println("Thinking...\n");

            String answer = chatBot.ask(q);

            ChatPrinter.printAIAnswer(answer);

        }
    }

    // ADMIN
    private void runAdminMenu() {
        while (currentUser != null && currentUser.getRole() == Role.ADMIN) {
            System.out.println("\nADMIN MENU");
            System.out.println("1. View all cars");
            System.out.println("2. Get car by ID");
            System.out.println("3. Create car");
            System.out.println("4. Mark SOLD car as available");
            System.out.println("5. Full car description (JOIN)");
            System.out.println("0. Logout");

            int option = readInt("Choose option: ");
            switch (option) {
                case 1 -> getAllCarsMenu();
                case 2 -> getCarByIdMenu();
                case 3 -> createCarMenu();
                case 4 -> markSoldAsAvailableMenu();
                case 5 -> fullCarDescriptionMenu();
                case 0 -> logout();
                default -> System.out.println("Invalid option");
            }
        }
    }

    //MANAGER
    private void runManagerMenu() {
        while (currentUser != null && currentUser.getRole() == Role.MANAGER) {
            System.out.println("\nMANAGER MENU");
            System.out.println("1. View all cars");
            System.out.println("2. Get car by ID");
            System.out.println("3. Full car description (JOIN)");
            System.out.println("0. Logout");

            int option = readInt("Choose option: ");
            switch (option) {
                case 1 -> getAllCarsMenu();
                case 2 -> getCarByIdMenu();
                case 3 -> fullCarDescriptionMenu();
                case 0 -> logout();
                default -> System.out.println("Invalid option");
            }
        }
    }

    // Car menus
    private void getAllCarsMenu() {
        System.out.println("\n=================================");
        System.out.println("LIST OF ALL CARS");
        System.out.println("=================================");
        CarPrinter.printAllCars(controller.getAllCars());
    }

    private void getCarByIdMenu() {
        int id = readInt("Enter car ID: ");
        String response = controller.getCar(id);

        if ("Car was not found!".equals(response)) {
            System.out.println("Car was not found!");
        } else {
            CarPrinter.printCarCard(response);
        }
    }

    private void buyCarByIdMenu() {
        int id = readInt("Enter car ID: ");
        String response = controller.buyCar(id, currentUser.getUserId());

        if ("Car has been sold or is archived!".equals(response)) {
            System.out.println("Car has been sold or is archived!");
        } else {
            CarPrinter.printCarCard(response);
            System.out.println("Congratulations!");
        }
    }

    private void fullCarDescriptionMenu() {
        int id = readInt("Enter car ID: ");
        System.out.println(controller.getFullCarDescription(id));
    }

    private void markSoldAsAvailableMenu() {
        int id = readInt("Enter car ID to set AVAILABLE: ");
        String response = controller.markCarAsAvailable(id);

        if (response != null && response.startsWith("Car{")) {
            System.out.println("Status updated successfully!");
            CarPrinter.printCarCard(response);
        } else {
            System.out.println(response);
        }
    }

    private void filterCarsMenu() {
        System.out.println("\n=================================");
        System.out.println("Choose How to filter: ");
        System.out.println("1. Filter by Brand");
        System.out.println("2. Filter by City");
        System.out.println("3. Filter by year");
        System.out.println("4. Filter by engine_type");
        System.out.println("5. Filter by price");
        System.out.println("6. Filter by ascending order");
        System.out.println("7. Filter by category");
        System.out.println("0. Back");
        System.out.println("=================================");

        int choice = readInt("Choose option: ");

        switch (choice) {
            case 1 -> filterByBrand();
            case 2 -> filterByCity();
            case 3 -> filterByYear();
            case 4 -> filterByEngineType();
            case 5 -> filterByPrice();
            case 6 -> filterCarsByASC();
            case 7 -> filterByCategory();
            case 0 -> { }
            default -> System.out.println("Invalid option");
        }
    }

    private void filterByBrand() {
        System.out.println("Available brands:\n" + controller.getAvailableBrands());
        String brand = readLine("Enter brand: ");
        CarPrinter.printAllCars(controller.filterByBrand(brand));
    }

    private void filterByCity() {
        System.out.println("Available cities:\n" + controller.getAvailableCities());
        String city = readLine("Enter city: ");
        CarPrinter.printAllCars(controller.filterByCity(city));
    }

    private void filterByYear() {
        int year = readInt("Enter year: ");
        CarPrinter.printAllCars(controller.filterByYear(year));
    }

    private void filterByEngineType() {
        System.out.println("Available engine types:\n" + controller.getAvailableEngineTypes());
        String type = readLine("Enter engine type: ");
        CarPrinter.printAllCars(controller.filterByEngineType(type));
    }

    private void filterByPrice() {
        double low = readDouble("Enter low price: ");
        double high = readDouble("Enter high price: ");
        if (low > high) { double t = low; low = high; high = t; }
        CarPrinter.printAllCars(controller.filterByPriceRange(low, high));
    }

    private void filterCarsByASC() {
        CarPrinter.printAllCars(controller.FilterCarsByASC());
    }

    private void filterByCategory() {
        System.out.println("Available categories:\n" + controller.getAvailableCategories());
        String category = readLine("Enter category: ");
        CarPrinter.printAllCars(controller.filterByCategory(category));
    }

    private void createCarMenu() {
        System.out.println("\nCREATE NEW CAR");

        String vin = readLine("VIN: ");
        String brand = readLine("Brand: ");
        String model = readLine("Model: ");
        String branchCity = readLine("Branch city: ");
        int year = readInt("Year: ");
        String color = readLine("Color: ");
        String engineType = readLine("Engine type: ");
        double engineVolume = readDouble("Engine volume: ");
        int mileage = readInt("Mileage: ");
        double salePrice = readDouble("Sale price: ");
        String status = readLine("Status (available/sold/archived): ");
        String category = readLine("Category (SUV/Sedan/Hatchback/Electric): ");

        System.out.println(controller.createCar(
                vin, brand, model, branchCity,
                year, color, engineType,
                engineVolume, mileage, salePrice, status, category
        ));
    }
}
