package org.example.server.system;

import org.example.common.spacemarine.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class CSVReader {
    public static final String ENV_VARIABLE = "COLLECTION_FILE";

    public static HashMap<Long, SpaceMarine> readCollection() throws FileNotFoundException {
        String fileName = System.getenv(ENV_VARIABLE);
        HashMap<Long, SpaceMarine> collection = new HashMap<>();

        if (fileName == null || fileName.isEmpty()) {
            throw new FileNotFoundException("Переменная окружения " + ENV_VARIABLE + " не установлена");
        }

        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextLine()) scanner.nextLine(); // Пропускаем заголовок

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    SpaceMarine marine = parseCSVLine(line);
                    collection.put(marine.getId(), marine);
                }
            }
        }
        return collection;
    }

    private static SpaceMarine parseCSVLine(String csvLine) throws IllegalArgumentException {
        String[] parts = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length != 11) {
            throw new IllegalArgumentException("Неверное количество полей в CSV строке. Ожидается 11");
        }

        // Очистка кавычек
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

            // Валидация
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
                    new Chapter(chapterName, chapterWorld),
                    weaponType,
                    id,
                    creationDate
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка парсинга: " + e.getMessage());
        }
    }
}