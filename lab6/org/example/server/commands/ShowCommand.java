package org.example.server.commands;

import org.example.common.dto.*;
import org.example.server.managers.CollectionManager;
import java.io.IOException;
import java.util.stream.Collectors;
import org.example.common.spacemarine.*;

public class ShowCommand extends ServerCommand {


    public ShowCommand(CollectionManager cm) {
        super("show", "вывести все элементы коллекции в строковом представлении", cm);
    }

    @Override
    public Response execute(Request request) throws IOException {
        String info = cm.getCollection().values()
                .stream()
                .map(SpaceMarine::toString)
                .collect(Collectors.joining("\n"));
        return new Response(info);
    }
}
