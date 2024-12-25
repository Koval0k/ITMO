import java.util.Random;
import exceptions.*;

public class Crowd {
    private Random r = new Random();
    private final int rows;
    private final int columns;
    private int streetCapacity = new Random().nextInt(4)+1;
    private ShortGuy[][] crowd;
    private ShortGuy yeller = new ShortGuy();
    private Car jam = new Car();

    public Crowd() {
        rows = r.nextInt(10) + 1;
        columns = r.nextInt(10) + 1;
        crowd = new ShortGuy[rows][columns];
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                crowd[i][j] = new ShortGuy();
                crowd[i][j].setHeight();
            }
        }
    }

    public void changeLocation(Location location){
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                crowd[i][j].changeLocation(location);
            }
        }
    }

    public boolean crowdHeight(){ //Метод, проверяющий, увидит ли коротышка Незнайку
        yeller = crowd[0][0];
        for(int i=rows-1; i>0; i--){
            for(int j=columns-1; j>0; j--){
                crowd[i][j].higher = crowd[i][j].isHigher(crowd, i, j);
                if(!crowd[i][j].higher){
                    yeller = crowd[i][j];
                    return true;
                }
            }
        }
        return false;
    }

    public void mayhem(Neznayka boy, Policeman officer){
            int i = 0;
            while(i<=streetCapacity) { // every short guy and car increases the street load until it's too much
                i = crowd[0][0].tryToMove(i);
                i = yeller.tryToMove(i);
                i = jam.tryToMove(i);
                System.out.println(" ");
            }
            officer.makeMove(boy);
            changeLocation(Location.ELSEWHERE);
            System.out.println("Все разбежались кто-куда");
    }

    public void outcomes(Neznayka boy, Policeman officer) throws TrickFailedException{ //main program method
        if(boy.trick()){
            System.out.println("Незнайка показал трюк и привлек внимание прохожих!\n");
            changeLocation(Location.CROSSROAD);
            if(crowdHeight()){//Neutral ending
                mayhem(boy, officer);
            }
            else { //Good Ending
                changeLocation(Location.ELSEWHERE);
                System.out.println("Коротышки посмотрели на трюк Незнайки и ушли довольными.\nНезнайка обрадовался");
            }
        }
        else{ //Bad Ending
            throw new TrickFailedException(boy.getName());
        }
    }
}
