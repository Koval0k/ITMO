package commands;

import utils.Console;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            Console.printError("Команда 'exit' не принимает аргументов");
            return;
        }

        Console.print("Вы уверены, что хотите выйти? Все несохраненные данные будут потеряны. (yes/no): ");
        String confirmation = Console.readLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            Console.println("Завершение программы...");
            System.exit(0);
        } else {
            Console.println("Выход отменен");
        }
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}
