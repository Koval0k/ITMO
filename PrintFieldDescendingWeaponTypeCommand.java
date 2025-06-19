package org.example.server.commands;

import org.example.common.spacemarine.*;
import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.server.managers.CollectionManager;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrintFieldDescendingWeaponTypeCommand extends ServerCommand {
    public PrintFieldDescendingWeaponTypeCommand(CollectionManager cm) {
        super("print_field_descending_weapon_type", "вывести значения поля weaponType всех элементов в порядке убывания", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        String result = cm.getCollection().values().stream()
                .map(SpaceMarine::getWeapontype)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            if (list.isEmpty()) {
                                return "В коллекции нет элементов с указанным weaponType";
                            } else {
                                return "Значения weaponType в порядке убывания:\n" +
                                        list.stream()
                                                .map(Weapon::toString)
                                                .collect(Collectors.joining("\n"));
                            }
                        }
                ));
        return new Response(result);
    }
}
