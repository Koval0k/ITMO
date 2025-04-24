package commands;

import utils.*;
import SpaceMarine.*;

public class ReplaceIfLowerCommand implements Command{
    private final CollectionManager collectionManager;

    public ReplaceIfLowerCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length != 1) {
                Console.printError("Необходимо указать один аргумент - ключ элемента");
                return;
            }

            Long key = parseKey(args[0]);
            SpaceMarine existingElement = collectionManager.getByKey(key);

            if (existingElement == null) {
                Console.printError("Элемент с ключом " + key + " не найден");
                return;
            }

            Console.println("\nТекущий элемент:");
            Console.println(existingElement.toString());

            Console.println("\nВведите новые данные для сравнения:");
            SpaceMarine newElement = collectionManager.inputSpaceMarine(null);

            if (newElement.getHealth() < existingElement.getHealth()) {
                collectionManager.replace(key, newElement);
                Console.println("Элемент заменен (новое health меньше старого)");
            } else {
                Console.println("Элемент не заменен (новое health не меньше старого)");
            }

        } catch (NumberFormatException e) {
            Console.printError("Некорректный формат ключа");
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
            throw new IllegalArgumentException("Ключ должен быть числом или 'null'");
        }
    }

    @Override
    public String getDescription() {
        return "заменить значение по ключу, если новое значение health меньше старого";
    }
}
