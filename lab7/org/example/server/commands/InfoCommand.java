package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.server.managers.CollectionManager;
import org.example.server.managers.DataBaseManager;

public class InfoCommand extends ServerCommand{

    public InfoCommand(CollectionManager cm) {
        super("info", "вывести информацию о коллекции", cm);
    }

    @Override
    public Response execute(Request request) {
        String login = request.getLogin();
        String password = request.getPassword();

        if (!DataBaseManager.checkUserCredentials(login, password)) {
            return new Response("Не выполнен вход в систему");
        }

        return new Response(String.format(
                "Информация о коллекции:%n" +
                        "%-25s%s%n" +
                        "%-25s%s%n" +
                        "%-25s%d",
                "Тип коллекции:", cm.getCollectionType(),
                "Дата инициализации:", cm.getInitDate(),
                "Количество элементов:", cm.getCollectionSize()
        ));
    }

}
