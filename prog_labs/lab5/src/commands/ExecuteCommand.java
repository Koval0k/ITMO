package commands;

import utils.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ExecuteCommand implements Command {
    private final CommandManager commandManager;
    private static final Set<String> executingScripts = new HashSet<>();

    public ExecuteCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            Console.printError("Необходимо указать один аргумент - имя файла скрипта");
            return;
        }

        String fileName = args[0];
        if (executingScripts.contains(fileName)) {
            Console.printError("Обнаружена рекурсия! Скрипт '" + fileName + "' уже выполняется");
            return;
        }

        executingScripts.add(fileName);
        try (Scanner scanner = new Scanner(new File(fileName))) {
            Console.println("Выполнение скрипта: " + fileName);
            Console.setInputSource(scanner);
            while (scanner.hasNextLine()) {
                if (!scanner.hasNextLine()) break;
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("//")) continue;

                Console.println("> " + line);
                String[] parts = line.split(" ", 2);
                commandManager.executeCommand(parts[0],
                        parts.length > 1 ? new String[]{parts[1]} : new String[0]);
            }
        } catch (FileNotFoundException e) {
            Console.printError("Файл скрипта не найден: " + fileName);
        } finally {
            Console.resetToDefaultInput();
            executingScripts.remove(fileName);
        }
    }

    @Override
    public String getDescription() {
        return "выполнить скрипт из указанного файла";
    }
}