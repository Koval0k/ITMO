package Attacks;
import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove{
    public Facade(){
        super(Type.NORMAL,70,100);
    }
    protected double calcCriticalHit(Pokemon att, Pokemon def){
        if ((att.getCondition()==Status.BURN)||(att.getCondition()==Status.POISON)||(att.getCondition()==Status.PARALYZE)){
            System.out.println("Critical hit!");
            return 2.0;
        } else {
            return 1.0;
        }
    }
    @Override
    protected String describe(){
        return "использует атаку Facade";
    }
}