package Attacks;

import ru.ifmo.se.pokemon.*;

public class DreamEater extends SpecialMove {
    public DreamEater(){
        super(Type.PSYCHIC,100,100);
    }

    protected void applySelfEffects(Pokemon p) {
        p.addEffect(new Effect().stat(Stat.HP, (int) p.getStat(Stat.SPECIAL_ATTACK)));
    }
    @Override
    protected String describe() {
        return "использует атаку Dream Eater";
    }
}
