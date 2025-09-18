package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import java.util.List;
import java.util.stream.Collectors;
import org.example.common.spacemarine.*;
import org.example.server.managers.DataBaseManager;

public class RemoveGreater extends ServerCommand{

    public RemoveGreater(CollectionManager cm) {
        super("remove_greater", "удалить из коллекции все элементы, значение health которых больше заданного", cm);
    }

    @Override
    public Response execute(Request request) {
        try {
            String login = request.getLogin();
            String password = request.getPassword();

            if (!DataBaseManager.checkUserCredentials(login, password)) {
                return new Response("Не выполнен вход в систему");
            }
            double health = Double.parseDouble(request.getMessage().split(" ")[1]);
            SpaceMarine comparisonElement = new SpaceMarine("dummy", health, null, null, null, null, null, null, null, login);

            List<Long> idsToRemove = cm.getCollection().values().stream()
                    .filter(marine -> marine.getHealth() > comparisonElement.getHealth())
                    .filter(marine -> marine.getUser().equals(comparisonElement.getUser()))
                    .map(SpaceMarine::getId)
                    .collect(Collectors.toList());

            if (idsToRemove.isEmpty()) {
                return new Response("Нет элементов с health > " + comparisonElement.getHealth());
            }

            int deletedCount = 0;
            for (Long x : idsToRemove) {
                if (DataBaseManager.removeSpaceMarineById(x, login)) {
                    cm.removeElement(x);
                    deletedCount++;
                }
            }

            if (deletedCount == 0) {
                return new Response("Ни один экземпляр не удален");
            } else {
                return new Response("Удалено " + deletedCount + " экземпляров, превышающих указанное значение");
            }

        } catch (Exception e) {
            return new Response("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

}
