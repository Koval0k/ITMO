package utils;

import SpaceMarine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FileDataLoader {
    public static final String ENV_VARIABLE = "COLLECTION_FILE";
    public static SpaceMarine parseCsvLine(String csvLine) throws IllegalArgumentException {
        String[] parts = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length < 11) {
            throw new IllegalArgumentException("Неверное количество полей в CSV строке. Ожидается 11, получено " + parts.length);
        }

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
        }

        try {
            long id = Long.parseLong(parts[0]);
            String name = parts[1];
            int x = Integer.parseInt(parts[2]);
            long y = Long.parseLong(parts[3]);
            LocalDateTime creationDate = LocalDateTime.parse(parts[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            double health = Double.parseDouble(parts[5]);

            Integer heartCount = parts[6].isEmpty() ? null : Integer.parseInt(parts[6]);
            String achievements = parts[7].isEmpty() ? null : parts[7];
            Weapon weaponType = parts[8].isEmpty() ? null : Weapon.valueOf(parts[8]);

            String chapterName = parts[9];
            String chapterWorld = parts[10];
            Chapter chapter = new Chapter(chapterName, chapterWorld);

            if (id <= 0) throw new IllegalArgumentException("ID должен быть положительным");
            if (y > 444) throw new IllegalArgumentException("Координата Y должна быть <= 444");
            if (health <= 0) throw new IllegalArgumentException("Health должно быть > 0");
            if (heartCount != null && (heartCount <= 0 || heartCount > 3)) {
                throw new IllegalArgumentException("HeartCount должен быть 1-3 или null");
            }

            return new SpaceMarine(
                    name,
                    health,
                    heartCount,
                    achievements,
                    new Coordinates(x, y),
                    chapter,
                    weaponType,
                    id,
                    creationDate
            );

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка парсинга числа: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Некорректное значение: " + e.getMessage());
        }
    }

    public static CollectionManager loadCollectionFromFile() {
        String fileName = System.getenv(ENV_VARIABLE);
        CollectionManager loadedCollection = new CollectionManager();
        if (fileName == null || fileName.isEmpty()) {
            Console.printError("Переменная окружения " + ENV_VARIABLE + " не установлена");
            return null;
        }

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            if (fileScanner.hasNextLine()) fileScanner.nextLine();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        SpaceMarine marine = FileDataLoader.parseCsvLine(line);
                        loadedCollection.addElement(marine.getId(), marine);
                    } catch (Exception e) {
                        Console.printError("Ошибка парсинга строки: " + line + " - " + e.getMessage());
                    }
                }
            }
            Console.println("Коллекция загружена. Элементов: " + loadedCollection.getCollection().size());
        } catch (FileNotFoundException e) {
            Console.printError("Файл не найден: " + fileName);
        } catch (Exception e) {
            Console.printError("Ошибка загрузки: " + e.getMessage());
        }
        return loadedCollection;
    }
}