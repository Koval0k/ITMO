package commands;

import utils.CollectionManager;
import SpaceMarine.*;
import utils.Console;

public class InsertElementCommand implements Command {
    private final CollectionManager collectionManager;

    public InsertElementCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length < 1) throw new IllegalArgumentException("Не указан ключ");

            Long key = parseKey(args[0]);
            if (collectionManager.getCollection().containsKey(key)) {
                throw new IllegalArgumentException("Элемент с ключом " + key + " уже существует");
            }

            Console.println("\nДобавление нового элемента с ключом " + key);
            SpaceMarine marine = collectionManager.inputSpaceMarine(key);
            collectionManager.addElement(key, marine);
            Console.println("Элемент успешно добавлен");

        } catch (IllegalArgumentException e) {
            Console.printError(e.getMessage());
        }
    }

    private Long parseKey(String input) {
        if (input.equalsIgnoreCase("null")) {
            return null;
        }
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат ключа: " + input);
        }
    }


    @Override
    public String getDescription() {
        return "добавить новый элемент с заданным ключом";
    }
}