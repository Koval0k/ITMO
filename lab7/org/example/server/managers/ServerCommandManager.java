package org.example.server.managers;

import java.util.HashMap;
import java.util.Map;
import org.example.server.commands.*;
import org.example.common.dto.*;

public class ServerCommandManager {
    private final Map<String, ServerCommand> commands = new HashMap<>();
    private final  CollectionManager cm;

    public ServerCommandManager(CollectionManager cm) {
        this.cm = cm;
        registerCommand("help", new ServerHelpCommand(this));
        registerCommand("info", new InfoCommand(cm));
        registerCommand("show", new ShowCommand(cm));
        registerCommand("remove_key", new RemoveByKeyCommand(cm));
        registerCommand("remove_greater_key", new RemoveGreaterKeyCommand(cm));
        registerCommand("insert", new InsertElementCommand(cm));
        registerCommand("update", new UpdateElementCommand(cm));
        registerCommand("clear", new ClearCommand(cm));
        registerCommand("print_ascending", new PrintAscendingCommand(cm));
        registerCommand("remove_greater", new RemoveGreater(cm));
        registerCommand("replace_if_lowe", new ReplaceIfLowerCommand(cm));
        registerCommand("print_field_descending_weapon_type", new PrintFieldDescendingWeaponTypeCommand(cm));
        registerCommand("group_counting_by_id", new GroupCountingByIdCommand(cm));
        registerCommand("register", new RegisterCommand(cm));
        registerCommand("login", new LoginCommand(cm));
    }

    private void registerCommand(String name, ServerCommand command) {
        commands.put(name, command);
    }

    public Map<String, ServerCommand> getCommands() {
        return commands;
    }

    public ServerCommand getServerCommand(String name) {
        if (name.equalsIgnoreCase("save")) {
            return new ServerCommand("save", "blocked command", cm) {
                @Override
                public Response execute(Request request) {
                    return new Response("У вас нет прав для использования данной программы.");
                }
            };
        }

        ServerCommand command = commands.get(name);
        if (command == null) {
            return new ServerCommand(name, "unknown command", cm) {
                @Override
                public Response execute(Request request) {
                    return new Response("Неизвестная команда: '" + name + "'. Для получения списка команд введите 'help'.");
                }
            };
        }
        return commands.get(name);
    }
}
