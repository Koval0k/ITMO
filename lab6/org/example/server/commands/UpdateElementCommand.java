package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.common.spacemarine.SpaceMarine;
import org.example.server.managers.CollectionManager;

import java.io.IOException;

public class UpdateElementCommand extends ServerCommand{
    public UpdateElementCommand(CollectionManager cm){
        super("update", "обновить значение элемента коллекции по id", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        try {
            Long id = request.getProduct().getId();
            SpaceMarine oldMarine = cm.getById(id);
            if (oldMarine == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }
            cm.updateElement(id, request.getProduct());
            return new Response("Объект успешно обновлен");
        }catch (IllegalArgumentException e){
            return new Response(e.getMessage());
        }
    }
}

