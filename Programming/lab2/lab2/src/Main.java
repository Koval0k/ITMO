import ru.ifmo.se.pokemon.*;
import Pokemons.*;


public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Banette p1 = new Banette("Кукла", 50);
        Nidoqueen p2 = new Nidoqueen("Броненосец", 50);
        NidoranF p3 = new NidoranF("Хомяк", 50);
        Nidorina p4 = new Nidorina("Динозаврик", 50);
        Seviper p5 = new Seviper("Змеюга", 50);
        Shuppet p6 = new Shuppet("Призрак", 50);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}




