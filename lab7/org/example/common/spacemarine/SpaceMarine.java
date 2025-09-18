package org.example.common.spacemarine;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class SpaceMarine implements Serializable {
    private static final AtomicLong idGenerator = new AtomicLong(1);

    private final Long id;
    private String name;
    private final LocalDateTime creationDate;
    private double health;
    private Integer heartCount;
    private String achievements;
    private Chapter chapter;
    private Coordinates coordinates;
    private Weapon weapontype;
    private String user;

    public SpaceMarine(String name, double health, Integer heartCount, String achievements, Coordinates coordinates, Chapter chapter, Weapon weapontype,
                       Long id, LocalDateTime creationDate, String user) {
        if(id==null){this.id = idGenerator.getAndIncrement();}
        else{this.id = id;}//for replacement

        if(creationDate == null){this.creationDate = LocalDateTime.now();}
        else{this.creationDate=creationDate;}
        setName(name);
        setHealth(health);
        setHeartCount(heartCount);
        setAchievements(achievements);
        setCoordinates(coordinates);
        setChapter(chapter);
        setWeapon(weapontype);
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getHealth() {
        return health;
    }

    public Integer getHeartCount() {
        return heartCount;
    }

    public String getAchievements() {
        return achievements;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public Chapter getChapter(){
        return chapter;
    }

    public Weapon getWeapontype(){
        return weapontype;
    }

    public String getUser(){return user;}

    public void setUser(String user){this.user=user;}

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setHealth(double health) {
        if (health <= 0) {
            throw new IllegalArgumentException("Health must be greater than 0");
        }
        this.health = health;
    }

    public void setHeartCount(Integer heartCount) {
        if (heartCount != null && (heartCount <= 0 || heartCount > 3)) {
            throw new IllegalArgumentException("Heart count must be null or between 1 and 3");
        }
        this.heartCount = heartCount;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null");
        }
        this.coordinates = coordinates;
    }

    public void setWeapon(Weapon weapontype) {
        this.weapontype = weapontype;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return Double.compare(that.health, health) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(heartCount, that.heartCount) &&
                Objects.equals(achievements, that.achievements) &&
                weapontype == that.weapontype &&
                Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public String toString() {
        return "SpaceMarine{" +
                "id:" + id +
                ", Имя:'" + name + '\'' +
                ", дата создания:" + creationDate +
                ", Здоровье:" + health +
                ", хп:" + heartCount +
                ", Достижения:'" + achievements + '\'' +
                ", " + coordinates +
                ", Тип оружия:" + weapontype +
                ", " + chapter +
                '}';
    }
}