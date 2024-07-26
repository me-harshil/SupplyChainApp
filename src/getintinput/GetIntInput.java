package getintinput;

import java.util.Scanner;

public class GetIntInput {
    public static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextInt();
    }
}
