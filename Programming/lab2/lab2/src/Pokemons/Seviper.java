package Pokemons;

import Attacks.*;
import ru.ifmo.se.pokemon.*;

public final class Seviper extends Pokemon {
    public Seviper(String name, int level) {
        super(name, level);
        this.setType(Type.POISON);
        this.setStats(73, 100, 60, 100, 60, 65);
        this.setMove(new BodySlam(), new NightSlash(), new PoisonTail(), new Facade());
    }
}
