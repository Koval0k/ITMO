package commands;

import utils.CollectionManager;
import SpaceMarine.*;
import utils.Console;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SaveCommand implements Command {
    private final CollectionManager collectionManager;
    private final String envVariable;

    public SaveCommand(CollectionManager collectionManager, String envVariable) {
        this.collectionManager = collectionManager;
        this.envVariable = envVariable;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            Console.printError("Команда 'save' не принимает аргументов");
            return;
        }

        String fileName = System.getenv(envVariable);
        if (fileName == null || fileName.isEmpty()) {
            Console.printError("Переменная окружения " + envVariable + " не установлена");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Записываем заголовки CSV
            writer.write("id,name,coordinate_x,coordinate_y,creation_date,health,heart_count,achievements,weapon_type,chapter_name,chapter_world");
            writer.newLine();

            // Записываем данные
            for (Map.Entry<Long, SpaceMarine> entry : collectionManager.getCollection().entrySet()) {
                SpaceMarine marine = entry.getValue();
                writer.write(toCsvLine(entry.getKey(), marine));
                writer.newLine();
            }

            Console.println("Коллекция успешно сохранена в файл: " + fileName);
        } catch (IOException e) {
            Console.printError("Ошибка при сохранении в файл: " + e.getMessage());
        } catch (SecurityException e) {
            Console.printError("Нет прав на запись в файл: " + fileName);
        }
    }

    private String toCsvLine(Long key, SpaceMarine marine) {
        return String.join(",",
                key.toString(),
                escapeCsv(marine.getName()),
                String.valueOf(marine.getCoordinates().getX()),
                String.valueOf(marine.getCoordinates().getY()),
                marine.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                String.valueOf(marine.getHealth()),
                marine.getHeartCount() != null ? marine.getHeartCount().toString() : "",
                escapeCsv(marine.getAchievements()),
                marine.getWeapontype() != null ? marine.getWeapontype().name() : "",
                escapeCsv(marine.getChapter().getName()),
                escapeCsv(marine.getChapter().getWorld())
        );
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл (формат CSV)";
    }
}