package petroleum_managemnt_system;
import java.util.Scanner;

public class InputValidator {
    private static InputValidator instance;
    private Scanner scanner;
    
    private InputValidator() {
        scanner = new Scanner(System.in);
    }
    
    public static InputValidator getInstance() {
        if (instance == null) {
            instance = new InputValidator();
        }
        return instance;
    }

    public double getValidDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.next();
                scanner.nextLine(); // consume remaining line
                
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("Error: Value cannot be negative. Please try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    public int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.next();
                scanner.nextLine(); // consume remaining line
                
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.println("Error: Please enter a number between " + min + " and " + max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    public String getValidString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public Scanner getScanner() {
        return scanner;
    }
} 