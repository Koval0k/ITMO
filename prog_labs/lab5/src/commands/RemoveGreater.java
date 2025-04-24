package commands;

import utils.*;
import SpaceMarine.*;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveGreater implements Command{
    private final CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length > 0) {
                Console.printError("Команда 'remove_greater' не принимает аргументов в строке");
                return;
            }

            Console.println("Введите данные элемента для сравнения:");
            SpaceMarine comparisonElement = inputSpaceMarine();

            List<Long> idsToRemove = collectionManager.getCollection().values().stream()
                    .filter(marine -> marine.getHealth() > comparisonElement.getHealth())
                    .map(SpaceMarine::getId)
                    .collect(Collectors.toList());

            if (idsToRemove.isEmpty()) {
                Console.println("Нет элементов с health > " + comparisonElement.getHealth());
                return;
            }

            Console.println("Будут удалены элементы с ID: " + idsToRemove);
            Console.print("Подтвердите удаление (yes/no): ");
            String confirmation = Console.readLine().trim();

            if (confirmation.equalsIgnoreCase("yes")) {
                idsToRemove.forEach(collectionManager::removeById);
                Console.println("Удалено элементов: " + idsToRemove.size());
            } else {
                Console.println("Удаление отменено");
            }

        } catch (Exception e) {
            Console.printError("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    private SpaceMarine inputSpaceMarine() {
        Console.print("Введите health (double > 0): ");
        double health = Console.readDouble();
        while (health <= 0) {
            Console.printError("Health должно быть больше 0");
            Console.print("Введите health (double > 0): ");
            health = Console.readDouble();
        }

        return new SpaceMarine("dummy", health, null, null, null, null, null, null, null);
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение health которых больше заданного";
    }
}
