package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;

public class ClearCommand extends ServerCommand {

    public ClearCommand(CollectionManager cm) {
        super("clear", "очистить коллекцию", cm);
    }

    @Override
    public Response execute(Request request) {
        cm.clear();
        return new Response("Коллекция успешно очищена");
    }
}