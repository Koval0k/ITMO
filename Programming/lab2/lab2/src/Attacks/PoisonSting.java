package Attacks;
import ru.ifmo.se.pokemon.*;

public class PoisonSting extends PhysicalMove{
    public PoisonSting(){
        super(Type.POISON,15,100);
    }

    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setCondition(new Effect().chance(0.3).condition(Status.POISON));
    }

    @Override
    protected String describe(){
        return "использует атаку Poison Sting";
    }
}
