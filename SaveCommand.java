package org.example.server.commands;

import org.example.server.managers.CollectionManager;
import org.example.server.system.CSVWriter;
import java.io.IOException;

public class SaveCommand {
    private final CollectionManager cm;

    public SaveCommand(CollectionManager cm) {
        this.cm = cm;
    }

    public String execute() {
        String filename = System.getenv("COLLECTION_FILE");
        if (filename == null) {
            return "Переменная окружения не установлена";
        }
        try {
            CSVWriter.saveCollection(cm.getCollection());
            return "Коллекция успешно сохранена в файл: " + filename;
        }catch (IOException e) {
            return "Ошибка записи в файл: " + e.getMessage();
        }
    }

}
