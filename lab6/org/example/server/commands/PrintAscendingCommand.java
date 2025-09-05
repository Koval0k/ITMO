package org.example.server.commands;

import org.example.server.managers.CollectionManager;
import org.example.common.dto.*;
import org.example.common.spacemarine.SpaceMarine;
import java.util.Comparator;
import java.util.List;

public class PrintAscendingCommand extends ServerCommand {
    public PrintAscendingCommand(CollectionManager cm) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания значений поля health", cm);
    }

    @Override
    public Response execute(Request request) {
        if (cm.getCollection().isEmpty()) {
            return new Response("Коллекция пуста");
        }

        List<SpaceMarine> sortedMarines = cm.getCollection().values().stream()
                .sorted(Comparator.comparingDouble(SpaceMarine::getHealth))
                .toList();

        return new Response("Элементы в порядке возрастания поля health:\n" + sortedMarines);
    }
}