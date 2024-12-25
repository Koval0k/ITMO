package Attacks;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam(){
        super(Type.NORMAL,0,0);
    }
    protected void applySelfEffects(Pokemon p){
        p.setCondition(new Effect().turns(1).stat(Stat.EVASION, 6));
    }
    @Override
    protected String describe(){
        return "использует атаку Double Team";
    }
}
