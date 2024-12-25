package Pokemons;

import ru.ifmo.se.pokemon.*;
import Attacks.*;
public class Shuppet extends Pokemon {
    public Shuppet(String name, int level) {
        super(name, level);
        this.setStats(44, 75, 35, 63, 33, 45);
        this.setType(Type.GHOST);
        this.setMove(new CalmMind(), new DoubleTeam(), new DreamEater());
    }
}
