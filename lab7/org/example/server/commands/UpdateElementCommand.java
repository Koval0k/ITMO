package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.common.spacemarine.SpaceMarine;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.DataBaseManager;

import java.io.IOException;

public class UpdateElementCommand extends ServerCommand{
    public UpdateElementCommand(CollectionManager cm){
        super("update", "обновить значение элемента коллекции по id", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        try {
            String login = request.getLogin();
            String password = request.getPassword();

            if (!DataBaseManager.checkUserCredentials(login, password)) {
                return new Response("Не выполнен вход в систему");
            }

            Long id = request.getProduct().getId();
            SpaceMarine oldMarine = cm.getById(id);
            SpaceMarine newMarine = request.getProduct();
            newMarine.setUser(login);
            if (oldMarine == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }
            boolean res = DataBaseManager.replaceSpaceMarine(oldMarine, newMarine);
            if (res) {
                cm.updateElement(id, request.getProduct());
                return new Response("Объект успешно обновлен");
            }else{
                return new Response("Ошибка при обновлении объекта");
            }
        }catch (IllegalArgumentException e){
            return new Response(e.getMessage());
        }
    }
}

