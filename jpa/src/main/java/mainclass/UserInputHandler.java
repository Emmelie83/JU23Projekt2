package mainclass;

import java.util.Scanner;

public class UserInputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int menuInput(int maxChoices) {
        int choice = readIntInput();
        while (choice < 0 || choice > maxChoices) {
            System.out.println("You have to enter a number between 0 and " + maxChoices);
            choice = readIntInput();
        }
        return choice;
    }

    public static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("You have to enter a number: ");
            }
        }
    }

    public static String readStringInput() {
        String input = scanner.nextLine();
        while (input == null || input.trim().isEmpty()) {
            System.out.println("Empty input. Please try again:");
            input = scanner.nextLine();
        }
        return input;
    }

    public static void pressEnterToContinue(int menuChoice) {
        if (menuChoice < 1) return;
        System.out.print("\nPress Enter to Continue...\n");
        scanner.nextLine();
    }
}
