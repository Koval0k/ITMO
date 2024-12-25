import java.lang.Math;

public class Neznayka extends Human{
    public Neznayka(String name,Location location) {
        this.name = name;
        this.location = location;
    }

    public boolean trick(){
        if(Math.random()<0.8){
            return true;
        }
        else{
            return false;
        }
    }
    public void changeLocation(Location location){
        this.location = location;
    }
}
