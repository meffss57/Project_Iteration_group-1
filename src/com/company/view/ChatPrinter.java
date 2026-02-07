package com.company.view;

public class ChatPrinter {

    public static void printAIAnswer(String text) {

        System.out.println();
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        AI CAR ADVISOR WHEELY         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();

        formatText(text);

        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println();
    }


    private static void formatText(String text) {

        String[] lines = text.split("\n");

        for (String line : lines) {

            line = line.trim();

            if (line.startsWith("**") || line.startsWith("##")) {
                System.out.println(line.replace("*", ""));
            }

            else if (line.startsWith("-") || line.startsWith("*")) {
                System.out.println(" • " + line.substring(1).trim());
            }

            else if (line.contains("recommend") || line.contains("Recommended")) {
                System.out.println(line);
            }

            else if (line.toLowerCase().contains("price")) {
                System.out.println(line);
            }

            else if (line.toLowerCase().contains("mileage")) {
                System.out.println(line);
            }

            else if (line.toLowerCase().contains("engine")) {
                System.out.println(line);
            }

            else if (line.toLowerCase().contains("year")) {
                System.out.println(line);
            }

            else if (line.toLowerCase().contains("category")) {
                System.out.println(line);
            }

            else {
                System.out.println("   " + line);
            }
        }
    }
}
