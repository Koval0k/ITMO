package Attacks;
import ru.ifmo.se.pokemon.*;

public class PoisonTail extends PhysicalMove{
    public PoisonTail(){
        super(Type.POISON,50,100);
    }

    @Override
    protected String describe(){
        return "использует атаку Poison Tail";
    }

    protected void applyOppEffects(Pokemon p){
        p.setCondition(new Effect().chance(0.1).condition(Status.POISON));
    }

    protected double calcCriticalHit(Pokemon var1, Pokemon var2) {
        if (var1.getStat(Stat.SPEED)*3 / 512.0 > Math.random()) {
            System.out.println("Critical hit!");
            return 2.0;
        } else {
            return 1.0;
        }
    }
}

