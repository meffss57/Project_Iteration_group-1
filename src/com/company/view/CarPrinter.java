package com.company.view;

public class CarPrinter {

    public static void printCarCard(String carString) {
        if (carString == null || carString.isBlank()) return;

        String data = carString
                .replace("Car{", "")
                .replace("}", "");

        String[] fields = data.split(", ");

        System.out.println("========================================");
        System.out.println("CAR");
        System.out.println("========================================");

        for (String field : fields) {
            String[] pair = field.split("=", 2);
            String key = pair[0];
            String value = pair.length > 1
                    ? pair[1].replace("'", "")
                    : "";

            System.out.printf("%-15s : %s%n", key, value);
        }

        System.out.println("========================================");
    }

    public static void printAllCars(String response) {
        if (response == null || response.isBlank()) {
            System.out.println("No cars found");
            return;
        }

        String[] cars = response.split("\n");

        for (String car : cars) {
            printCarCard(car);
        }
    }
}
