package commands;
import utils.CommandManager;
import utils.Console;

public class HelpCommand implements Command{
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args){
        if (args.length > 0) {
            Console.printError("Команда не принимает аргументов");
            return;
        }
        commandManager.getCommands().forEach((name, command) -> {
            System.out.printf("%-40s%s%n", name, command.getDescription());
        });
    }
    @Override
    public String getDescription(){
        return "вывести справку по доступным командам";
    }
}
