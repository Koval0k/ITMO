package utils;

import java.util.Scanner;

public class Console {
    private static Scanner scanner = new Scanner(System.in);

    public static void setInputSource(Scanner newScanner) {
        if (scanner != null) {
            scanner.close();
        }
        scanner = newScanner;
    }

    public static void resetToDefaultInput() {
        setInputSource(new Scanner(System.in));
    }

    public static void print(Object obj) {
        System.out.print(obj);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void printError(Object obj) {
        System.err.println("Ошибка: " + obj);
    }

    public static String readLine() {
        return scanner.nextLine();
    }

    public static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(readLine().trim());
            } catch (NumberFormatException e) {
                printError("Некорректный ввод числа. Повторите попытку:");
            }
        }
    }
}