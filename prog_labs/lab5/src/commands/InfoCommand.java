package commands;

import utils.*;

public class InfoCommand implements Command{
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            Console.printError("Команда не принимает аргументов");
            return;
        }
        System.out.println("Информация о коллекции:");
        System.out.printf("%-25s%s%n", "Тип коллекции:", collectionManager.getCollectionType());
        System.out.printf("%-25s%s%n", "Дата инициализации:", collectionManager.getInitDate());
        System.out.printf("%-25s%d%n", "Количество элементов:", collectionManager.getCollectionSize());
    }

    @Override
    public String getDescription(){
        return "вывести информацию о коллекции";
    }
}
