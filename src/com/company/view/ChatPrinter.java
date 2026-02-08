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

            if (line.isEmpty()) {
                System.out.println();
                continue;
            }

            // Header
            if (line.toLowerCase().startsWith("recommended")) {
                System.out.println("  " + line);
                continue;
            }

            if (line.toLowerCase().startsWith("reasons")) {
                System.out.println("\n  " + line);
                continue;
            }

            // Bullet point
            if (line.startsWith("•") || line.startsWith("-")) {
                System.out.println(" • " + line.substring(1).trim());
                continue;
            }

            // Normal text
            System.out.println("   " + line);
        }
    }

}
