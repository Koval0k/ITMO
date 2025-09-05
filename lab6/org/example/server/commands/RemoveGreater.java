package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import java.util.List;
import java.util.stream.Collectors;
import org.example.common.spacemarine.*;

public class RemoveGreater extends ServerCommand{

    public RemoveGreater(CollectionManager cm) {
        super("remove_greater", "удалить из коллекции все элементы, значение health которых больше заданного", cm);
    }

    @Override
    public Response execute(Request request) {
        try {
            double health = Double.parseDouble(request.getMessage().split(" ")[1]);
            SpaceMarine comparisonElement = new SpaceMarine("dummy", health, null, null, null, null, null, null, null);

            List<Long> idsToRemove = cm.getCollection().values().stream()
                    .filter(marine -> marine.getHealth() > comparisonElement.getHealth())
                    .map(SpaceMarine::getId)
                    .collect(Collectors.toList());

            if (idsToRemove.isEmpty()) {
                return new Response("Нет элементов с health > " + comparisonElement.getHealth());
            }

            idsToRemove.forEach(cm::removeById);
            return new Response("Удалено элементов: " + idsToRemove.size());

        } catch (Exception e) {
            return new Response("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

}
