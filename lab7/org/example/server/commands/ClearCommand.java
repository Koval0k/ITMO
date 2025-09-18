package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.DataBaseManager;

public class ClearCommand extends ServerCommand {

    public ClearCommand(CollectionManager cm) {
        super("clear", "очистить коллекцию", cm);
    }

    @Override
    public Response execute(Request request) {
        String login = request.getLogin();
        String password = request.getPassword();

        if (!DataBaseManager.checkUserCredentials(login, password)) {
            return new Response("Не выполнен вход в систему");
        }

        boolean res = DataBaseManager.clearCollection(login);
        if(res) {
            cm.clear();
            return new Response("Коллекция успешно очищена");
        }else{
            return new Response("Ошибка при очищении коллекции");
        }
    }
}