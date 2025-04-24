package commands;

import utils.CollectionManager;
import SpaceMarine.SpaceMarine;
import java.util.*;
import java.util.stream.Collectors;

public class RemoveGreaterKeyCommand implements Command {
    private final CollectionManager collectionManager;
    private final Comparator<Long> keyComparator;

    public RemoveGreaterKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.keyComparator = Comparator.nullsFirst(Comparator.naturalOrder());
    }

    @Override
    public void execute(String[] args) {
        try {
            Long targetKey = parseKey(args);
            Map<Long, SpaceMarine> collection = collectionManager.getCollection();

            List<Long> keysToRemove = collection.keySet().stream()
                    .filter(key -> keyComparator.compare(key, targetKey) > 0)
                    .toList();

            keysToRemove.forEach(collection::remove);

            System.out.printf("Удалено %d элементов%n", keysToRemove.size());

        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private Long parseKey(String[] args) {
        if (args.length == 0 || args[0].trim().isEmpty()) {
            return null;
        }

        try {
            return Long.parseLong(args[0]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Некорректный формат ключа. Должно быть число или пустая строка для null");
        }
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, ключ которых превышает заданный (пустая строка для null)";
    }
}