package org.example.server.commands;

import org.example.common.spacemarine.*;
import org.example.server.managers.CollectionManager;
import org.example.common.dto.*;
import org.example.server.managers.DataBaseManager;

import java.io.IOException;

public class InsertElementCommand extends ServerCommand{
    public InsertElementCommand(CollectionManager cm){
        super("insert", "добавить новый элемент с заданным ключом", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        try {
            String login = request.getLogin();
            String password = request.getPassword();

            if (!DataBaseManager.checkUserCredentials(login, password)) {
                return new Response("Не выполнен вход в систему");
            }

            SpaceMarine spaceMarine = request.getProduct();
            spaceMarine.setUser(login);
            Long id = spaceMarine.getId();
            if (cm.getCollection().containsKey(id)) {
                throw new IllegalArgumentException("Элемент с ключом " + id + " уже существует");
            }

            boolean res = DataBaseManager.insertSpaceMarine(spaceMarine);
            if(res) {
                cm.addElement(id, request.getProduct());
                return new Response("Объект успешно добавлен");
            }else{
                return new Response("Ошибка при добавлении объекта");
            }
        }catch (IllegalArgumentException e){
            return new Response(e.getMessage());
        }
    }
}
