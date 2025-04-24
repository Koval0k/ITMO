package commands;

import utils.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import SpaceMarine.*;

public class PrintAscendingCommand implements Command{
    private final CollectionManager collectionManager;

    public PrintAscendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            Console.printError("Команда 'print_ascending' не принимает аргументов");
            return;
        }

        List<SpaceMarine> sortedMarines = collectionManager.getCollection().values().stream()
                .sorted(Comparator.comparingDouble(SpaceMarine::getHealth))
                .collect(Collectors.toList());

        if (sortedMarines.isEmpty()) {
            Console.println("Коллекция пуста");
        } else {
            Console.println("Элементы коллекции в порядке возрастания health:");
            sortedMarines.forEach(marine ->
                    Console.println(String.format(
                            "ID: %d, Name: %-20s, Health: %.2f",
                            marine.getId(),
                            marine.getName(),
                            marine.getHealth()
                    ))
            );
            Console.println("Всего элементов: " + sortedMarines.size());
        }
    }

    @Override
    public String getDescription() {
        return "вывести элементы коллекции в порядке возрастания значений поля health";
    }
}
