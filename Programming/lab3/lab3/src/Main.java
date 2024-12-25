import exceptions.*;

public class Main {
    public static void main(String[] args) throws TrickFailedException {
        Neznayka neznayka = new Neznayka("Незнайка", Location.CROSSROAD);
        Policeman officer = new Policeman("Полицейский", Location.CROSSROAD);
        Crowd stack = new Crowd();
        stack.outcomes(neznayka, officer);
    }
}