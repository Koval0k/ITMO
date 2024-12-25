package Attacks;
import ru.ifmo.se.pokemon.*;

public class Scratch extends PhysicalMove{
    public Scratch(){
        super(Type.NORMAL,40,100);
    }

    protected double calcTypeEffect(Pokemon pokemon, Pokemon pokemon1) {
        return 1.0;
    }
    @Override
    protected String describe(){
        return "использует атаку Scratch";
    }
}
