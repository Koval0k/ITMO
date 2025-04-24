package commands;

public interface Command {
    public void execute(String[] args);
    String getDescription();
}
