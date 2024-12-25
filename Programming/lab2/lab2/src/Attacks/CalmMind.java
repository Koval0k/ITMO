package Attacks;

import ru.ifmo.se.pokemon.*;

public class CalmMind extends StatusMove {
    public CalmMind(){
        super(Type.PSYCHIC,0,0);
    }

    protected void applyOppEffects(Pokemon p){
        p.setCondition(new Effect().turns(1).stat(Stat.SPECIAL_ATTACK, 6));
        p.setCondition(new Effect().turns(1).stat(Stat.SPECIAL_DEFENSE, 6));
    }
    @Override
    protected String describe(){
        return "использует атаку Calm Mind";
    }
}
