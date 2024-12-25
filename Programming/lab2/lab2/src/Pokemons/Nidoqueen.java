package Pokemons;
import ru.ifmo.se.pokemon.*;
import Attacks.*;

public final class Nidoqueen extends Nidorina {
    public Nidoqueen(String name, int level){
        super(name,level);
        this.setType(Type.POISON,Type.GROUND);
        this.setStats(90,92,87,75,85,76);
        this.setMove(new Scratch());
    }
}
