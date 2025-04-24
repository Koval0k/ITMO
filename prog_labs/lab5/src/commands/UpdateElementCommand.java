package commands;

import utils.CollectionManager;
import SpaceMarine.*;
import utils.Console;

public class UpdateElementCommand implements Command {
    private final CollectionManager collectionManager;

    public UpdateElementCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        try {
            if (args.length < 1) throw new IllegalArgumentException("Не указан id");

            long id = Long.parseLong(args[0]);
            SpaceMarine oldMarine = collectionManager.getById(id);
            if (oldMarine == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }

            Console.println("\nОбновление элемента с id " + id);
            SpaceMarine newMarine = collectionManager.inputSpaceMarine(oldMarine.getId());
            collectionManager.updateElement(id, newMarine);
            Console.println("Элемент успешно обновлен");

        } catch (NumberFormatException e) {
            Console.printError("Некорректный формат id");
        } catch (IllegalArgumentException e) {
            Console.printError(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по id";
    }
}