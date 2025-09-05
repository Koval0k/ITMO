package org.example.client.commands;

public class ExitCommand extends ClientCommand {
    public ExitCommand(){
        setDescription("закрыть клиентскую часть");
        setName("exit");
    }

    @Override
    public boolean execute(String[] args) {
        System.out.println("Closing client...");
        System.exit(0);
        return true;
    }

}