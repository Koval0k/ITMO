package org.example.client;

import java.io.IOException;
import java.util.Scanner;
import org.example.common.spacemarine.*;

public class SpaceMarineCreator {
    private static final Scanner scanner = new Scanner(System.in);

    public static SpaceMarine inputSpaceMarineFromScript(Scanner scanner, Long key) throws IOException {
        return new SpaceMarine(
                readName(scanner),
                readHealth(scanner),
                readHeartCount(scanner),
                readAchievements(scanner),
                readCoordinates(scanner),
                readChapter(scanner),
                readWeaponType(scanner),
                key,
                null
        );
    }

    private static String readName(Scanner scanner) throws IOException {
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        if (name == null || name.trim().isEmpty()) {
            throw new IOException("Name cannot be empty");
        }
        return name.trim();
    }

    private static Coordinates readCoordinates(Scanner scanner) throws IOException {
        System.out.println("Введите координату x:");
        int x = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите координату y (не более 444):");
        long y = Long.parseLong(scanner.nextLine());
        if (y > 444) {
            throw new IOException("Y coordinate must be <= 444");
        }

        return new Coordinates(x, y);
    }

    private static double readHealth(Scanner scanner) throws IOException {
        System.out.println("Введите здоровье (>0):");
        double health = Double.parseDouble(scanner.nextLine());
        if (health <= 0) {
            throw new IOException("Health must be > 0");
        }
        return health;
    }

    private static Integer readHeartCount(Scanner scanner) throws IOException {
        System.out.println("Введите количество сердец (1-3, пусто для null):");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        int count = Integer.parseInt(input);
        if (count < 1 || count > 3) {
            throw new IOException("Heart count must be 1-3");
        }
        return count;
    }

    private static String readAchievements(Scanner scanner) throws IOException {
        System.out.println("Введите достижения (пусто для null):");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    private static Chapter readChapter(Scanner scanner) throws IOException {
        System.out.println("Создать главу? (yes/no):");
        if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            return null;
        }

        System.out.println("Введите название главы:");
        String name = scanner.nextLine();
        if (name == null || name.trim().isEmpty()) {
            throw new IOException("Chapter name cannot be empty");
        }

        System.out.println("Введите мир главы:");
        String world = scanner.nextLine();
        if (world == null || world.trim().isEmpty()) {
            throw new IOException("Chapter world cannot be empty");
        }

        return new Chapter(name.trim(), world.trim());
    }

    private static Weapon readWeaponType(Scanner scanner) throws IOException {
        System.out.println("Доступные типы оружия:");
        for (Weapon weapon : Weapon.values()) {
            System.out.println("- " + weapon.name());
        }
        System.out.println("Введите тип оружия (пусто для null):");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }

        try {
            return Weapon.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid weapon type");
        }
    }
}