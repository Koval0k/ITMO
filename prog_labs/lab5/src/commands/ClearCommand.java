package commands;

import utils.*;

public class ClearCommand implements Command{
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            Console.printError("Команда 'clear' не принимает аргументов");
            return;
        }

        if (collectionManager.getCollection().isEmpty()) {
            Console.println("Коллекция уже пуста");
            return;
        }

        Console.print("Вы действительно хотите очистить коллекцию? (yes/no): ");
        String confirmation = Console.readLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            collectionManager.clear();
            Console.println("Коллекция успешно очищена");
        } else {
            Console.println("Очистка коллекции отменена");
        }
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
