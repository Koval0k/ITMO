package exceptions;

public class TrickFailedException extends Exception{
    public TrickFailedException(String name){
        super(name + " не выполнил трюк. Никто не обратил на него внимание");
    }
}
