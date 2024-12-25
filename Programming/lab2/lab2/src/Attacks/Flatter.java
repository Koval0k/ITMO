package Attacks;
import ru.ifmo.se.pokemon.*;

public class Flatter extends StatusMove{
    public Flatter(){
        super(Type.DARK,0,0);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setCondition(new Effect().turns(1).condition(Status.PARALYZE));
    }

    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setCondition(new Effect().turns(1).stat(Stat.SPECIAL_ATTACK,6));
    }
    @Override
    protected String describe(){
        return "использует атаку Flatter";
    }
}
