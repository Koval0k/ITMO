package utils;

import java.util.HashMap;
import java.util.Map;
import commands.*;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandManager(CollectionManager collectionManager, String envVariable) {
        registerCommand("help", new HelpCommand(this));
        registerCommand("info", new InfoCommand(collectionManager));
        registerCommand("show", new ShowCommand(collectionManager));
        registerCommand("remove_key", new RemoveByKeyCommand(collectionManager));
        registerCommand("remove_greater_key", new RemoveGreaterKeyCommand(collectionManager));
        registerCommand("insert", new InsertElementCommand(collectionManager));
        registerCommand("update", new UpdateElementCommand(collectionManager));
        registerCommand("clear", new ClearCommand(collectionManager));
        registerCommand("exit", new ExitCommand());
        registerCommand("print_ascending", new PrintAscendingCommand(collectionManager));
        registerCommand("remove_greater", new RemoveGreater(collectionManager));
        registerCommand("replace_if_lowe", new ReplaceIfLowerCommand(collectionManager));
        registerCommand("print_field_descending_weapon_type", new PrintFieldDescendingWeaponTypeCommand(collectionManager));
        registerCommand("group_counting_by_id", new GroupCountingByIdCommand(collectionManager));
        registerCommand("save", new SaveCommand(collectionManager, envVariable));
        registerCommand("execute_script", new ExecuteCommand(this));
    }

    private void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void executeCommand(String commandName, String[] args) {
        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Команда не найдена. Введите 'help' для списка команд.");
            return;
        }
        command.execute(args);
    }
}