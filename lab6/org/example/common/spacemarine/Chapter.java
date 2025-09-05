package org.example.common.spacemarine;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String name;
    private String world;

    public Chapter(String name, String world){
        setName(name);
        setWorld(world);
    }

    private void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    private void setWorld(String world){
        if(world == null){
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.world = world;
    }

    public String getName(){
        return name;
    }

    public String getWorld(){
        return world;
    }

    @Override
    public String toString() {
        return "Название главы:"+name+", Мир:"+world;
    }
}