package utils;

import SpaceMarine.*;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class CollectionManager {
    private Map<Long, SpaceMarine> collection = new HashMap<>();
    private LocalDateTime initDate;

    public CollectionManager() {
        this.initDate = LocalDateTime.now();
    }

    public void removeElement(Long id) {
        collection.remove(id);
    }

    public Map<Long, SpaceMarine> getCollection() {
        return collection;
    }

    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public int getCollectionSize() {
        return collection.size();
    }

    public void addElement(Long key, SpaceMarine marine) {
        collection.put(key, marine);
    }

    public SpaceMarine getById(long id) {
        return collection.values().stream().filter(marine -> marine.getId() == id).findFirst().orElse(null);
    }

    public void updateElement(long id, SpaceMarine newMarine) {
        collection.entrySet().stream().filter(entry -> entry.getValue().getId() == id).findFirst().ifPresent(entry -> entry.setValue(newMarine));
    }

    public void clear() {
        collection.clear();
    }

    public void removeById(long id) {
        collection.values().removeIf(marine -> marine.getId() == id);
    }

    public SpaceMarine getByKey(Long key) {
        return collection.get(key);
    }

    public void replace(Long key, SpaceMarine newElement) {
        SpaceMarine oldElement = collection.get(key);
        SpaceMarine newCollectionElement = new SpaceMarine(newElement.getName(), newElement.getHealth(), newElement.getHeartCount(), newElement.getAchievements(),
                newElement.getCoordinates(), newElement.getChapter(), newElement.getWeapontype(), oldElement.getId(), oldElement.getCreationDate());
        collection.put(key, newCollectionElement);
    }

    public SpaceMarine inputSpaceMarine(Long key){
        return new SpaceMarine(
                inputName(),
                inputHealth(),
                inputHeartCount(),
                inputAchievements(),
                inputCoordinates(),
                inputChapter(),
                inputWeaponType(),
                key,
                null
        );
    }

    private String inputName() {
        while (true) {
            try {
                Console.print("Введите имя (не может быть пустым): ");
                String input = Console.readLine().trim();
                if (input.isEmpty()) throw new IllegalArgumentException();
                return input;
            } catch (IllegalArgumentException e) {
                Console.printError("Имя не может быть пустым");
            }
        }
    }

    private Coordinates inputCoordinates() {
        while(true){
            try{
                Console.print("Введите координату x (не может быть пустым): ");
                Integer x = Integer.parseInt(Console.readLine().trim());
                if (x == null) throw new IllegalArgumentException();

                Console.print("Введите координату y (не может превышать 444): ");
                long y = Long.parseLong(Console.readLine().trim());
                if (y > 444) throw new IllegalArgumentException();
                return new Coordinates(x, y);
            } catch (IllegalArgumentException e) {
                Console.printError("Координата x не может быть null, координата y не может превышать 444");
            }
        }
    }

    private double inputHealth() {
        while(true){
            try{
                Console.print("Введите количество здоровья(значение должно быть больше 0):");
                Double health = Double.parseDouble(Console.readLine().trim());
                if (health<=0) throw new IllegalArgumentException();
                return health;
            } catch (IllegalArgumentException e){
                Console.printError("Значение здоровья должно быть больше 0");
            }
        }
    }

    private Integer inputHeartCount() {
        while(true) {
            try {
                Console.print("Введите количество хп (от 1 до 3, пустая строка для null): ");
                String input = Console.readLine().trim();

                if (input.isEmpty()) {
                    return null;
                }

                int heartCount = Integer.parseInt(input);

                if (heartCount < 1 || heartCount > 3) {
                    throw new IllegalArgumentException();
                }

                return heartCount;
            } catch (NumberFormatException e) {
                Console.printError("Введите целое число или оставьте пустую строку");
            } catch (IllegalArgumentException e) {
                Console.printError("Значение хп должно быть от 1 до 3");
            }
        }
    }

    private String inputAchievements(){
        Console.print("Введите достижения: ");
        String achievements = Console.readLine().trim();
        return achievements.isEmpty() ? null : achievements;

    }

    private Chapter inputChapter() {
        Console.println("Ввод данных о главе");
        Console.print("Создать главу? (yes/no): ");
        String choice = Console.readLine().trim();
        if (!choice.equalsIgnoreCase("yes")) {
            return null;
        }
        return inputChapterData();
    }

    private Chapter inputChapterData() {
        Console.println("Ввод данных о главе (Chapter):");
        String name = inputChapterName();
        String world = inputChapterWorld();
        return new Chapter(name, world);
    }

    private String inputChapterName() {
        while (true) {
            Console.print("Введите название главы (не может быть пустым): ");
            String input = Console.readLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            Console.printError("Название главы не может быть пустым");
        }
    }

    private String inputChapterWorld() {
        while (true) {
            Console.print("Введите мир главы (не может быть пустым): ");
            String input = Console.readLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            Console.printError("Мир главы не может быть пустым");
        }
    }

    private Weapon inputWeaponType() {
        Console.println("Ввод типа оружия (weaponType):");
        Console.println("Доступные варианты:");

        for (Weapon weapon : Weapon.values()) {
            Console.println("- " + weapon.name());
        }

        Console.println("• Поле может быть null");
        Console.println("• Для значения null введите пустую строку");
        Console.print("Введите тип оружия: ");

        while (true) {
            String input = Console.readLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return Weapon.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                Console.printError("Некорректный тип оружия. Введите одно из доступных значений или пустую строку:");
            }
        }
    }

}
