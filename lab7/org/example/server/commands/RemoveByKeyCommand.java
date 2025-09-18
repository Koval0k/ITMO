package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.DataBaseManager;

public class RemoveByKeyCommand extends ServerCommand {

    public RemoveByKeyCommand(CollectionManager cm) {
        super("remove_by_key", "удалить элемент из коллекции по его ключу", cm);
    }

    @Override
    public Response execute(Request request) {
        try {
            String login = request.getLogin();
            String password = request.getPassword();

            if (!DataBaseManager.checkUserCredentials(login, password)) {
                return new Response("Не выполнен вход в систему");
            }

            if (request.getMessage().split(" ").length < 2) throw new IllegalArgumentException("Не указан ключ");

            Long id = Long.parseLong(request.getMessage().split(" ")[1]);
            if (cm.getById(id) == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }
            boolean res = DataBaseManager.removeSpaceMarineById(id, login);
            if(res) {
                cm.removeElement(id);
                return new Response("Элемент с id " + id + " удален.");
            }else{
                return new Response("Ошибка при удалении элемента с id " + id);
            }
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage());
        }
    }
}
