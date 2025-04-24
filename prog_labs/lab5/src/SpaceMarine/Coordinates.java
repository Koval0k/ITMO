package SpaceMarine;

public class Coordinates {
    private Integer x; //Поле не может быть null
    private long y; //Максимальное значение поля: 444

    public Coordinates(Integer x, long y){
        setXCoordinate(x);
        setYCoordinate(y);
    }

    private void setXCoordinate(Integer x){
        if(x == null){
            throw new IllegalArgumentException("X coordinate cannot be null");
        }
        this.x = x;
    }

    private void setYCoordinate(long y){
        if(y>444){
            throw new IllegalArgumentException("X coordinate cannot be null");
        }
        this.y = y;
    }

    public Integer getX(){
        return x;
    }

    public long getY(){
        return y;
    }

    @Override
    public String toString() {
        return String.format("Координаты: (%d, %d)", x, y);
    }
}
