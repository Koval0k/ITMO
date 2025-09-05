package org.example.server.system;

import org.example.common.spacemarine.SpaceMarine;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CSVWriter {
    public static final String ENV_VARIABLE = CSVReader.ENV_VARIABLE;

    public static void saveCollection(Map<Long, SpaceMarine> collection) throws IOException {
        String fileName = System.getenv(ENV_VARIABLE);
        if (fileName == null) {
            throw new IOException("Переменная окружения " + ENV_VARIABLE + " не установлена");
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            // Заголовок CSV
            writer.write("id,name,coordinates_x,coordinates_y,creationDate,health,heartCount,achievements,weaponType,chapterName,chapterWorld\n");

            for (SpaceMarine marine : collection.values()) {
                writer.write(String.format(
                        "\"%d\",\"%s\",%d,%d,\"%s\",%.1f,%s,\"%s\",%s,\"%s\",\"%s\"\n",
                        marine.getId(),
                        marine.getName(),
                        marine.getCoordinates().getX(),
                        marine.getCoordinates().getY(),
                        marine.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        marine.getHealth(),
                        marine.getHeartCount() == null ? "" : marine.getHeartCount(),
                        marine.getAchievements() == null ? "" : marine.getAchievements(),
                        marine.getWeapontype() == null ? "" : marine.getWeapontype().name(),
                        marine.getChapter().getName(),
                        marine.getChapter().getWorld()
                ));
            }
        }
    }
}