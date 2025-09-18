package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.common.spacemarine.SpaceMarine;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.DataBaseManager;

import java.io.IOException;

public class ReplaceIfLowerCommand extends ServerCommand{
    public ReplaceIfLowerCommand(CollectionManager cm){
        super("replace_if_lowe", "заменить значение по ключу, если новое значение health меньше старого", cm);
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

            if (request.getProduct().getHealth() < oldMarine.getHealth() && oldMarine.getUser().equals(newMarine.getUser())) {
                boolean res = DataBaseManager.replaceSpaceMarine(oldMarine, newMarine);
                if(res) {
                    cm.replace(id, request.getProduct());
                    return new Response("Элемент заменен (новое health меньше старого)");
                } else {
                    return new Response("Элемент не заменен");
                }
            } else {
                return new Response("Элемент не заменен (новое health не меньше старого) или объект не принадлежит вам");
            }

        }catch (IllegalArgumentException e){
            return new Response(e.getMessage());
        }
    }
}
