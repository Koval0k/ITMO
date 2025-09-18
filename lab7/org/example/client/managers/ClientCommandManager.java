package org.example.client.managers;

import org.example.client.commands.*;
import java.util.*;
import org.example.client.Client;

public class ClientCommandManager {
    private final Map<String, ClientCommand> clientCommandMap = new HashMap<>();
    private Client client;

    public ClientCommandManager(Client client){
        this.client = client;
        clientCommandMap.put("help", new HelpCommand(this));
        clientCommandMap.put("exit", new ExitCommand());
        clientCommandMap.put("execute_script", new ExecuteScriptCommand(client));
    }

    public Map<String, ClientCommand> getCommandHashMap() {
        return clientCommandMap;
    }

    public ClientCommand getClientCommand(String name){
        return clientCommandMap.get(name);
    }

    public boolean hasCommand(String name) {
        return clientCommandMap.containsKey(name);
    }

    public void executeCommand(String name, String[] args) {
        ClientCommand command = clientCommandMap.get(name);
        if (command != null) {
            System.out.println(Arrays.toString(args));
            try {
                command.execute(args);
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении команды: " + e.getMessage());
            }
        }
    }

    public Client getClient() {
        return client;
    }
}
