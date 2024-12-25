package Pokemons;

import ru.ifmo.se.pokemon.*;
import Attacks.*;

public class NidoranF extends Pokemon {
    public NidoranF(String name, int level){
        super(name,level);
        this.setType(Type.POISON);
        this.setStats(55,47,52,40,40,41);
        this.setMove(new Blizzard(), new PoisonSting());
    }
}
