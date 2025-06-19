package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import java.util.*;
import org.example.common.spacemarine.*;

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
            Long targetKey = parseKey(parts);
            Map<Long, SpaceMarine> collection = cm.getCollection();

            List<Long> keysToRemove = collection.keySet().stream()
                    .filter(key -> keyComparator.compare(key, targetKey) > 0)
                    .toList();

            keysToRemove.forEach(collection::remove);

            return new Response("Удалено " + keysToRemove.size() + " элементов");

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
