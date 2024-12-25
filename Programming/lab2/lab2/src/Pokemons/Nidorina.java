package Pokemons;
import ru.ifmo.se.pokemon.*;
import Attacks.*;

public class Nidorina extends NidoranF{
    public Nidorina(String name,int level){
        super(name,level);
        this.setType(Type.POISON);
        this.setStats(70,62,67,55,55, 56);
        this.setMove(new Flatter());
    }
}
