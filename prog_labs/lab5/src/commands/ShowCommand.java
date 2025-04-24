package commands;

import utils.CollectionManager;
import utils.Console;

public class ShowCommand implements Command{
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args){
        if (args.length > 0) {
            Console.printError("Команда не принимает аргументов");
            return;
        }
        if(collectionManager.getCollection().isEmpty()){
            System.out.println("Коллекция пустая");
            return;
        }
        System.out.println("Элементы коллекции:");
        collectionManager.getCollection().values().forEach(spaceMarine -> System.out.println(spaceMarine.toString()));
    }

    @Override
    public String getDescription(){
        return "вывести все элементы коллекции в строковом представлении";
    }
}
