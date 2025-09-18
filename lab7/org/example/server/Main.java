package org.example.server;

import org.example.server.managers.CollectionManager;
import org.example.server.managers.DataBaseManager;
import org.example.common.spacemarine.SpaceMarine;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            DataBaseManager.connect();
            // Загрузка коллекции
            HashMap<Long, SpaceMarine> collection = DataBaseManager.getSpaceMarines();
            CollectionManager collectionManager = new CollectionManager();
            collectionManager.setCollection(collection);

            // Запуск сервера
            Server server = new Server(collectionManager);
            try {
                server.start(Integer.parseInt(args[0]));
            } finally {
                server.shutdown();
            }

        } catch (Exception e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
            System.exit(1);
        }
    }
}