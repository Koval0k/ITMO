import java.lang.Math;

public class ShortGuy extends Human implements Movable {
    public ShortGuy(){
        changeLocation(Location.ELSEWHERE);
    }

    private int height;
    boolean higher = true;

    @Override
    public void changeLocation(Location location){
        this.location = location;
    }

    public void setHeight(){
        this.height = (int) (Math.random() * 51) + 70;
    }

    public boolean isHigher(ShortGuy[][] array, int row, int position){
        for(int i=row-1;i>=1;i--){
            if(array[row][position].height - array[i][position].height < 5){
                return false;
            }
        }
        return true;
    }

    @Override
    public void yell(){
        if(higher){
            System.out.println("Коротышка спереди: \"Я видел Незнайку!\"");
        }
        else{
            System.out.println("Коротышка позади: \"Дайте посмотреть на него!\"");
        }
    }

    @Override
    public int tryToMove(int inc){ //inc extends the streets overload in outcomes method
        yell();
        if (!higher){
            return inc + 1;
        }
        return inc;
    }
}
