package org.example.server.commands;

import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.server.managers.*;

public class LoginCommand extends ServerCommand{
    public LoginCommand(CollectionManager cm) {
        super("login", "войти в систему", cm);
    }

    @Override
    public Response execute(Request request) {
        String login = request.getLogin();
        String password = request.getPassword();

        if (login == null || password == null) {
            return new Response("необходимы логин и пароль");
        }

        boolean valid = DataBaseManager.checkUserCredentials(login, password);
        return valid
                ? new Response("успешный вход в систему success")
                : new Response("неверное имя пользователя или пароль");
    }
}
