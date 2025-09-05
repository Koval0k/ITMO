package org.example.client.commands;

import org.example.client.Client;
import org.example.client.SpaceMarineCreator;
import org.example.common.dto.Request;
import org.example.common.dto.Response;
import org.example.common.spacemarine.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ExecuteScriptCommand extends ClientCommand{
    private final Client client;
    private static final Stack<String> executingScripts = new Stack<>();
    private static final List<String> SPACEMARINE_COMMANDS = List.of("insert", "replace_if_lowe", "update");
    private static final List<String> ID_COMMANDS = List.of("remove_by_key", "remove_greater_key");

    public ExecuteScriptCommand(Client client) {
        this.client = client;
        setDescription("выполнить скрипт из указанного файла");
        setName("execute_script");
    }

    public boolean execute(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Error: Script file path required");
            return false;
        }

        String filePath = args[0];
        if (executingScripts.contains(filePath)) {
            System.err.println("Error: Recursion detected in script: " + filePath);
            return false;
        }

        executingScripts.push(filePath);
        try (Scanner scanner = new Scanner(new File(filePath))) {
            System.out.println("Executing script: " + filePath);
            processScript(scanner);
            return true;
        } finally {
            executingScripts.pop();
        }
    }

    private void processScript(Scanner scanner) throws IOException {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            System.out.println("Executing: " + line);

            if (command.equals("execute_script")) {
                execute(new String[]{argument});
            } else if (SPACEMARINE_COMMANDS.contains(command)) {
                processSpaceMarineCommand(scanner, command, argument);
//            } else if (ID_COMMANDS.contains(command)) {
//                processUpdateCommand(scanner, argument);
            } else {
                sendSimpleCommand(line);
            }
        }
    }

    private void processSpaceMarineCommand(Scanner scanner, String command, String arg) throws IOException {
        SpaceMarine spaceMarine;
        long key = Long.parseLong(arg);
        spaceMarine = SpaceMarineCreator.inputSpaceMarineFromScript(scanner, key);

        Request request = new Request(command);
        request.setProduct(spaceMarine);
        sendAndPrintResponse(request);
    }

    private void sendSimpleCommand(String commandLine) {
        Request request = new Request(commandLine);
        sendAndPrintResponse(request);
    }

    private void sendAndPrintResponse(Request request) {
        try {
            Response response = client.sendRequest(request);
            System.out.println(response.getResponse());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error sending request: " + e.getMessage());
        }
    }

}