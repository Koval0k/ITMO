package org.example.server;

import org.example.server.managers.CollectionManager;
import org.example.server.system.*;
import org.example.common.spacemarine.SpaceMarine;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            // Загрузка коллекции
            HashMap<Long, SpaceMarine> collection = CSVReader.readCollection();
            CollectionManager collectionManager = new CollectionManager();
            collectionManager.setCollection(collection);

            // Запуск сервера
            Server server = new Server(collectionManager);
            server.start(8080);

            // Хук для сохранения при завершении
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    CSVWriter.saveCollection(collectionManager.getCollection());
                    System.out.println("Коллекция сохранена в CSV");
                } catch (Exception e) {
                    System.err.println("Ошибка сохранения: " + e.getMessage());
                }
            }));

        } catch (Exception e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            System.exit(1);
        }
    }
}