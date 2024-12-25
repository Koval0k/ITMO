import java.util.Random;

public class Car implements Movable{
    private Location location;
    private static int trafficLimit = new Random().nextInt(10)+5;
    private static int trafficAmount = new Random().nextInt(10)+10;

    public static boolean isTrafficJam(){
        return trafficLimit <= trafficAmount;
    }

    @Override
    public void yell(){
        System.out.println("\"Уйдите с дороги!\",- кричат водители машин");
    }

    @Override
    public int tryToMove(int inc){
        if(isTrafficJam()){
            yell();
            return inc + 1;
        }
        return inc;
    }
}
