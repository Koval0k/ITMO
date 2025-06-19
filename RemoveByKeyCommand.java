package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;

public class RemoveByKeyCommand extends ServerCommand {

    public RemoveByKeyCommand(CollectionManager cm) {
        super("remove_by_key", "удалить элемент из коллекции по его ключу", cm);
    }

    @Override
    public Response execute(Request request) {
        try {
            if (request.getMessage().split(" ").length < 2) throw new IllegalArgumentException("Не указан ключ");

            Long id = Long.parseLong(request.getMessage().split(" ")[1]);
            if (cm.getById(id) == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }
            cm.removeElement(id);
            return new Response("Элемент с id " + id + " удален.");
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage());
        }
    }
}
