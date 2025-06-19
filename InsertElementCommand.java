package org.example.server.commands;

import org.example.common.spacemarine.*;
import org.example.server.managers.CollectionManager;
import org.example.common.dto.*;
import java.io.IOException;

public class InsertElementCommand extends ServerCommand{
    public InsertElementCommand(CollectionManager cm){
        super("insert", "добавить новый элемент с заданным ключом", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        try {
            Long id = request.getProduct().getId();
            if (cm.getCollection().containsKey(id)) {
                throw new IllegalArgumentException("Элемент с ключом " + id + " уже существует");
            }
            cm.addElement(id, request.getProduct());
            return new Response("Объект успешно добавлен");
        }catch (IllegalArgumentException e){
            return new Response(e.getMessage());
        }
    }
}
