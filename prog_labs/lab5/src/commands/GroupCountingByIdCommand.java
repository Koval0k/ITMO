package commands;

import utils.CollectionManager;
import utils.Console;
import java.util.Map;
import java.util.stream.Collectors;
import SpaceMarine.SpaceMarine;

public class GroupCountingByIdCommand implements Command {
    private final CollectionManager collectionManager;

    public GroupCountingByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            Console.printError("Команда не принимает аргументов");
            return;
        }

        Map<Long, Long> idGroups = collectionManager.getCollection().values().stream()
                .collect(Collectors.groupingBy(
                        SpaceMarine::getId,
                        Collectors.counting()
                ));

        if (idGroups.isEmpty()) {
            Console.println("Коллекция пуста");
            return;
        }

        Console.println("Группировка элементов по ID:");
        idGroups.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        Console.println("ID " + entry.getKey() + ": " + entry.getValue() + " элемент(ов)")
                );
    }

    @Override
    public String getDescription() {
        return "сгруппировать элементы коллекции по значению поля id, вывести количество элементов в каждой группе";
    }
}