package Attacks;
import ru.ifmo.se.pokemon.*;

public class ShadowClaw extends PhysicalMove{
    public ShadowClaw(){
        super(Type.GHOST,70,100);
    }

    protected double calcCriticalHit(Pokemon var1, Pokemon var2) {
        if (var1.getStat(Stat.SPEED)*3 / 512.0 > Math.random()) {
            System.out.println("Critical hit!");
            return 2.0;
        } else {
            return 1.0;
        }
    }

    @Override
    protected String describe(){
        return "использует атаку Shadow Claw";
    }
}
