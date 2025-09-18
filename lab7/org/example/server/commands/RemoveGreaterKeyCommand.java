package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import java.util.*;
import org.example.common.spacemarine.*;
import org.example.server.managers.DataBaseManager;

public class RemoveGreaterKeyCommand extends ServerCommand{
    private final Comparator<Long> keyComparator;

    public RemoveGreaterKeyCommand(CollectionManager cm) {
        super("remove_greater_key", "удалить из коллекции все элементы, ключ которых превышает заданный (пустая строка для null)", cm);
        this.keyComparator = Comparator.nullsFirst(Comparator.naturalOrder());
    }

    @Override
    public Response execute(Request request) {
        String[] parts = request.getMessage().trim().split(" ");

        try {
            String login = request.getLogin();
            String password = request.getPassword();

            if (!DataBaseManager.checkUserCredentials(login, password)) {
                return new Response("Не выполнен вход в систему");
            }

            Long targetKey = parseKey(parts);
            Map<Long, SpaceMarine> collection = cm.getCollection();

            List<Long> keysToRemove = collection.keySet().stream()
                    .filter(key -> keyComparator.compare(key, targetKey) > 0)
                    .toList();

            int deletedCount = 0;
            for(Long x: keysToRemove){
                if (DataBaseManager.removeSpaceMarineById(x, login)) {
                    cm.removeElement(x);
                    deletedCount++;
                }
            }

            if (deletedCount == 0) {
                return new Response("Ни один экземпляр не удален");
            } else {
                return new Response("Удалено " + deletedCount + " экземпляров, превышающих указанное значение id");
            }

        } catch (IllegalArgumentException e) {
            return new Response("Ошибка: " + e.getMessage());
        }
    }

    private Long parseKey(String[] args) {
        if (args.length == 1 || args[1].trim().isEmpty()) {
            return null;
        }

        try {
            return Long.parseLong(args[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Некорректный формат ключа. Должно быть число или пустая строка для null");
        }
    }
}
