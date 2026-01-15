package com.company;

import com.company.controllers.interfaces.ICarController;

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
        System.out.println("Welcome to Car Dealership ");
        System.out.println("Select option:");
        System.out.println("1. Get car");
        System.out.println("2. Get user by id");
        System.out.println("3. Create user");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter option (1-3):  ");
    }

    public void start() {
        while (true) {
            mainMenu();

            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1: getAllCarsMenu(); break;
                    case 2: getCarByIdMenu(); break;
                    case 3: createCarMenu(); break;
                    default: return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be integer: " + e);
                scanner.nextLine(); // to ignore incorrect input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("*************************");
        }
    }

    public void getAllCarsMenu() {
        String response = controller.getAllCars();
        System.out.println(response);
    }

    public void getCarByIdMenu() {
        System.out.println("Please enter id");

        int id = scanner.nextInt();

        String response = controller.getCar(id);
        System.out.println(response);
    }

    public void createCarMenu() {
        System.out.println("Please enter vin");
        String vin = scanner.next();
        System.out.println("Please enter brand");
        String brand = scanner.next();
        System.out.println("Please enter model");
        String model = scanner.next();
        System.out.println("Please enter branch_city");
        String branchCity = scanner.next();
        System.out.println("Please enter year");
        int year = scanner.nextInt();
        System.out.println("Please enter color");
        String color = scanner.next();
        System.out.println("Please enter engine_type");
        String engineType = scanner.next();
        System.out.println("Please enter engine_volume");
        Double engineVolume = scanner.nextDouble();
        System.out.println("Please enter mileage");
        int mileage = scanner.nextInt();
        System.out.println("Please enter sale_price");
        Double salePrice = scanner.nextDouble();
        System.out.println("Please enter status");
        String status = scanner.next();


        String response = controller.createCar(vin,  brand,  model,  branchCity,  year, color, engineType,  engineVolume,  mileage,  salePrice,  status);
        System.out.println(response);
    }
}   
