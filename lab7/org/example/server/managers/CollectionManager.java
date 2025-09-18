package org.example.server.managers;

import org.example.common.spacemarine.*;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class CollectionManager {
    private Map<Long, SpaceMarine> collection = new HashMap<>();
    private LocalDateTime initDate;

    public CollectionManager() {
        this.initDate = LocalDateTime.now();
    }

    public void setCollection(HashMap<Long, SpaceMarine> collection) {
        this.collection = collection;
    }

    public void removeElement(Long id) {
        collection.remove(id);
    }

    public Map<Long, SpaceMarine> getCollection() {
        return collection;
    }

    public String getCollectionType() {
        return collection.getClass().getSimpleName();
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public int getCollectionSize() {
        return collection.size();
    }

    public void addElement(Long key, SpaceMarine marine) {
        collection.put(key, marine);
    }

    public SpaceMarine getById(long id) {
        return collection.values().stream().filter(marine -> marine.getId() == id).findFirst().orElse(null);
    }

    public void updateElement(long id, SpaceMarine newMarine) {
        collection.entrySet().stream().filter(entry -> entry.getValue().getId() == id).findFirst().ifPresent(entry -> entry.setValue(newMarine));
    }

    public void clear() {
        collection.clear();
    }

    public SpaceMarine getByKey(Long key) {
        return collection.get(key);
    }

    public void replace(Long key, SpaceMarine newElement) {
        SpaceMarine oldElement = collection.get(key);
        SpaceMarine newCollectionElement = new SpaceMarine(newElement.getName(), newElement.getHealth(), newElement.getHeartCount(), newElement.getAchievements(),
                newElement.getCoordinates(), newElement.getChapter(), newElement.getWeapontype(), oldElement.getId(), oldElement.getCreationDate(), newElement.getUser());
        collection.put(key, newCollectionElement);
    }

}