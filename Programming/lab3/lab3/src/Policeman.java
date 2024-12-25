public record Policeman(String name, Location location) {

    public void rubHead(Neznayka boy) {
        System.out.println(name+ " почесал затылок");
        System.out.println("Полицейский: \"Пока тут " + boy.getName() + ", никто никуда не уйдет!\"");
    }

    public void getToStation(Human person){
        person.changeLocation(Location.POLICESTATION);
        System.out.println(person.getName() + " был отведен в участок!");
    }

    public void makeMove(Neznayka boy){
        rubHead(boy);
        getToStation(boy);
    }
}
