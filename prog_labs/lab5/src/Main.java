import utils.*;

import java.util.Scanner;

public class Main {
    public static final String ENV_VARIABLE = "COLLECTION_FILE";
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    public Main() {
        this.collectionManager = FileDataLoader.loadCollectionFromFile();
        this.commandManager = new CommandManager(collectionManager, ENV_VARIABLE);
    }

    public static void main(String[] args) {
        Main application = new Main();
        application.start();
    }

    public void start() {
        runInteractiveMode();
    }

    private void runInteractiveMode() {
        Scanner userScanner = new Scanner(System.in);
        Console.println("Программа запущена. Введите команду (help для справки):");

        while (true) {
            try {
                Console.print("> ");
                String input = userScanner.nextLine().trim();

                if (!input.isEmpty()) {
                    String[] parts = input.split(" ", 2);
                    commandManager.executeCommand(parts[0], parts.length > 1 ? new String[]{parts[1]} : new String[0]);
                }
            } catch (Exception e) {
                Console.printError("Ошибка: " + e.getMessage());
            }
        }
    }

}