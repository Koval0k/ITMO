package Attacks;

import ru.ifmo.se.pokemon.*;

public class BodySlam extends PhysicalMove {
    public BodySlam() {
        super(Type.NORMAL,60,100);
    }

    @Override
    protected String describe() {
        return "Использует атаку Body Slam";
    }

    protected void applyOppEffects(Pokemon p){
        p.setCondition(new Effect().chance(0.3).condition(Status.PARALYZE));
    }
}
