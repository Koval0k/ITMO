public abstract class Human {
    public Location location;
    public String name;

    public abstract void changeLocation(Location location);

    public String getName(){
        return this.name;
    }

}
