package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.server.managers.*;

public class RegisterCommand extends ServerCommand{
    public RegisterCommand(CollectionManager cm) {
        super("register", "зарегистрировать нового пользователя", cm);
    }

    @Override
    public Response execute(Request request) {
        String login = request.getLogin();
        String password = request.getPassword();

        if (login == null || password == null) {
            return new Response("введите логин и пароль");
        }

        boolean success = DataBaseManager.insertUser(login, password);
        return success
                ? new Response("успешная регистрация success")
                : new Response("регистрация не удалась");
    }
}
