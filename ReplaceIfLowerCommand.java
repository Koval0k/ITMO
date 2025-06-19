package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.common.spacemarine.SpaceMarine;
import org.example.server.managers.CollectionManager;

import java.io.IOException;

public class ReplaceIfLowerCommand extends ServerCommand{
    public ReplaceIfLowerCommand(CollectionManager cm){
        super("replace_if_lowe", "заменить значение по ключу, если новое значение health меньше старого", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        try {
            Long id = request.getProduct().getId();
            SpaceMarine oldMarine = cm.getById(id);
            if (oldMarine == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }
            if (request.getProduct().getHealth() < oldMarine.getHealth()) {
                cm.replace(id, request.getProduct());
                return new Response("Элемент заменен (новое health меньше старого)");
            } else {
                return new Response("Элемент не заменен (новое health не меньше старого)");
            }

        }catch (IllegalArgumentException e){
            return new Response(e.getMessage());
        }
    }
}
