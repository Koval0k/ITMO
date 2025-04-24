package commands;

import utils.CollectionManager;
import utils.Console;

public class RemoveByKeyCommand implements Command{
    private final CollectionManager collectionManager;

    public RemoveByKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args){
        try {
            if (args.length < 1) throw new IllegalArgumentException("Не указан ключ");

            Long id = Long.parseLong(args[0]);
            if (collectionManager.getById(id) == null) {
                throw new IllegalArgumentException("Элемент с id " + id + " не найден");
            }
            Console.print("Удалить объект? (yes/no): ");
            String choice = Console.readLine().trim();
            if (choice.equalsIgnoreCase("yes")) {
                collectionManager.removeElement(id);
            }
        }catch(IllegalArgumentException e) {
            Console.printError(e.getMessage());
        }
    }

    @Override
    public String getDescription(){
        return "удалить элемент из коллекции по его ключу";
    }
}
