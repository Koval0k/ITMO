package commands;

import utils.*;
import SpaceMarine.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PrintFieldDescendingWeaponTypeCommand implements Command{
    private final CollectionManager collectionManager;

    public PrintFieldDescendingWeaponTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args){
        if (args.length > 0) {
            Console.printError("Команда не принимает аргументов");
            return;
        }

        List<Weapon> weaponTypes = collectionManager.getCollection().values().stream()
                .map(SpaceMarine::getWeapontype)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .toList();

        if (weaponTypes.isEmpty()) {
            Console.println("В коллекции нет элементов с указанным weaponType");
            return;
        }

        Console.println("Значения weaponType в порядке убывания:");
        weaponTypes.forEach(weapon ->
                Console.println(weapon.toString())
        );
    }
    @Override
    public String getDescription(){
        return "вывести значения поля weaponType всех элементов в порядке убывания";
    }
}
