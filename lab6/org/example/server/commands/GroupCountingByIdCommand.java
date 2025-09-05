package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import java.util.*;
import org.example.common.spacemarine.*;
import java.util.stream.Collectors;

public class GroupCountingByIdCommand extends ServerCommand {


    public GroupCountingByIdCommand(CollectionManager cm) {
        super("group_counting_by_id", "сгруппировать элементы коллекции по значению поля id, вывести количество элементов в каждой группе", cm);
    }

    @Override
    public Response execute(Request request) {

        Map<Long, Long> idGroups = cm.getCollection().values().stream()
                .collect(Collectors.groupingBy(
                        SpaceMarine::getId,
                        Collectors.counting()
                ));

        if (idGroups.isEmpty()) {
            return new Response("Коллекция пуста");
        }

        StringBuilder result = new StringBuilder();
        idGroups.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    result.append("ID ")
                            .append(entry.getKey())
                            .append(": ")
                            .append(entry.getValue())
                            .append(" элемент(ов)\n");
                });

        return new Response(result.toString());
    }

}
